package com.r0adkll.postoffice.styles;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ftinc.fontloader.FontLoader;
import com.ftinc.fontloader.Types;
import com.r0adkll.postoffice.R;
import com.r0adkll.postoffice.model.Design;

/**
 * Created by r0adkll on 8/21/14.
 */
public class ProgressStyle implements Style {

    /**********************************************************
     *
     * Variables
     *
     */

    private View mLayout;
    private ProgressBar mProgress;
    private TextView mProgressText, mProgressMax;

    private CharSequence mSuffix;
    private boolean mIsCloseOnFinish = false;
    private boolean mIsPercentageMode = false;

    private DialogInterface mDialogInterface;

    /**
     * Constructor
     * @param ctx
     */
    public ProgressStyle(Context ctx){
        mLayout = LayoutInflater.from(ctx).inflate(R.layout.layout_progress_style, null, false);
        mProgress = (ProgressBar) mLayout.findViewById(R.id.progressBar);
        mProgressText = (TextView) mLayout.findViewById(R.id.progress);
        mProgressMax = (TextView) mLayout.findViewById(R.id.total);
    }

    /**
     * Get this style's content view
     *
     * @return      the view of the style
     */
    @Override
    public View getContentView() {
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mLayout.getResources().getDisplayMetrics());
        mLayout.setPadding(padding, padding, padding, padding);
        return mLayout;
    }

    /**
     * Apply the design of a delivery to this style
     *
     * @param design    the design, i.e. Holo, Material, Light, Dark
     * @param themeColor    the theme color
     */
    @Override
    public void applyDesign(Design design, int themeColor) {

        LayerDrawable drawable = (LayerDrawable) mLayout.getResources().getDrawable(R.drawable.progress_material_horizontal);

        Drawable bg = drawable.getDrawable(0);
        int color = design.isLight() ? R.color.grey_700 : R.color.grey_500;
        bg.setColorFilter(mLayout.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

        Drawable secProg = drawable.getDrawable(1);
        secProg.setColorFilter(lighten(themeColor), PorterDuff.Mode.SRC_ATOP);

        Drawable prg = drawable.getDrawable(2);
        prg.setColorFilter(themeColor, PorterDuff.Mode.SRC_ATOP);

        mProgress.setProgressDrawable(drawable);

        AnimationDrawable animDrawable = (AnimationDrawable) mProgress.getIndeterminateDrawable();
        animDrawable.setColorFilter(themeColor, PorterDuff.Mode.SRC_ATOP);


        if(design.isLight()){
            mProgressText.setTextColor(mLayout.getResources().getColor(R.color.tertiary_text_material_light));
            mProgressMax.setTextColor(mLayout.getResources().getColor(R.color.tertiary_text_material_light));
        }else{
            mProgressText.setTextColor(mLayout.getResources().getColor(R.color.tertiary_text_material_dark));
            mProgressMax.setTextColor(mLayout.getResources().getColor(R.color.tertiary_text_material_dark));
        }

        if(design.isMaterial()){
            FontLoader.applyTypeface(mProgressText, Types.ROBOTO_MEDIUM);
            FontLoader.applyTypeface(mProgressMax, Types.ROBOTO_MEDIUM);
        }

    }


    @Override
    public void onButtonClicked(int which, DialogInterface dialogInterface) {}

    @Override
    public void onDialogShow(DialogInterface dialog) {
        mDialogInterface = dialog;
    }

    /**
     * Set the progress of the progress bar
     *
     * @param value     the progress out of the max
     * @return          self for chaining
     */
    public ProgressStyle setProgress(int value){
        mProgress.setProgress(value);

        if(!mIsPercentageMode) {
            mProgressText.setText(String.format("%d %s", value, mSuffix));
        }else{
            mProgressText.setVisibility(View.GONE);

            int progress = mProgress.getProgress();
            int max = mProgress.getMax();
            float percent = ((float)progress/(float)max);
            int percentNorm = (int) (percent * 100);

            mProgressMax.setText(String.format("%d %%", percentNorm));
        }

        if(value >= mProgress.getMax() && mIsCloseOnFinish && mDialogInterface != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialogInterface.dismiss();

                }
            }, 300);
        }

        return this;
    }

    /**
     * Set the max total progress of the progress bar
     *
     * @param value     the max value
     * @return          self for chaining
     */
    public ProgressStyle setMax(int value){
        mProgress.setMax(value);

        if(!mIsPercentageMode)
            mProgressMax.setText(String.format("%d %s", value, mSuffix));

        return this;
    }

    /**
     * Set this progress as Indeterminate
     *
     * @param value     indeterminate value
     * @return          self for chaining
     */
    public ProgressStyle setIndeterminate(boolean value){
        mProgress.setIndeterminate(value);
        mProgressText.setVisibility(value ? View.GONE : View.VISIBLE);
        mProgressMax.setVisibility(value ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * Get a lighter color for a given color
     *
     * @param color     the color to lighten
     * @return          the lightened color
     */
    private int lighten(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = 1.0f - 0.8f * (1.0f - hsv[2]);
        color = Color.HSVToColor(hsv);
        return color;
    }

    /**
     * This style's builder class
     */
    public static class Builder{

        // The Object being built
        private ProgressStyle style;

        /**
         * Constructor
         * @param ctx
         */
        public Builder(Context ctx){
            style = new ProgressStyle(ctx);
        }

        /**
         * Set the progress and max label suffixes i.e. Mb, Gb, byte
         *
         * @param sfx       the suffix to set
         * @return          self for chaining
         */
        public Builder setSuffix(String sfx){
            style.mSuffix = sfx;
            return this;
        }

        /**
         * Set the progress bar as an indeterminate
         *
         * @param value     the indeterminate value
         * @return          self for chaining
         */
        public Builder setIndeterminate(boolean value){
            style.setIndeterminate(value);
            return this;
        }

        /**
         * Set this progress dialog to close when it finishes
         * i.e. when progress >= max
         *
         * @param value     the flag value
         * @return          self for chaining
         */
        public Builder setCloseOnFinish(boolean value){
            style.mIsCloseOnFinish = value;
            return this;
        }

        /**
         * Set this progress style to percentage mode where it only displays the
         * progress' percentage completion
         *
         * @param value     the flag value
         * @return          self for chaining
         */
        public Builder setPercentageMode(boolean value){
            style.mIsPercentageMode = value;
            return this;
        }

        /**
         * Complete and return the Progress Style build
         * @return  built progress style
         */
        public ProgressStyle build(){
            return style;
        }

    }

}
