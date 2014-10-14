package com.r0adkll.postoffice.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * The material design button layout is to automatically switch to a horizontal orientation
 * if one of the buttons is greater than the 124dp maximum width.
 *
 * Created by r0adkll on 10/11/14.
 */
public class MaterialButtonLayout extends LinearLayout {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private float MAX_BUTTON_WIDTH;
    private float MIN_BUTTON_WIDTH;

    /***********************************************************************************************
     *
     * Constructors
     *
     */


    public MaterialButtonLayout(Context context) {
        super(context);
        init();
    }

    public MaterialButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialButtonLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Initialize the view
     */
    private void init(){
        MAX_BUTTON_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 124f, getResources().getDisplayMetrics());
        MIN_BUTTON_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 88f, getResources().getDisplayMetrics());
    }

    /**
     * if we detect that one of the dialog buttons are greater
     * than the max horizontal layout width of 124dp then to switch the
     * orientation to {@link #VERTICAL}
     *
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(getOrientation() == HORIZONTAL) {
            int N = getChildCount();
            for (int i = 0; i < N; i++) {

                View child = getChildAt(i);
                int width = child.getWidth();
                if (width > MAX_BUTTON_WIDTH || (N>=3 && width > MIN_BUTTON_WIDTH)) {

                    // Update the children's params
                    for (int j = 0; j < N; j++) {
                        Button chd = (Button) getChildAt(j);
                        chd.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);
                    }

                    // Switch orientation
                    setOrientation(VERTICAL);
                    requestLayout();
                    return;
                }


            }
        }


    }
}
