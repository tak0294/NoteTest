package cx.fam.tak0294.NoteTest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.app.NavUtils;
import cx.fam.tak0294.NoteBook.*;
import cx.fam.tak0294.NoteBook.Note.NoteBook;
import cx.fam.tak0294.NoteBook.Note.NoteShelf;
import cx.fam.tak0294.NoteBook.Note.NoteView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        NoteShelf ns = new NoteShelf(this);
        NoteBook nb = ns.addNoteBook();
        NoteView nv = nb.addNoteView();
        nb.setCurrentNoteView(nv);
        setContentView(nb.getMainFrame());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
