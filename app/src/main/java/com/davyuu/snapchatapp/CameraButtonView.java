package com.davyuu.snapchatapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import static com.davyuu.snapchatapp.CameraButtonView.State.ACTIVE;
import static com.davyuu.snapchatapp.CameraButtonView.State.ANIMATION_TO_ACTIVE;
import static com.davyuu.snapchatapp.CameraButtonView.State.ANIMATION_TO_INACTIVE;
import static com.davyuu.snapchatapp.CameraButtonView.State.INACTIVE;

public class CameraButtonView extends View {

    public static final long ANIMATION_DURATION = 500L;

    private final float START_ANGLE = -90f;
    private final float TOTAL_ANGLE = 360f;
    private float progress;
    private State currentState;

    private final int white;
    private final int blue;
    private final int red;
    private final int translucentWhite;
    private final int translucentGray;

    private final float strokeWidth;
    private final float delta;
    private float size;

    private Paint innerBackgroundPaint;
    private Paint outerStrokePaint;
    private Paint animationStrokePaint;
    private Paint progressCirclePaint;
    private RectF rect = new RectF();

    private Animation animation;

    public CameraButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        //colors
        white = ContextCompat.getColor(context, R.color.white);
        blue = ContextCompat.getColor(context, R.color.blue);
        red = ContextCompat.getColor(context, R.color.red);
        translucentWhite = ContextCompat.getColor(context, R.color.translucent_white);
        translucentGray = ContextCompat.getColor(context, R.color.translucent_gray);

        strokeWidth = getResources().getDimension(R.dimen.camera_button_stroke_width);
        size = getResources().getDimension(R.dimen.camera_button_size);
        delta = getHalf(strokeWidth);
        updateRect();

        //paints
        innerBackgroundPaint = new Paint();
        innerBackgroundPaint.setColor(translucentGray);
        innerBackgroundPaint.setAntiAlias(true);

        outerStrokePaint = new Paint();
        outerStrokePaint.setStyle(Paint.Style.STROKE);
        outerStrokePaint.setStrokeWidth(strokeWidth);
        outerStrokePaint.setAntiAlias(true);

        animationStrokePaint = new Paint();
        animationStrokePaint.setStyle(Paint.Style.STROKE);
        animationStrokePaint.setStrokeWidth(strokeWidth);
        animationStrokePaint.setAntiAlias(true);

        progressCirclePaint = new Paint();
        progressCirclePaint.setStyle(Paint.Style.STROKE);
        progressCirclePaint.setStrokeWidth(strokeWidth);
        progressCirclePaint.setColor(red);
        progressCirclePaint.setAntiAlias(true);

        setInactive();
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentState == INACTIVE) {
                    setToActiveAnimation();
                } else if (currentState == ACTIVE) {
                    setToInactiveAnimation();
                }
                startAnimation(animation);
            }
        });
        animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                progress = interpolatedTime;
                invalidate();
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentState == ANIMATION_TO_ACTIVE) {
                    setActive();
                } else if (currentState == ANIMATION_TO_INACTIVE) {
                    setInactive();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(ANIMATION_DURATION);
        animation.setInterpolator(new LinearInterpolator());
    }

    private void updateRect() {
        float left = 0 + delta;
        float top = 0 + delta;
        float right = size - delta;
        float bottom = size - delta;
        rect.set(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getHalf(size);
        float centerY = getHalf(size);
        float innerRadius = centerX;
        float outerRadius = centerX - delta;

        canvas.drawCircle(centerX, centerY, innerRadius, innerBackgroundPaint);
        canvas.drawCircle(centerX, centerY, outerRadius, outerStrokePaint);

        if (currentState == ANIMATION_TO_ACTIVE) {
            float currentAngle = TOTAL_ANGLE * progress;
            canvas.drawArc(rect, START_ANGLE, currentAngle, false, animationStrokePaint);
        } else if (currentState == ANIMATION_TO_INACTIVE) {
            float currentAngle = -TOTAL_ANGLE * progress;
            canvas.drawArc(rect, START_ANGLE, currentAngle, false, animationStrokePaint);
        }
    }

    private void updateSize(float newSize) {
        size = newSize;
        updateRect();
    }

    public void setActive() {
        currentState = ACTIVE;
        outerStrokePaint.setColor(translucentWhite);
        invalidate();
    }

    public void setInactive() {
        currentState = INACTIVE;
        outerStrokePaint.setColor(blue);
        invalidate();
    }

    private void setToActiveAnimation() {
        currentState = ANIMATION_TO_ACTIVE;
        animationStrokePaint.setColor(translucentWhite);
        invalidate();
    }

    private void setToInactiveAnimation() {
        currentState = ANIMATION_TO_INACTIVE;
        animationStrokePaint.setColor(blue);
        invalidate();
    }

    private float getHalf(float value) {
        return value / 2;
    }

    public enum State {
        ACTIVE,
        INACTIVE,
        ANIMATION_TO_ACTIVE,
        ANIMATION_TO_INACTIVE
    }
}
