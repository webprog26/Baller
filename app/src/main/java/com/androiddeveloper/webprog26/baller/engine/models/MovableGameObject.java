package com.androiddeveloper.webprog26.baller.engine.models;

import com.androiddeveloper.webprog26.baller.engine.ObjectFacing;
import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 20.04.2017.
 */

public abstract class MovableGameObject extends GameObject {

    public static final int FACING_LEFT = 1;
    public static final int FACING_RIGHT = -1;
    public static final int FACING_TOP = 2;
    public static final int FACING_BOTTOM = -2;

    private float xVelocity;
    private float yVelocity;
    private boolean moves = false;

    protected float speed;
    protected ObjectFacing objectFacing;

    public MovableGameObject(GameManager gameManager) {
        super(gameManager);
        objectFacing = new ObjectFacing();
    }


    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        if(couldMoveHorizontally() && isMoves()){
            this.xVelocity = xVelocity;
        }
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        if(couldMoveVertically() && isMoves()){
            this.yVelocity = yVelocity;
        }
    }

    public boolean isMoves() {
        return moves;
    }

    public void setMoves(boolean moves) {
        if(!couldMoveHorizontally() && !couldMoveVertically()){
            this.moves = false;
        }
        this.moves = moves;
    }

    public abstract boolean couldMoveHorizontally();
    public abstract boolean couldMoveVertically();

    public abstract void update(long fps);

    protected abstract void move(long fps);

    public void reset(){
        setxVelocity(0);
        setyVelocity(0);
        setMoves(false);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ObjectFacing getObjectFacing() {
        return objectFacing;
    }
}
