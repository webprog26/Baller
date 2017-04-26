package com.androiddeveloper.webprog26.baller.engine.manager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by webpr on 26.04.2017.
 */

public class TextMessageManager {

    private int screenWidth;
    private int screenHeight;

    private Paint paint;

    public TextMessageManager(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(screenHeight / 20);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    void printUserMessage(String userMessage, Canvas canvas){
        PointF userMessageCoordinates = getUserMessageCoordinates();
        canvas.drawText(userMessage, userMessageCoordinates.x, userMessageCoordinates.y, getPaint());
    }

    private PointF getUserMessageCoordinates(){
        PointF userMessageCoordinates = new PointF();
        userMessageCoordinates.x = getScreenWidth() / 2;
        userMessageCoordinates.y = getScreenHeight() / 4;

        return userMessageCoordinates;
    }

    private float getScreenWidth() {
        return (float)screenWidth;
    }

    private float getScreenHeight() {
        return (float) screenHeight;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
