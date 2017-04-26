package com.androiddeveloper.webprog26.baller.engine.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 26.04.2017.
 */

public class Life extends UnmovableGameObject{

    public static final String LIFE_BITMAP_NAME = "life";
    public static final char LIFE_TYPE = 'l';

    public Life(GameManager gameManager) {
        super(gameManager);
        setWidth(gameManager.getScreenWidth() / 8);
        setHeight(gameManager.getScreenHeight() / 24);
        setTop(gameManager.getScreenHeight() / 110);
        setType(LIFE_TYPE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmap(), getLeft(), getTop(),getPaint());
    }
}
