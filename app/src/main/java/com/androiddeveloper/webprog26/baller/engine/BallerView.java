package com.androiddeveloper.webprog26.baller.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.androiddeveloper.webprog26.baller.R;
import com.androiddeveloper.webprog26.baller.engine.controller.TouchController;
import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;
import com.androiddeveloper.webprog26.baller.engine.models.Brick;
import com.androiddeveloper.webprog26.baller.engine.models.FirstLevel;
import com.androiddeveloper.webprog26.baller.engine.models.Level;
import com.androiddeveloper.webprog26.baller.engine.models.Life;
import com.androiddeveloper.webprog26.baller.engine.models.SecondLevel;
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
        levels.add(new SecondLevel());

        this.mGameManager = new GameManager(context, screenWidth, screenHeight, levels);
        this.mTouchController = new TouchController();




    }

    private void update(){
        if(mGameManager.isPlaying()){
            mGameManager.updateMovableGameObjects(fps);
            mGameManager.checkBallCollisions();
        }
    }

    private void draw(){
        if(mSurfaceHolder.getSurface().isValid()){
            mCanvas = mSurfaceHolder.lockCanvas();
            mPaint.setColor(Color.argb(255, 64, 207, 255));
            mCanvas.drawColor(Color.argb(255, 64, 207, 255));

            mGameManager.getPlatform().draw(mCanvas);
            mGameManager.getBall().draw(mCanvas);

            float lifeStartX = 0;

            for(int i = 0; i < mGameManager.getLivesCountManager().getLives().size(); i++){
                Life life = mGameManager.getLivesCountManager().getLives().get(i);
                if(life.isVisible()){
                    life.setLeft(lifeStartX);
                    life.draw(mCanvas);
                    lifeStartX += life.getWidth() + 6;
                }
            }

            mGameManager.getPointsManager().draw(mCanvas, mGameManager.getContext().getString(R.string.points));

            float brickStartX = 0;
            float brickStartY = getScreenHeight() / 20;

                for(int i = 0; i <  mGameManager.getBricksObjects().size(); i++){
                        UnmovableGameObject collidedUnmovableGameObject = mGameManager.getBricksObjects().get(i);
                        switch (collidedUnmovableGameObject.getType()){
                            case Brick.BRICK_TYPE:
                                Brick brick = (Brick) collidedUnmovableGameObject;
                                brick.setLeft(brickStartX);
                                brick.setTop(brickStartY);
                                brick.setRight(brickStartX + brick.getWidth());
                                brick.setBottom(brickStartY + brick.getHeight());
                                brick.setHitBox();

                                float singleUnmovableObjectWidth = (int) brick.getWidth();
                                float singleUnmovableObjectHeight = (int) brick.getHeight();
                                if(brick.isVisible()){
//                            mCanvas.drawBitmap(brick.getBitmap(), brickStartX, brickStartY, mPaint);
                                    brick.draw(mCanvas);
                                }
                                brickStartX += singleUnmovableObjectWidth;
                                if(brickStartX == getScreenWidth()){
                                    brickStartX = 0;
                                    brickStartY += singleUnmovableObjectHeight + 1;
                                }
                                break;
                        }
                }

            if(!mGameManager.isPlaying()){
                if(mGameManager.isGameOver()){
                    mGameManager.showGameOverMessage(mCanvas);
                } else {
                    mGameManager.showGreetingMessage(mCanvas);
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
