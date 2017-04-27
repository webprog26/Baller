package com.androiddeveloper.webprog26.baller.engine.manager;

import android.graphics.PointF;

/**
 * Created by webpr on 26.04.2017.
 */

public class TextMessageManager extends MessageManager{

    public TextMessageManager(GameManager gameManager) {
        super(gameManager);
    }

    private boolean isGameOver = false;

    @Override
    protected int getTextSize() {
        return getGameManager().getScreenHeight() / 20;
    }

    @Override
    protected PointF getCoordinates() {
        return new PointF((float)getGameManager().getScreenWidth() / 2,
                (float)getGameManager().getScreenHeight() / 4);
    }

    @Override
    protected String getMessage() {
        if(isGameOver()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getGameManager().getGameOverString());
            stringBuilder.append(getGameManager().getGreetingString());
            return stringBuilder.toString();
        }
        return getGameManager().getGreetingString();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
