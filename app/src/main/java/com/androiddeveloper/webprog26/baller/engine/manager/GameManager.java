package com.androiddeveloper.webprog26.baller.engine.manager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.androiddeveloper.webprog26.baller.R;
import com.androiddeveloper.webprog26.baller.engine.models.Ball;
import com.androiddeveloper.webprog26.baller.engine.models.Brick;
import com.androiddeveloper.webprog26.baller.engine.models.Level;
import com.androiddeveloper.webprog26.baller.engine.models.MovableGameObject;
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
    private Context mContext;
    private Level currentLevel;
    private LivesCountManager livesCountManager;
    private PointsManager pointsManager;

    private TextMessageManager textMessageManager;

    private static final float BALL_START_SPEED = 200;

    private ArrayList<UnmovableGameObject> bricksObjects = new ArrayList<>();

    public GameManager(Context context, int screenWidth, int screenHeight, ArrayList<Level> levels) {
        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;
        this.mLevels = levels;
        this.mContext = context;

        this.textMessageManager = new TextMessageManager(screenWidth, screenHeight);
        this.pointsManager = new PointsManager(this);

        initGame();
    }

    private void initGame(){
        this.livesCountManager = new LivesCountManager(this);

        loadBricksObjects();
        this.mPlatform = new Platform(this);
        this.mBall = new Ball(this);
        mBall.setFacingAngle(45);
        mBall.setSpeed(BALL_START_SPEED);
    }

    public void startBall(){
        setPlaying(true);
        mBall.start();
    }

    private boolean isPlaying;
    private boolean isGameOver;

    public boolean isPlaying() {
        return isPlaying;
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    private void setPlaying(boolean playing) {
        if(isPlaying()){
            if(isGameOver){
                setGameOver(false);
            }
        }
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

    private ArrayList<Level> getLevels() {
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

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    private void loadBricksObjects(){
        int bricksCount = 0;
        if(getCurrentLevel() != null){
            ArrayList<String> levelData = getCurrentLevel().getLevelData();
            if(levelData != null){
                for(int i = 0; i < getCurrentLevel().getLevelData().size(); i++){
                    for(int j = 0; j < getCurrentLevel().getLevelData().get(i).length(); j++){
                        switch (getBitmapIndex(getCurrentLevel().getLevelData().get(i).charAt(j))){
                            case 0:
                                Brick brick = new Brick(this);
                                brick.prepareBitmap(Brick.BRICK_BITMAP_NAME, brick.getWidth(), brick.getHeight());
                                bricksObjects.add(brick);
                                bricksCount++;
                                break;
                        }
                    }
                }
                getPointsManager().setMaxPossiblePoints(bricksCount);
            }
        }
    }

    public ArrayList<UnmovableGameObject> getBricksObjects() {
        return bricksObjects;
    }

    private void setUnmovableGameObjectInvisibility(int index){
        getBricksObjects().get(index).setVisible(false);
    }

    private void reset(){
        setPlaying(false);
        mPlatform.reset();
        mBall.reset();

        int livesCount = livesCountManager.getLivesCount();

        if(livesCount > 0){
            livesCountManager.loseLife();
        }

        if(livesCount == 0){
            setGameOver(true);
        }
    }

    public void setBallSpeed(float ballSpeed){
        mBall.setSpeed(ballSpeed);
    }

    public Context getContext() {
        return mContext;
    }

    public void updateMovableGameObjects(long fps){
        updatePlatform(fps);
        updateBall(fps);
    }

    private void updatePlatform(long fps){
        Platform platform = getPlatform();
        platform.update(fps);
        platform.getObjectLocation().updateLocation(
                platform.getLeft(),
                platform.getRight());
    }

    private void updateBall(long fps){
        Ball ball = getBall();
        ball.update(fps);
        ball.getObjectLocation().updateLocation(
                ball.getLeft(),
                ball.getTop(),
                ball.getRight(),
                ball.getBottom());
    }

    private void checkBallCollisionsWithPlatform(){
        Ball ball = getBall();
        Platform platform = getPlatform();

        if(ball.getObjectFacing().getVerticalFacing() == MovableGameObject.FACING_BOTTOM
                && ball.getHitBox().intersects(platform.getHitBox())){
            Log.i(GAME_MANAGER_TAG, "ball touched the platform");

            inverseBallHorizontalFacing();

            ball.getObjectFacing().setVerticalFacing(MovableGameObject.FACING_TOP);

           inverseBallAngle();

        }

        if(ball.getObjectFacing().getVerticalFacing() == MovableGameObject.FACING_BOTTOM
                && !ball.getHitBox().intersects(platform.getHitBox())
                && ball.getBottom() >= getScreenHeight()){
            reset();
        }
    }

    public void checkBallCollisions(){
        checkBallCollisionsWithPlatform();
        checkBallCollisionsWithBricks();
    }

    private void checkBallCollisionsWithBricks(){
        Ball ball = getBall();

        for(int i = 0; i < getBricksObjects().size(); i++){
            UnmovableGameObject collidedUnmovableGameObject = getBricksObjects().get(i);
            switch (collidedUnmovableGameObject.getType()){
                case Brick.BRICK_TYPE:
                    Brick brick = (Brick) collidedUnmovableGameObject;
                    if(ball.getHitBox().intersects(brick.getHitBox())){
                        if(brick.isVisible()){
                            PointsManager pointsManager = getPointsManager();

                            pointsManager.setCurrentUserPoints(pointsManager.getCurrentUserPoints() + 1);

                            if(pointsManager.getCurrentUserPoints() == pointsManager.getMaxPossiblePoints()){
                                //we've reached the end of this level
                                int levelIndex = getLevelIndex();
                                if(!(getLevels().size() < (levelIndex + 2))){
                                    setLevelIndex(levelIndex + 1);
                                } else {
                                    setLevelIndex(0);
                                }

                                setPlaying(false);
                                getBall().reset();
                                getPlatform().reset();
                                int currentLevelMaxPossiblePoints = pointsManager.getMaxPossiblePoints();
                                loadBricksObjects();
                                pointsManager.setMaxPossiblePoints(currentLevelMaxPossiblePoints + pointsManager.getMaxPossiblePoints());
                            }
                            Log.i(GAME_MANAGER_TAG, "points " + getPointsManager().getCurrentUserPoints());

                            setUnmovableGameObjectInvisibility(i);
                            inverseBallVerticalFacing();

                            inverseBallHorizontalFacing();

                            inverseBallAngle();

                            if(ball.getObjectFacing().getVerticalFacing() == MovableGameObject.FACING_TOP){
                                Log.i(GAME_MANAGER_TAG, "FACING_TOP");
                            } else if(ball.getObjectFacing().getVerticalFacing() == MovableGameObject.FACING_BOTTOM){
                                Log.i(GAME_MANAGER_TAG, "FACING_BOTTOM");
                            } else {
                                Log.i(GAME_MANAGER_TAG, "vertical FACING is undefined");
                            }
                            Log.i(GAME_MANAGER_TAG, "facing angle is " + ball.getFacingAngle());
                        }
                    }
                    break;
            }

        }
    }

    private void inverseBallAngle(){
        Ball ball = getBall();

        float ballTop = ball.getTop();
        float ballLeft = ball.getLeft();

        if(ball.getObjectLocation().getTop() == ball.getObjectLocation().getLeft()){
            ball.setFacingAngle(45);
        } else if(ballTop > ballLeft){
            ball.setFacingAngle(45 + (45 / (ball.getObjectLocation().getTop() - ball.getObjectLocation().getLeft())));
        } else {
            ball.setFacingAngle(45 - (45 / (ball.getObjectLocation().getLeft() - ball.getObjectLocation().getTop())));
        }
    }

    private void inverseBallVerticalFacing(){
        Ball ball = getBall();

        if(ball.getObjectFacing().getVerticalFacing() == MovableGameObject.FACING_TOP){
            ball.getObjectFacing().setVerticalFacing(MovableGameObject.FACING_BOTTOM);
        } else {
            ball.getObjectFacing().setVerticalFacing(MovableGameObject.FACING_TOP);
        }
        ball.setyVelocity((float)-(ball.getSpeed() * Math.sin(Math.toRadians(ball.getFacingAngle()))));
    }

    private void inverseBallHorizontalFacing(){

        Ball ball = getBall();

        if(ball.getObjectFacing().getHorizontalFacing() == MovableGameObject.FACING_LEFT){
            ball.setxVelocity((float)-(ball.getSpeed() * Math.cos(Math.toRadians( ball.getFacingAngle()))));
        }

        if(ball.getObjectFacing().getHorizontalFacing() == MovableGameObject.FACING_RIGHT){
            ball.setxVelocity((float)( ball.getSpeed() * Math.cos(Math.toRadians( ball.getFacingAngle()))));
        }
    }

    private String getGreetingString(){
        return getContext().getString(R.string.touch_to_play, (getLevelIndex() + 1));
    }

    private String getGameOverString(){
        return getContext().getString(R.string.game_over);
    }

    public void showGreetingMessage(Canvas canvas){
        textMessageManager.printUserMessage(getGreetingString(), canvas);
    }

    public void showGameOverMessage(Canvas canvas){
        textMessageManager.printUserMessage(getGameOverString() + "\n" + getGreetingString(), canvas);
    }

    public LivesCountManager getLivesCountManager() {
        return livesCountManager;
    }

    public void resetUI(){
        livesCountManager.reset();
        resetBricks();
    }

    private void resetBricks(){
        for(UnmovableGameObject unmovableGameObject: getBricksObjects()){
            if(!unmovableGameObject.isVisible()){
                unmovableGameObject.setVisible(true);
            }
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public PointsManager getPointsManager() {
        return pointsManager;
    }

    public void setPointsManager(PointsManager pointsManager) {
        this.pointsManager = pointsManager;
    }
}
