package com.androiddeveloper.webprog26.baller.engine.controller;

import android.util.Log;
import android.view.MotionEvent;

import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;
import com.androiddeveloper.webprog26.baller.engine.models.MovableGameObject;
import com.androiddeveloper.webprog26.baller.engine.models.Platform;

/**
 * Created by webpr on 20.04.2017.
 */

public class TouchController {

    private static final String TOUCH_TAG = "TouchController_TAG";


    public void handleTouchMotion(MotionEvent motionEvent, GameManager gameManager){
        Platform platform = gameManager.getPlatform();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:

                if(!gameManager.isPlaying()){
                    gameManager.startBall();
                }

                if(isLeftTouch(motionEvent, platform.getLeft())){
                        platform.setMoves(true);
                        platform.setFacing(MovableGameObject.LEFT);
                } else if(isRightTouch(motionEvent, platform.getRight())){
                        platform.setMoves(true);
                        platform.setFacing(MovableGameObject.RIGHT);

                } else {
                    platform.setxVelocity(0);
                    platform.setMoves(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                platform.setxVelocity(0);
                platform.setMoves(false);
                break;
        }
    }

    private boolean isLeftTouch(MotionEvent event, float left){
        return event.getX() < left;
    }

    private boolean isRightTouch(MotionEvent event, float right){
        return (int)event.getX() > right;
    }
}
