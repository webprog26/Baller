package com.androiddeveloper.webprog26.baller.engine.manager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by webpr on 26.04.2017.
 */

public class PointsManager {

    private GameManager gameManager;

    public PointsManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int currentUserPoints = 0;
    private int maxPossiblePoints;

    public int getCurrentUserPoints() {
        return currentUserPoints;
    }

    public void setCurrentUserPoints(int currentUserPoints) {
        this.currentUserPoints = currentUserPoints;
    }

    public int getMaxPossiblePoints() {
        return maxPossiblePoints;
    }

    public void setMaxPossiblePoints(int maxPossiblePoints) {
        this.maxPossiblePoints = maxPossiblePoints;
    }

    public void draw(Canvas canvas, String pointsString){
        Paint paint = new Paint();

        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(getGameManager().getScreenHeight() / 40);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(pointsString + ": " + getCurrentUserPoints(), ((float)gameManager.getScreenWidth() - (gameManager.getScreenWidth() / 8)), 30, paint);
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
