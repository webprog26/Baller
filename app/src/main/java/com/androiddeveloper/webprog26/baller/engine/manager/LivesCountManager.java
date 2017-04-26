package com.androiddeveloper.webprog26.baller.engine.manager;

import com.androiddeveloper.webprog26.baller.engine.models.Life;

import java.util.ArrayList;

/**
 * Created by webpr on 26.04.2017.
 */

public class LivesCountManager {

    private static final int LIVES_COUNT = 3;

    private int livesCount = LIVES_COUNT;

    private ArrayList<Life> lives = new ArrayList<>();

    LivesCountManager(GameManager gameManager) {
        loadLivesArray(gameManager);
    }

    private void loadLivesArray(GameManager gameManager){
        for(int i  = 0; i < LIVES_COUNT; i++){
            Life life = new Life(gameManager);
            life.prepareBitmap(Life.LIFE_BITMAP_NAME, life.getWidth(), life.getHeight());
            lives.add(i, life);
        }
    }


    public ArrayList<Life> getLives() {
        return lives;
    }

    void loseLife(){
        setLivesCount(getLivesCount() - 1);
        getLives().get(getLivesCount()).setVisible(false);
    }

    int getLivesCount() {
        return livesCount;
    }

    private void setLivesCount(int livesCount) {
        this.livesCount = livesCount;
    }

    void reset(){
        for(Life life: getLives()){
            life.setVisible(true);
        }
        setLivesCount(LIVES_COUNT);
    }
}
