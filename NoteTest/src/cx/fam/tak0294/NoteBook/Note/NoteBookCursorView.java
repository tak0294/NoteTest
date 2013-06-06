package cx.fam.tak0294.NoteBook.Note;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.View;

public class NoteBookCursorView extends View
{
	private boolean m_blinkFlag = false;
	private Paint m_cursorPaint = null;
	
	public float x = 0f;
	public float y = 0f;
	
	public NoteBookCursorView(Context context) {
		super(context);
		
		m_cursorPaint= new Paint();
		m_cursorPaint.setColor(Color.BLACK );
		m_cursorPaint.setStrokeWidth(2);
		m_cursorPaint.setAntiAlias(true);
		m_cursorPaint.setStyle(Style.STROKE);
		m_cursorPaint.setStrokeCap(Cap.ROUND);
		m_cursorPaint.setStrokeJoin(Join.ROUND);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		m_blinkFlag = !m_blinkFlag;
		
		if(m_blinkFlag)
		{
			canvas.drawLine(x + 5, y, x + 5, y + NoteGlobal.LINE_HEIGHT, m_cursorPaint);
		}
	}
}
