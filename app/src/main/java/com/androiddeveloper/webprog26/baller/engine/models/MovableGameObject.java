package com.androiddeveloper.webprog26.baller.engine.models;

import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 20.04.2017.
 */

public abstract class MovableGameObject extends GameObject {

    public MovableGameObject(GameManager gameManager) {
        super(gameManager);
    }

    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int TOP = 2;
    public static final int BOTTOM = -2;

    private float xVelocity;
    private float yVelocity;
    private int facing;
    private boolean moves = false;

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

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
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
}
