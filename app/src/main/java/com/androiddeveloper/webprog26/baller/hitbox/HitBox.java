package com.androiddeveloper.webprog26.baller.hitbox;

/**
 * Created by webpr on 20.04.2017.
 */

public class HitBox {

    float top;
    float left;
    float bottom;
    float right;
    float height;

    public boolean intersects(HitBox hitBox){
        boolean hit = false;

        if(this.right > hitBox.left && this.left < hitBox.right){
            // Intersecting on X-axis
            if(this.top < hitBox.bottom && this.bottom > hitBox.top){
                hit = true;
            }
        }
        return hit;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
