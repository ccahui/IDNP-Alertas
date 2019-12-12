package com.example.proyecto.ui.ubicacion;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

class CircleTransform extends BitmapTransformation {

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(null, toTransform);
        circularBitmapDrawable.setCircular(true);
        Bitmap bitmap = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        circularBitmapDrawable.setBounds(0, 0, outWidth, outHeight);
        circularBitmapDrawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {}

}