package cx.fam.tak0294.NoteBook.DrawObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class DrawObject
{
	public Paint m_paint;
	public Path  m_path;
	protected Bitmap m_privateBitmap = null;
	
	protected float org_width = 0f;
	protected float org_height = 0f;
	
	public RectF org_Rect;
	public float x = 0f;
	public float y = 0f;
	
	
	public DrawObject(Paint paint, Path path, RectF r)
	{
		org_Rect = r;
		m_paint = new Paint();
		if(paint != null)
			m_paint.set(paint);
		m_path  = new Path();
		if(path != null)
			m_path.set(path);

		x = r.left;
		y = r.top;
		
		resize();
	}
	
	protected abstract void resize();
	
	public abstract void setPath(Path path);
	public abstract void draw(Canvas canvas);
	public Bitmap getBitmap(){ return m_privateBitmap; };
	public float getOrgWidth(){ return org_width; };
}
