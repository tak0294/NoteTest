package cx.fam.tak0294.NoteBook.Note;

import java.util.ArrayList;

import android.content.Context;

//--------------------------------------------
//	ノート棚クラス.
//--------------------------------------------
public class NoteShelf
{
	private ArrayList<NoteBook> m_noteBooks = null;
	private Context m_context = null;
	
	//--------------------------------------------
	//	コンストラクタ.
	//--------------------------------------------
	public NoteShelf(Context context)
	{
		m_context = context;
		m_noteBooks = new ArrayList<NoteBook>();
	}
	
	//--------------------------------------------
	//	Context取得.
	//--------------------------------------------
	public Context getContext()
	{
		return m_context;
	}
	
	//--------------------------------------------
	//	ノート追加.
	//--------------------------------------------
	public NoteBook addNoteBook()
	{
		NoteBook nb = new NoteBook(this);
		m_noteBooks.add(nb);
		
		return nb;
	}
}
