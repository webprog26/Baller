package com.androiddeveloper.webprog26.baller.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.androiddeveloper.webprog26.baller.engine.controller.TouchController;
import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;
import com.androiddeveloper.webprog26.baller.engine.models.Ball;
import com.androiddeveloper.webprog26.baller.engine.models.FirstLevel;
import com.androiddeveloper.webprog26.baller.engine.models.Level;
import com.androiddeveloper.webprog26.baller.engine.models.MovableGameObject;
import com.androiddeveloper.webprog26.baller.engine.models.Platform;
import com.androiddeveloper.webprog26.baller.engine.models.UnmovableGameObject;

import java.util.ArrayList;

/**
 * Created by webpr on 20.04.2017.
 */

public class BallerView extends SurfaceView implements Runnable{

    private static final String BALLER_VIEW_TAG = "BallerView_Tag";

    private boolean running;
    private Thread mGameThread = null;
    private int mScreenWidth;
    private int mScreenHeight;

    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    long startFrameTime;
    long timeThisFrame;
    long fps;

    private GameManager mGameManager;
    private TouchController mTouchController;

    public BallerView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.mSurfaceHolder = getHolder();
        this.mPaint = new Paint();
        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;

        ArrayList<Level> levels = new ArrayList<>();
        levels.add(new FirstLevel());

        this.mGameManager = new GameManager(context, screenWidth, screenHeight, levels);
        this.mTouchController = new TouchController();




    }

    private void update(){
        if(mGameManager.isPlaying()){
            Platform platform = mGameManager.getPlatform();
            platform.update(fps);
            platform.getObjectLocation().updateLocation(
                    platform.getLeft(),
                    platform.getRight());

            Ball ball = mGameManager.getBall();
            ball.update(fps);
            ball.getObjectLocation().updateLocation(
                    ball.getLeft(),
                    ball.getTop(),
                    ball.getRight(),
                    ball.getBottom());

            if(ball.getFacing() == MovableGameObject.BOTTOM
                    && ball.getHitBox().intersects(platform.getHitBox())){
                Log.i(BALLER_VIEW_TAG, "ball touched the platform");
                ball.setxVelocity(-ball.getxVelocity());
                ball.setFacing(MovableGameObject.TOP);
            }

            if(ball.getFacing() == MovableGameObject.BOTTOM
                    && !ball.getHitBox().intersects(platform.getHitBox())
                    && ball.getBottom() >= getScreenHeight()){
                mGameManager.reset();
            }

            for(int i = 0; i < mGameManager.getUnmovableGameObjects().size(); i++){
                UnmovableGameObject unmovableGameObject = mGameManager.getUnmovableGameObjects().get(i);
                if(ball.getHitBox().intersects(unmovableGameObject.getHitBox())){
                    Log.i(BALLER_VIEW_TAG, "ball touched object with index " + i);
                    if(unmovableGameObject.isVisible()){
                        if(ball.getFacing() == MovableGameObject.TOP){
                            ball.setFacing(MovableGameObject.BOTTOM);
                        } else {
                            ball.setFacing(MovableGameObject.TOP);
                        }
                        ball.setyVelocity(-ball.getyVelocity());
                        mGameManager.setUnmovableGameObjectInvisibility(i);
                    }

                }
            }
        }
    }

    private void draw(){
        if(mSurfaceHolder.getSurface().isValid()){
            mCanvas = mSurfaceHolder.lockCanvas();
            mPaint.setColor(Color.argb(255, 64, 207, 255));
            mCanvas.drawColor(Color.argb(255, 64, 207, 255));

            mGameManager.getPlatform().draw(mCanvas);
            mGameManager.getBall().draw(mCanvas);


            float singleUnmovableObjectWidth;
            float singleUnmovableObjectHeight;
            float startX = 0;
            float startY = 10;

                for(int i = 0; i <  mGameManager.getUnmovableGameObjects().size(); i++){
                        UnmovableGameObject unmovableGameObject = mGameManager.getUnmovableGameObjects().get(i);
                        unmovableGameObject.setLeft(startX);
                        unmovableGameObject.setTop(startY);
                        unmovableGameObject.setRight(startX + unmovableGameObject.getWidth());
                        unmovableGameObject.setBottom(startY + unmovableGameObject.getHeight());
                        unmovableGameObject.setHitBox();

                        singleUnmovableObjectWidth = (int) unmovableGameObject.getWidth();
                        singleUnmovableObjectHeight = (int) unmovableGameObject.getHeight();
                        if(unmovableGameObject.isVisible()){
                            mCanvas.drawBitmap(unmovableGameObject.getBitmap(), startX, startY, mPaint);
                        }
                        startX += singleUnmovableObjectWidth;
                        if(startX == getScreenWidth()){
                            startX = 0;
                            startY += singleUnmovableObjectHeight + 1;
                        }
                }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public void run() {
        while(running){
            startFrameTime = System.currentTimeMillis();
        update();
        draw();

        // Calculate the fps this frame
        // We can then use the result to
        // time animations and more.
        timeThisFrame = System.currentTimeMillis() - startFrameTime;
        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame;
        }
    }
    }

    public void resume(){
        running = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    public void pause(){
        running = false;
        try {
            mGameThread.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mGameManager != null){
            mTouchController.handleTouchMotion(event, mGameManager);
        }
        return true;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public void setScreenHeight(int mScreenHeight) {
        this.mScreenHeight = mScreenHeight;
    }
}
