package cx.fam.tak0294.NoteBook.Note;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import cx.fam.tak0294.NoteBook.DrawObjects.*;
import cx.fam.tak0294.Utils.DisplayUtil;


//--------------------------------------------------
//	ノートViewクラス.
//--------------------------------------------------
public class NoteView extends View
{
	//--------------------------------------------------
	//	メンバ.
	//--------------------------------------------------
	private NoteBook m_noteBook = null;
	private final int c_noteWidth;
	private final int c_noteHeight;
	
	private float pos_x = 0f;	//Current touch pos x
	private float pos_y = 0f;	//Current touch pos y
	private float max_x = 0f;	//Current touch max x;
	private float min_x = 0f;	//Current touch min x;
	private float max_y = 0f;	//Current touch max y;
	private float min_y = 0f;	//Current touch min y;
	
	private float m_currentCharX = 0f;	//Current char cursor pos;
	private float m_currentCharY = 0f;	//Current char cursor pos;
	
	private Path m_currentPath = new Path();
	private Bitmap bitmap = null;
	private Paint m_currentPaint = null;
	
	private Handler m_handler = new Handler();
	private Timer m_writeWaitTimer = null;
	private boolean m_isWriteWait = false;
	
	private int m_currentLineIndex = 0;
	private int m_currentCursorIndex = 0;
	private ArrayList<ArrayList<DrawObject>> m_drawObjects = null;
	
	private boolean m_updateCacheBitmapRequest = false;
	//------------------------
	//	Debug var.
	//------------------------
	private boolean m_reDrawRequest = false;
	
	//--------------------------------------------------
	//	コンストラクタ.
	//--------------------------------------------------
	public NoteView(Context context) {
		super(context);
		this.setFocusable(true);
		m_writeWaitTimer = new Timer();
		m_drawObjects = new ArrayList<ArrayList<DrawObject>>();
		m_drawObjects.add(new ArrayList<DrawObject>());	//1行目.
		Rect r = DisplayUtil.getScreenRect(context);
		c_noteWidth 	= Math.min(r.width(), r.height());
		c_noteHeight 	= Math.max(r.width(), r.height());
		initPaint();
	}
	
	
	//-------------------------------------------------------------------------------------------------
	//
	//	private メソッド.
	//
	//-------------------------------------------------------------------------------------------------

	private void initPaint()
	{
		m_currentPaint= new Paint();
		m_currentPaint.setColor(Color.BLACK );
		m_currentPaint.setStrokeWidth(2);
		m_currentPaint.setAntiAlias(true);
		m_currentPaint.setStyle(Style.STROKE);
		m_currentPaint.setStrokeCap(Cap.ROUND);
		m_currentPaint.setStrokeJoin(Join.ROUND);
	}
	
	//--------------------------------------------------
	//	描画待ちタイマースタート.
	//--------------------------------------------------
	private void startWriteWaitTimer()
	{
		this.m_writeWaitTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				m_handler.post(new Runnable()
				{
					@Override
					public void run()
					{
						m_isWriteWait = false;
						m_reDrawRequest = true;
						m_updateCacheBitmapRequest = true;
						
						//Make DrawObject.
						CharDrawObject charObj = new CharDrawObject(m_currentPaint, m_currentPath, new RectF(min_x, min_y, max_x, max_y));
						
						//画面からはみ出す場合は新しい行にする.
						if(charObj.getOrgWidth() + m_currentCharX + NoteGlobal.NOTE_MARGIN_LEFT > c_noteWidth)
							newLine();
						
						m_currentCursorIndex++;
						charObj.moveToCursorX(m_currentCharX + NoteGlobal.NOTE_MARGIN_LEFT);
						charObj.moveToCursorY(m_currentCharY);
						m_currentCharX += charObj.getOrgWidth() + NoteGlobal.CHAR_PADDING;
						m_noteBook.getCursor().setCursorPoints(m_currentCharX, m_currentCharY);
						m_drawObjects.get(m_currentLineIndex).add(charObj);
						m_currentPath = new Path();
						
						invalidate();
					}
				});
			}}
		,NoteGlobal.WRITE_WAIT_TIME);
		
		this.m_isWriteWait = true;
		
	}
	
	//--------------------------------------------------
	//	描画待ちタイマーストップ.
	//--------------------------------------------------
	private void stopWriteWaitTimer()
	{
		this.m_writeWaitTimer.cancel();
		this.m_writeWaitTimer = new Timer();
		this.m_isWriteWait = false;
	}
	
	
	//--------------------------------------------------
	//	キャッシュBitmap更新.
	//--------------------------------------------------
	private void updateCacheBitmap()
	{
		if(bitmap != null)
		{
			bitmap.recycle();
			bitmap = null;
		}
		m_reDrawRequest = true;
		//---------------------------------------------
		//	Bitmapキャッシュ.
		//---------------------------------------------
		setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(getDrawingCache());
		setDrawingCacheEnabled(false);
		
		postInvalidate();
	}
	
	
	//-------------------------------------------------------------------------------------------------
	//
	//	public メソッド.
	//
	//-------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------
	//	コントローラの割付.
	//--------------------------------------------------
	public void setController(NoteBook nb)
	{
		m_noteBook = nb;
	}
	
	//--------------------------------------------------
	//	タッチイベント.
	//--------------------------------------------------
	public boolean onTouchEvent(MotionEvent e)
	{
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			
			
			pos_x = e.getX();
			pos_y = e.getY();
			
			if(!this.m_isWriteWait)
			{
				m_currentPath = new Path();
				min_x = max_x = pos_x;
				min_y = max_y = pos_y;
			}
			else
				this.stopWriteWaitTimer();
			
			m_currentPath.moveTo(pos_x, pos_y);
			break;
		
		case MotionEvent.ACTION_MOVE:
			pos_x = e.getX();
			pos_y = e.getY();
			
			//最大、最小値更新.
			//描画領域矩形算出のため.
			if(pos_x > max_x)
				max_x = pos_x;
			if(pos_x < min_x)
				min_x = pos_x;
			if(pos_y > max_y)
				max_y = pos_y;
			if(pos_y < min_y)
				min_y = pos_y;
		
			m_currentPath.lineTo(pos_x, pos_y);
			invalidate();
			break;
			
		case MotionEvent.ACTION_UP:
			//path.lineTo(e.getX(), e.getY());
			//キャッシュ.
			/*
			setDrawingCacheEnabled(true);
			bitmap = Bitmap.createBitmap(getDrawingCache());
			setDrawingCacheEnabled(false);
			invalidate();
			*/
			
			startWriteWaitTimer();
			
			break;
		}
		
		return true;
	}
	
	
	//--------------------------------------------------
	//	描画.
	//--------------------------------------------------
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(bitmap != null)
		{
			canvas.drawBitmap(bitmap, 0, 0, null);
		}
		
		if(m_currentPath != null)
			canvas.drawPath(m_currentPath, m_currentPaint);
		
		
		if(m_reDrawRequest)
		{
			m_reDrawRequest = false;

			for(int ii=0;ii<m_drawObjects.size();ii++)
			{
				for(int jj=0;jj<m_drawObjects.get(ii).size();jj++)
				m_drawObjects.get(ii).get(jj).draw(canvas);
			}
		}
		
		if(m_updateCacheBitmapRequest)
		{
			m_updateCacheBitmapRequest = false;
			updateCacheBitmap();
		}
	}
	
	
	//--------------------------------------------------
	//	改行.
	//--------------------------------------------------
	public void newLine()
	{
		m_currentLineIndex++;
		m_currentCursorIndex = 0;
		if(m_drawObjects.size()-1 < m_currentLineIndex)
			m_drawObjects.add(new ArrayList<DrawObject>());
		m_currentCharX = 0f;
		m_currentCharY += (NoteGlobal.LINE_HEIGHT * 1.05f);
		m_noteBook.getCursor().setCursorPoints(m_currentCharX, m_currentCharY);
	}

	//--------------------------------------------------
	//	バックスペース.
	//--------------------------------------------------
	public void backSpace()
	{
		if(m_currentCursorIndex == 0)
		{
			if(m_currentLineIndex == 0)	return;
			
			m_currentCharX = 0f;
			m_currentCharY -= (NoteGlobal.LINE_HEIGHT * 1.05f);
			m_currentLineIndex--;
			m_currentCursorIndex = m_drawObjects.get(m_currentLineIndex).size();
			for(int ii=0;ii<m_currentCursorIndex;ii++)
				m_currentCharX += m_drawObjects.get(m_currentLineIndex).get(ii).getOrgWidth() + NoteGlobal.CHAR_PADDING;
			m_noteBook.getCursor().setCursorPoints(m_currentCharX, m_currentCharY);
			return;
		}

		//削除対象のDrawObject分の横幅を戻す.
		DrawObject d = m_drawObjects.get(m_currentLineIndex).remove(m_currentCursorIndex-1);
		m_currentCharX -= d.getOrgWidth() + NoteGlobal.CHAR_PADDING;
		m_noteBook.getCursor().setCursorPoints(m_currentCharX, m_currentCharY);
		m_currentCursorIndex--;
		m_reDrawRequest = true;
		m_updateCacheBitmapRequest = true;
		postInvalidate();

	}

	//--------------------------------------------------
	//	スペース.
	//--------------------------------------------------
	public void space()
	{
		//Make DrawObject.
		CharSpaceObject charObj = new CharSpaceObject(null, null, new RectF(0f, 0f, 10f, 0f));
		
		//画面からはみ出す場合は新しい行にする.
		if(charObj.getOrgWidth() + m_currentCharX + NoteGlobal.NOTE_MARGIN_LEFT > c_noteWidth)
			newLine();
		
		m_currentCursorIndex++;
		m_currentCharX += charObj.getOrgWidth() + NoteGlobal.CHAR_PADDING;
		m_noteBook.getCursor().setCursorPoints(m_currentCharX, m_currentCharY);
		m_drawObjects.get(m_currentLineIndex).add(charObj);
		m_currentPath = new Path();
	}

}
