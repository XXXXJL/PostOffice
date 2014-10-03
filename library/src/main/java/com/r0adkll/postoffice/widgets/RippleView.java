package com.r0adkll.postoffice.widgets;

/*
 * Copyright (C) 2013 Muthuramakrishnan <siriscac@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.r0adkll.postoffice.R;

@SuppressLint("ClickableViewAccessibility")
public class RippleView extends Button {

    /**********************************************************
     *
     * Constants
     *
     */

    private static final long ANIM_DURATION = 300L;
    private static final float DEFAULT_START_RADIUS = 40; // 40dp

    /**********************************************************
     *
     * Variables
     *
     */

    private float mDownX;
    private float mDownY;
    private float mAlphaFactor;
    private float mDensity;
    private float mRadius;
    private float mMaxRadius;

    private long mRippleDuration = ANIM_DURATION;
    private float mStartRadius = dp(DEFAULT_START_RADIUS);
    private int mRippleColor;
    private boolean mIsAnimating = false;
    private boolean mHover = true;

    private RadialGradient mRadialGradient;
    private Paint mPaint;
    private ObjectAnimator mRadiusAnimator;

    private boolean mAnimationIsCancel;
    private Rect mRect;
    private Path mPath = new Path();


    /**********************************************************
     *
     * Constructors
     *
     */

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RippleView);
        mRippleDuration = a.getInteger(R.styleable.RippleView_duration, (int)ANIM_DURATION);
        mStartRadius = a.getDimensionPixelSize(R.styleable.RippleView_startRadius, (int)dp(DEFAULT_START_RADIUS));
        mRippleColor = a.getColor(R.styleable.RippleView_rippleColor,
                mRippleColor);
        mAlphaFactor = a.getFloat(R.styleable.RippleView_alphaFactor,
                mAlphaFactor);
        mHover = a.getBoolean(R.styleable.RippleView_hover, mHover);
        a.recycle();
    }


    /**********************************************************
     *
     * Overridden Methods
     *
     */

    @Override
    public boolean performClick() {
        if(mIsAnimating) {
            return false;
        }else {
            return super.performClick();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = (float) Math.sqrt(w * w + h * h);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        boolean superResult = super.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN
                && this.isEnabled() && mHover) {
            mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
            mAnimationIsCancel = false;
            mDownX = event.getX();
            mDownY = event.getY();

            mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", 0, mStartRadius)
                    .setDuration(mRippleDuration);
            mRadiusAnimator
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusAnimator.start();
            if (!superResult) {
                return true;
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE
                && this.isEnabled() && mHover) {
            mDownX = event.getX();
            mDownY = event.getY();

            // Cancel the ripple animation when moved outside
            if (mAnimationIsCancel = !mRect.contains(
                    getLeft() + (int) event.getX(),
                    getTop() + (int) event.getY())) {
                setRadius(0);
            } else {
                setRadius(mStartRadius);
            }
            if (!superResult) {
                return true;
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP
                && !mAnimationIsCancel && this.isEnabled()) {
            mDownX = event.getX();
            mDownY = event.getY();

            final float tempRadius = (float) Math.sqrt(mDownX * mDownX + mDownY
                    * mDownY);
            float targetRadius = Math.max(tempRadius, mMaxRadius);

            if (mIsAnimating) {
                mRadiusAnimator.cancel();
            }
            mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", mStartRadius,
                    targetRadius);
            mRadiusAnimator.setDuration(mRippleDuration);
            mRadiusAnimator
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mIsAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    setRadius(0);
                    ViewHelper.setAlpha(RippleView.this, 1);
                    mIsAnimating = false;
                    performClick();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            mRadiusAnimator.start();
            if (!superResult) {
                return true;
            }
        }
        return superResult;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode()) {
            return;
        }

        canvas.save(Canvas.CLIP_SAVE_FLAG);

        mPath.reset();
        mPath.addCircle(mDownX, mDownY, mRadius, Path.Direction.CW);

        canvas.clipPath(mPath);
        canvas.restore();

        canvas.drawCircle(mDownX, mDownY, mRadius, mPaint);
    }

    /**********************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Intialize the RippleView
     */
    public void init() {
        mDensity = getContext().getResources().getDisplayMetrics().density;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAlpha(100);
        setRippleColor(Color.BLACK, 0.2f);
    }

    /**
     * Set the color of the ripple effect
     *
     * @param rippleColor       the ripple color
     * @param alphaFactor       the color alpha
     */
    public void setRippleColor(int rippleColor, float alphaFactor) {
        mRippleColor = rippleColor;
        mAlphaFactor = alphaFactor;
    }

    /**
     * Set hover enabled, that is the ripple being drawn on hover
     *
     * @param enabled       the hover value
     */
    public void setHover(boolean enabled) {
        mHover = enabled;
    }

    /**
     * Set the start radius of the ripple effect
     *
     * @param radius        the starting radius of the ripple effect
     */
    public void setStartRadius(int radius){
        mStartRadius = radius;
    }

    /**
     * Adjust the alpha level of a color
     *
     * @param color     the color to modify
     * @param factor    the alpha factor to modify by
     * @return
     */
    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Update the radius of the drawn ripple
     * @param radius        the updated radius ripple to apply
     */
    private void setRadius(final float radius) {
        mRadius = radius;
        if (mRadius > 0) {
            mRadialGradient = new RadialGradient(mDownX, mDownY, mRadius,
                    adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor,
                    Shader.TileMode.MIRROR);
            mPaint.setShader(mRadialGradient);
        }
        invalidate();
    }

    /**
     * Convert DP to Pixels
     *
     * @param dp        the dp value to convert
     * @return          the pixel value for the provided DP
     */
    private float dp(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}