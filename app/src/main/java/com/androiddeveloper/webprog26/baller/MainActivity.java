package com.androiddeveloper.webprog26.baller;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.androiddeveloper.webprog26.baller.engine.BallerView;

public class MainActivity extends Activity {

    private BallerView mBallerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        mBallerView = new BallerView(this, resolution.x, resolution.y);

        setContentView(mBallerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBallerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBallerView.pause();
    }
}
