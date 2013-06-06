package cx.fam.tak0294.NoteBook.Note;

import java.util.ArrayList;
import java.util.Timer;

import cx.fam.tak0294.Utils.UiUtil;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

//--------------------------------------------
//	ノートクラス.
//--------------------------------------------
public class NoteBook
{
	private NoteShelf m_noteShelf = null;
	private ArrayList<NoteView> m_notePages  = null;
	private FrameLayout m_mainFrame;
	private FrameLayout m_noteViewFrame;
	private LinearLayout m_footerToolsLayout;
	private NoteBackground m_noteBackground = null;
	private Button			m_newLineButton = null;
	private Button			m_backSpaceButton = null;
	private Button			m_spaceButton = null;
	
	private NoteView m_currentNoteView = null;
	private NoteBookCursor m_cursor = null;
	
	//--------------------------------------------
	//	コンストラクタ.
	//--------------------------------------------
	public NoteBook(NoteShelf noteShelf)
	{
		m_noteShelf = noteShelf;
		m_notePages = new ArrayList<NoteView>();
		m_mainFrame = new FrameLayout(noteShelf.getContext());
		m_noteViewFrame = new FrameLayout(noteShelf.getContext());
		m_footerToolsLayout = new LinearLayout(noteShelf.getContext());
		m_footerToolsLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
		
		//----------------------------------------
		//	改行ボタン.
		//----------------------------------------
		m_newLineButton = UiUtil.makeButton("　改行　", getContext());
		m_newLineButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0)
			{
				m_currentNoteView.newLine();
			}
		});
		m_footerToolsLayout.addView(m_newLineButton);
		
		//----------------------------------------
		//	バックスペースボタン.
		//----------------------------------------
		m_backSpaceButton = UiUtil.makeButton("　　BS　　", getContext());
		m_backSpaceButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0)
			{
				m_currentNoteView.backSpace();
			}
			
		});
		m_footerToolsLayout.addView(m_backSpaceButton);
		
		//----------------------------------------
		//	スペースボタン.
		//----------------------------------------
		m_spaceButton = UiUtil.makeButton("  SPACE  ", getContext());
		m_spaceButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v)
			{
				m_currentNoteView.space();
			}
		});
		m_footerToolsLayout.addView(m_spaceButton);
		
		m_noteBackground = new NoteBackground(noteShelf.getContext());
		
		//Add views.
		m_mainFrame.addView(m_noteBackground);
		m_mainFrame.addView(m_noteViewFrame);
		m_mainFrame.addView(m_footerToolsLayout);
		
		//Timer.
		m_cursor 		= new NoteBookCursor(this);
		m_mainFrame.addView(m_cursor.getCursorView());
		
	}
	
	//--------------------------------------------
	//	MainFrame取得.
	//--------------------------------------------
	public FrameLayout getMainFrame()
	{
		return m_mainFrame;
	}
	
	//--------------------------------------------
	//	Cursor取得.
	//--------------------------------------------
	public NoteBookCursor getCursor()
	{
		return m_cursor;
	}
	
	//--------------------------------------------
	//	Context取得.
	//--------------------------------------------
	public Context getContext()
	{
		return m_noteShelf.getContext();
	}
	
	//--------------------------------------------
	//	ページ追加.
	//--------------------------------------------
	public NoteView addNoteView()
	{
		NoteView nv = new NoteView(getContext());
		nv.setController(this);
		m_notePages.add(nv);
		
		return nv;
	}
	
	//--------------------------------------------
	//	カレントページ設定.
	//--------------------------------------------
	public void setCurrentNoteView(NoteView note)
	{
		if(m_currentNoteView != null)
			m_noteViewFrame.removeView(m_currentNoteView);
		m_noteViewFrame.addView(note);
		m_currentNoteView = note;
	}
}
