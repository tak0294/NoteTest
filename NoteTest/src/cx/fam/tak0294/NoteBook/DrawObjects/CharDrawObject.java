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
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void setPath(Path path) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void draw(Canvas canvas) {
		// TODO 自動生成されたメソッド・スタブ
		
		canvas.drawPath(m_path, m_paint);
	}

	@Override
	protected void resize() {
		// TODO 自動生成されたメソッド・スタブ
		
		//サイズを調べる.
		RectF bounds = new RectF();
		m_path.computeBounds(bounds, false);
		org_height = bounds.height();
		org_width  = bounds.width();
		
		//最初に移動させる.
		Matrix m = new Matrix();
		float padding_y = 0f;
		
		//----------------------------------------------
		//	文字の倍率を計算する.
		//----------------------------------------------
		if(org_height > NoteGlobal.CHAR_HEIGHT || (org_width > NoteGlobal.CHAR_WIDTH && org_width < NoteGlobal.CHAR_WIDTH*4))
		{
			//----------------------------------------------
			//	横幅がNoteGlobal.CHAR_WIDTHの４倍より大きい場合は横幅は処理しない.
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
		
		//下線揃えにする.
		m.preTranslate(0, 10f);
		m_path.transform(m);
		
		//変更後のサイズで再取得.
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
