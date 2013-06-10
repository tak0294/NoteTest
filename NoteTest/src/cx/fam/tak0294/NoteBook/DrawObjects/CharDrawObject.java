package cx.fam.tak0294.NoteBook.DrawObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import cx.fam.tak0294.NoteBook.Note.NoteGlobal;

public class CharDrawObject extends DrawObject {
	
	public CharDrawObject(Paint paint, Path path, RectF r) {
		super(paint, path, r);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	@Override
	public void setPath(Path path) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void draw(Canvas canvas) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		canvas.drawPath(m_path, m_paint);
	}

	@Override
	protected void resize() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		//�T�C�Y�𒲂ׂ�.
		RectF bounds = new RectF();
		m_path.computeBounds(bounds, false);
		org_height = bounds.height();
		org_width  = bounds.width();
		
		//�ŏ��Ɉړ�������.
		Matrix m = new Matrix();
		float padding_y = 0f;
		
		//----------------------------------------------
		//	�����̔{�����v�Z����.
		//----------------------------------------------
		if(org_height > NoteGlobal.CHAR_HEIGHT || (org_width > NoteGlobal.CHAR_WIDTH && org_width < NoteGlobal.CHAR_WIDTH*4))
		{
			//----------------------------------------------
			//	������NoteGlobal.CHAR_WIDTH�̂S�{���傫���ꍇ�͉����͏������Ȃ�.
			//----------------------------------------------
			if(org_height > org_width || (org_width > NoteGlobal.CHAR_WIDTH*4))
			{
				m.preScale(NoteGlobal.CHAR_HEIGHT / org_height, NoteGlobal.CHAR_HEIGHT / org_height);
			}
			else
			{
				m.preScale(NoteGlobal.CHAR_WIDTH / org_width, NoteGlobal.CHAR_WIDTH / org_width);
				padding_y = NoteGlobal.CHAR_HEIGHT - (org_height * NoteGlobal.CHAR_WIDTH / org_width);
			}
		}
		else
		{
			padding_y = NoteGlobal.CHAR_HEIGHT - org_height;
		}
			
		m.preTranslate(-x, -y + padding_y);
		m_path.transform(m);
		m.reset();
		
		//���������ɂ���.
		m.preTranslate(0, 10f);
		m_path.transform(m);
		
		//�ύX��̃T�C�Y�ōĎ擾.
		m_path.computeBounds(bounds, false);
		org_width  = bounds.width();
		org_height = bounds.height();
	}

	
	//---------------------------------------------------------------
	
	public void moveToCursorX(float x)
	{
		Matrix m = new Matrix();
		m.preTranslate(x, 0);
		m_path.transform(m);
	}

	public void moveToCursorY(float y)
	{
		Matrix m = new Matrix();
		m.preTranslate(0, y);
		m_path.transform(m);
	}
}
