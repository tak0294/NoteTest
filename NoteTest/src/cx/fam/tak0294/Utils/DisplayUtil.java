package cx.fam.tak0294.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtil
{
	public static Rect getScreenRect(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		Rect outSize = new Rect();
		disp.getRectSize(outSize);
		return outSize;
	}
}
