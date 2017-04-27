package com.androiddeveloper.webprog26.baller.engine.manager;

import android.graphics.PointF;

/**
 * Created by webpr on 26.04.2017.
 */

public class PointsManager extends MessageManager{


    public PointsManager(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    protected int getTextSize() {
        return getGameManager().getScreenHeight() / 40;
    }

    @Override
    protected PointF getCoordinates() {
        return new PointF(((float)getGameManager().getScreenWidth() - (getGameManager().getScreenWidth() / 8)),
                         30);
    }

    @Override
    protected String getMessage() {
        return getGameManager().getPointsString();
    }

    private int currentUserPoints = 0;
    private int maxPossiblePoints;

    int getCurrentUserPoints() {
        return currentUserPoints;
    }

    void setCurrentUserPoints(int currentUserPoints) {
        this.currentUserPoints = currentUserPoints;
    }

    int getMaxPossiblePoints() {
        return maxPossiblePoints;
    }

    void setMaxPossiblePoints(int maxPossiblePoints) {
        this.maxPossiblePoints = maxPossiblePoints;
    }
}
