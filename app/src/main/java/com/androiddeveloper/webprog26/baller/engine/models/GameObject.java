package com.androiddeveloper.webprog26.baller.engine.models;

import android.graphics.Canvas;

import com.androiddeveloper.webprog26.baller.engine.ObjectLocation;
import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;
import com.androiddeveloper.webprog26.baller.hitbox.HitBox;

/**
 * Created by webpr on 20.04.2017.
 */

public abstract class GameObject {

    private boolean active = false;
    private boolean visible = true;

    private float width;
    private float height;

    private float left;
    private float top;
    private float right;
    private float bottom;

    private ObjectLocation objectLocation = new ObjectLocation();
    private GameManager gameManager;
    private HitBox mHitBox = new HitBox();

    public GameObject(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setHitBox() {
        this.mHitBox.setLeft(getLeft());
        this.mHitBox.setTop(getTop());
        this.mHitBox.setRight(getRight());
        this.mHitBox.setBottom(getBottom());
    }

    public HitBox getHitBox() {
        return mHitBox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public abstract void draw(Canvas canvas);

    public ObjectLocation getObjectLocation() {
        return objectLocation;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
