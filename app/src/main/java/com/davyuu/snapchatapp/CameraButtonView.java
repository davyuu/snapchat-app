package com.davyuu.snapchatapp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class CameraButtonView extends View{

    private final float START_ANGLE = -90f;
    private final float TOTAL_ANGLE = 360f;

    private int white;
    private int blue;
    private int red;
    private int translucentWhite;
    private int translucentGray;

    private Paint innerBackgroundPaint;
    private Paint outerBackgroundPaint;
    private Paint progressCirclePaint;
    private RectF rectF = new RectF();

    public CameraButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //colors
        white = ContextCompat.getColor(context, R.color.white);
        blue = ContextCompat.getColor(context, R.color.blue);
        red = ContextCompat.getColor(context, R.color.red);
        translucentWhite = ContextCompat.getColor(context, R.color.translucent_white);
        translucentGray = ContextCompat.getColor(context, R.color.translucent_gray);

        //paints
        innerBackgroundPaint = new Paint();
        innerBackgroundPaint.setStyle(Paint.Style.FILL);
        innerBackgroundPaint.setColor(white);
        innerBackgroundPaint.setAntiAlias(true);

        outerBackgroundPaint = new Paint();
        outerBackgroundPaint.setStyle(Paint.Style.STROKE);
        outerBackgroundPaint.setColor(blue);
        outerBackgroundPaint.setAntiAlias(true);

        progressCirclePaint = new Paint();
        progressCirclePaint.setStyle(Paint.Style.STROKE);
        progressCirclePaint.setColor(white);
        progressCirclePaint.setAntiAlias(true);
    }
}
