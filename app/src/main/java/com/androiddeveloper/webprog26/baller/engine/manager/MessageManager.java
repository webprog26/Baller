package com.androiddeveloper.webprog26.baller.engine.manager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by webpr on 27.04.2017.
 */

public abstract class MessageManager {

    private GameManager gameManager;
    private Paint paint;

    public MessageManager(GameManager gameManager) {
        this.gameManager = gameManager;
        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(getTextSize());
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Paint getPaint() {
        return paint;
    }

    public void draw(Canvas canvas){
        PointF userMessageCoordinates = getCoordinates();
        canvas.drawText(getMessage(), userMessageCoordinates.x, userMessageCoordinates.y, getPaint());
    }

    protected abstract int getTextSize();

    protected abstract PointF getCoordinates();

    protected abstract String getMessage();
}
