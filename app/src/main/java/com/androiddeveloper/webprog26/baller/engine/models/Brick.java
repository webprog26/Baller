package com.androiddeveloper.webprog26.baller.engine.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 21.04.2017.
 */

public class Brick extends UnmovableGameObject {

    public static final String BRICK_BITMAP_NAME = "brick";
    public static final char BRICK_TYPE = 'b';

    public Brick(GameManager gameManager) {
        super(gameManager);
        setWidth(gameManager.getScreenWidth() / 10);
        setHeight(gameManager.getScreenHeight() / 80);
        setType(BRICK_TYPE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmap(), getLeft(), getTop(),getPaint());
    }
}
