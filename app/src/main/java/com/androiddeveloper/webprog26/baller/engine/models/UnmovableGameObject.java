package com.androiddeveloper.webprog26.baller.engine.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Toast;

import com.androiddeveloper.webprog26.baller.engine.manager.GameManager;

/**
 * Created by webpr on 21.04.2017.
 */

public abstract class UnmovableGameObject extends GameObject {

    private Bitmap bitmap;
    private char type;
    protected Paint paint;

    public UnmovableGameObject(GameManager gameManager) {
        super(gameManager);
        paint = new Paint();
        setHitBox();
    }

    public void prepareBitmap(String bitmapName, float width, float height){
        Context context = getGameManager().getContext();
        // Make a resource id from a String that is the same name as the .png
        int resID = context.getResources().getIdentifier(bitmapName,
                "drawable", context.getPackageName());

        // Create the bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resID);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int ) width,
                (int) height,
                false);
        setBitmap(bitmap);
    }

    private void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Paint getPaint() {
        return paint;
    }
}
