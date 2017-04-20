package com.androiddeveloper.webprog26.baller.engine.manager;

import com.androiddeveloper.webprog26.baller.engine.models.Ball;
import com.androiddeveloper.webprog26.baller.engine.models.Platform;

/**
 * Created by webpr on 20.04.2017.
 */

public class GameManager {

    private int mScreenWidth;
    private int mScreenHeight;
    private Platform mPlatform;
    private Ball mBall;

    public GameManager(int screenWidth, int screenHeight) {
        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;
        initGame();
    }

    private void initGame(){
        this.mPlatform = new Platform(this);
        this.mBall = new Ball(this);
    }

    public void startBall(){
        mBall.start();
    }

    private boolean isPlaying;

    public boolean isPlaying() {
        return isPlaying;
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public Ball getBall() {
        return mBall;
    }
}
