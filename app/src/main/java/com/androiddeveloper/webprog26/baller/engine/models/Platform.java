package com.androiddeveloper.webprog26.baller.engine.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.androiddeveloper.webprog26.baller.engine.ObjectLocation;
import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 20.04.2017.
 */

public class Platform extends MovableGameObject {

    private static final float PLATFORM_X_VELOCITY = 200;

    private static final String PLATFORM_TAG = "Platform_TAG";

    public Platform(GameManager gameManager) {
        super(gameManager);
        //Platform can only move on X-axis
        setWidth(gameManager.getScreenWidth() / 10);
        setHeight(gameManager.getScreenHeight() / 80);
        ObjectLocation location = getObjectLocation();
        setLeft((gameManager.getScreenWidth() / 2) - (getWidth() / 2));
        setTop(gameManager.getScreenHeight() - getHeight());
        setRight(getLeft() + getWidth());
        setBottom(getTop() + getHeight());
        location.setLeft(getLeft());
        location.setTop(getTop());
        location.setRight(getRight());
        location.setBottom(getBottom());

        setHitBox();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(
                getLeft(),
                getTop(),
                getRight(),
                getBottom(),
                paint);
    }

    @Override
    public void update(long fps) {
            switch (getFacing()){
                case LEFT:
                    if((int) getLeft() <= 0){
                        stopMoving();
                        break;
                    }
                    setxVelocity(-PLATFORM_X_VELOCITY);
                    Log.i(PLATFORM_TAG, "left " + getLeft());
                    break;
                case RIGHT:
                    if((int) getRight() >= getGameManager().getScreenWidth()){
                        stopMoving();
                        break;
                    }
                    setxVelocity(PLATFORM_X_VELOCITY);
                    Log.i(PLATFORM_TAG, "right " + getRight());
                    break;
            }
        if(isMoves()){
            move(fps);
            setHitBox();
        }
    }

    @Override
    protected void move(long fps) {
        if(getxVelocity() != 0){
            float left = getLeft();
            float right = getRight();

            left += getxVelocity() / fps;
            right += getxVelocity() / fps;

            setLeft(left);
            setRight(right);
        }
    }

    @Override
    public boolean couldMoveHorizontally() {
        return true;
    }

    @Override
    public boolean couldMoveVertically() {
        return false;
    }

    private void stopMoving(){
        setxVelocity(0);
        setMoves(false);
    }
}
