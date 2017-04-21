package com.androiddeveloper.webprog26.baller.engine.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.androiddeveloper.webprog26.baller.engine.models.Ball;
import com.androiddeveloper.webprog26.baller.engine.models.Brick;
import com.androiddeveloper.webprog26.baller.engine.models.GameObject;
import com.androiddeveloper.webprog26.baller.engine.models.Level;
import com.androiddeveloper.webprog26.baller.engine.models.Platform;
import com.androiddeveloper.webprog26.baller.engine.models.UnmovableGameObject;

import java.util.ArrayList;

/**
 * Created by webpr on 20.04.2017.
 */

public class GameManager {

    private static final String GAME_MANAGER_TAG = "GameManager_TAG";

    private int mScreenWidth;
    private int mScreenHeight;
    private Platform mPlatform;
    private Ball mBall;
    private ArrayList<Level> mLevels;
    private int levelIndex = 0;

    private ArrayList<UnmovableGameObject> unmovableGameObjects = new ArrayList<>();

    public GameManager(Context context, int screenWidth, int screenHeight, ArrayList<Level> levels) {
        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;
        this.mLevels = levels;
        loadUnmovableGameObjects(context);

        for(UnmovableGameObject unmovableGameObject: unmovableGameObjects){
            if(unmovableGameObject.getBitmap() != null){
                Log.i(GAME_MANAGER_TAG, unmovableGameObject.getBitmap().toString());
            } else {
                Log.i(GAME_MANAGER_TAG, "unmovableGameObject.getBitmap() is NULL");
            }

        }
        initGame();
    }

    private void initGame(){
        this.mPlatform = new Platform(this);
        this.mBall = new Ball(this);
    }

    public void startBall(){
        setPlaying(true);
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

    public ArrayList<Level> getLevels() {
        return mLevels;
    }

    private Level getCurrentLevel(){
        ArrayList<Level> levels = getLevels();
        if(levels != null && levels.size() > 0){
            int levelIndex = getLevelIndex();
            if(!(levelIndex < 0) && !(levelIndex > (levels.size() - 1))){
                return levels.get(levelIndex);
            }
        }
        return null;
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
    }

    private int getBitmapIndex(char c){
        int index = -1;
        switch (c){
            case 'b':
                index = 0;
                break;
        }
        return index;
    }

    private void loadUnmovableGameObjects(Context context){
        for(int i = 0; i < getCurrentLevel().getLevelData().size(); i++){
            for(int j = 0; j < getCurrentLevel().getLevelData().get(i).length(); j++){
                switch (getBitmapIndex(getCurrentLevel().getLevelData().get(i).charAt(j))){
                    case 0:
                        Brick brick = new Brick(this);
                        brick.prepareBitmap(context, Brick.BRICK_BITMAP_NAME, brick.getWidth(), brick.getHeight());
                        unmovableGameObjects.add(brick);
                        break;
                }
            }
        }
    }

    public ArrayList<UnmovableGameObject> getUnmovableGameObjects() {
        return unmovableGameObjects;
    }

    public void setUnmovableGameObjectInvisibility(int index){
        getUnmovableGameObjects().get(index).setVisible(false);
    }

    public void reset(){
        setPlaying(false);
        mPlatform.reset();
        mBall.reset();
    }
}
