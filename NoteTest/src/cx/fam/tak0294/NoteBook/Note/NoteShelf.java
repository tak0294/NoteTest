package cx.fam.tak0294.NoteBook.Note;

import java.util.ArrayList;

import android.content.Context;

//--------------------------------------------
//	�m�[�g�I�N���X.
//--------------------------------------------
public class NoteShelf
{
	private ArrayList<NoteBook> m_noteBooks = null;
	private Context m_context = null;
	
	//--------------------------------------------
	//	�R���X�g���N�^.
	//--------------------------------------------
	public NoteShelf(Context context)
	{
		m_context = context;
		m_noteBooks = new ArrayList<NoteBook>();
	}
	
	//--------------------------------------------
	//	Context�擾.
	//--------------------------------------------
	public Context getContext()
	{
		return m_context;
	}
	
	//--------------------------------------------
	//	�m�[�g�ǉ�.
	//--------------------------------------------
	public NoteBook addNoteBook()
	{
		NoteBook nb = new NoteBook(this);
		m_noteBooks.add(nb);
		
		return nb;
	}
}
