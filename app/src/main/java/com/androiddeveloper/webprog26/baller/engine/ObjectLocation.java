package com.androiddeveloper.webprog26.baller.engine;

import android.util.Log;

/**
 * Created by webpr on 20.04.2017.
 */

public class ObjectLocation {

    private static final String LOCATION_TAG = "Location_TAG";

    private float objectLocationLeft;
    private float objectLocationTop;
    private float objectLocationRight;
    private float objectLocationBottom;

    public float getTop() {
        return objectLocationTop;
    }

    public void setTop(float objectLocationTop) {
        this.objectLocationTop = objectLocationTop;
    }

    public float getBottom() {
        return objectLocationBottom;
    }

    public void setBottom(float objectLocationBottom) {
        this.objectLocationBottom = objectLocationBottom;
    }

    public float getLeft() {
        return objectLocationLeft;
    }

    public void setLeft(float objectLocationLeft) {
        this.objectLocationLeft = objectLocationLeft;
    }

    public float getRight() {
        return objectLocationRight;
    }

    public void setRight(float objectLocationRight) {
        this.objectLocationRight = objectLocationRight;
    }

    public void updateLocation(float left, float right){
        Log.i(LOCATION_TAG, "left " + left + ", right " + right);
        setLeft(left);
        setRight(right);
    }

    public void updateLocation(float left, float top, float right, float bottom){
        Log.i(LOCATION_TAG, "left " + left + ", right " + right);
        setLeft(left);
        setTop(top);
        setRight(right);
        setBottom(bottom);
    }
}
