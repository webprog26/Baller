package com.androiddeveloper.webprog26.baller.engine.models;

import java.util.ArrayList;

/**
 * Created by webpr on 21.04.2017.
 */

public class FirstLevel extends Level {

    public static char BRICK = 'b';

    public FirstLevel() {
        levelData = new ArrayList<>();
        levelData.add("bbbbbbbbbb");
        levelData.add("bbbbbbbbbb");
        levelData.add("bbbbbbbbbb");
        levelData.add("bbbbbbbbbb");
        levelData.add("bbbbbbbbbb");
        levelData.add("bbbbbbbbbb");
    }
}
