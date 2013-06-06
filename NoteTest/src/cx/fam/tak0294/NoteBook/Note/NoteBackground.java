package cx.fam.tak0294.NoteBook.Note;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.View;
import cx.fam.tak0294.Utils.*;

public class NoteBackground extends View
{

	public NoteBackground(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	protected void onDraw(Canvas canvas)
	{
		//canvas.drawColor(Color.CYAN);
		
		Paint m_currentPaint = new Paint();
		m_currentPaint.setColor(Color.GRAY);
		m_currentPaint.setStrokeWidth(1);
		m_currentPaint.setAntiAlias(true);
		m_currentPaint.setStyle(Style.STROKE);
		
		Rect screenSize = DisplayUtil.getScreenRect(getContext());
		for(int ii=0;ii<screenSize.height();ii+=NoteGlobal.LINE_HEIGHT)
		{
			canvas.drawLine(NoteGlobal.NOTE_MARGIN_LEFT,
							ii*1.05f,
							screenSize.width()-NoteGlobal.NOTE_MARGIN_LEFT-NoteGlobal.NOTE_MARGIN_RIGHT,
							ii*1.05f,
							m_currentPaint);
		}
	}
}
