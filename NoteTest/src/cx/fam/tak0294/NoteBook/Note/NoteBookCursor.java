package cx.fam.tak0294.NoteBook.Note;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.View;


public class NoteBookCursor extends TimerTask
{
	//---------------------------------------
	//	メンバ.
	//---------------------------------------
	private NoteBook m_noteBook;
	private NoteBookCursorView m_cursorView;
	private int m_blinkInterval = 500;	//点滅ミリ秒.
	private Handler m_handler;
	private Timer m_cursorTimer;
	
	public NoteBookCursor(NoteBook noteBook)
	{
		m_noteBook = noteBook;
		m_cursorView = new NoteBookCursorView(m_noteBook.getContext());
		m_handler = new Handler();
		m_cursorTimer = new Timer();
		m_cursorTimer.scheduleAtFixedRate(this, 0, m_blinkInterval);
	}

	private void updateView()
	{
		m_handler.post(new Runnable()
		{
			@Override
			public void run() {
				// TODO 自動生成されたメソッド・スタブ
				m_cursorView.invalidate();
			}
		});
	}
	
	
	@Override
	public void run()
	{
		updateView();
	}

	public NoteBookCursorView getCursorView()
	{
		return m_cursorView;
	}
	
	
	public void setCursorPoints(float x, float y)
	{
		m_cursorView.x = x;
		m_cursorView.y = y;
		
		updateView();
	}
	
}
