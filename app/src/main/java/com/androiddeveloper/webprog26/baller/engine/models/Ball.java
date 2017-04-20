package com.androiddeveloper.webprog26.baller.engine.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.androiddeveloper.webprog26.baller.engine.ObjectLocation;
import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 20.04.2017.
 */

public class Ball extends MovableGameObject {

    private float mRadius;

    private static final int BALL_X_VELOCITY = 200;
    private static final int BALL_Y_VELOCITY = 200;

    public Ball(GameManager gameManager) {
        //Platform can only move on X-axis
        super(gameManager);
        setRadius(gameManager.getScreenHeight() / 80);
        setWidth(getRadius() * 2);
        setHeight(getRadius() * 2);
        ObjectLocation location = getObjectLocation();
        setLeft(gameManager.getScreenWidth() / 2);
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
        paint.setColor(Color.argb(255, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getLeft(), getTop(), getRadius(), paint);
    }

    @Override
    public boolean couldMoveHorizontally() {
        return true;
    }

    @Override
    public boolean couldMoveVertically() {
        return true;
    }

    @Override
    public void update(long fps) {
        switch (getFacing()){
            case TOP:
                if(getTop() <= 0){
                    setFacing(BOTTOM);
                }
                setyVelocity(-BALL_Y_VELOCITY);
                break;
            case BOTTOM:
                if(getBottom() >= getGameManager().getScreenHeight()){
                    setFacing(TOP);
                }
                setyVelocity(BALL_Y_VELOCITY);
                break;
        }

        if(isMoves()){
            if(getxVelocity() > 0 && getRight() >= getGameManager().getScreenWidth()){
                setxVelocity(-BALL_X_VELOCITY);
            }

            if(getxVelocity() < 0 && getLeft() <= 0){
                setxVelocity(BALL_X_VELOCITY);
            }
            move(fps);
            setHitBox();
        }
    }

    @Override
    protected void move(long fps) {
        if(getyVelocity() != 0){
            float top = getTop();
            float bottom = getBottom();

            top += getyVelocity() / fps;
            bottom += getyVelocity() / fps;

            setTop(top);
            setBottom(bottom);

            if(getxVelocity() != 0){
                float left = getLeft();
                float right = getRight();

                left += getxVelocity() / fps;
                right += getxVelocity() / fps;

                setLeft(left);
                setRight(right);
            }
        }
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public void start(){
        if(!isMoves()){
            setMoves(true);
            setxVelocity(BALL_X_VELOCITY);
            setyVelocity(BALL_Y_VELOCITY);
            setFacing(TOP);
        }
    }
}