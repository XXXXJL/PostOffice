package com.r0adkll.postoffice.styles;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextWatcher;
import android.util.StateSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.r0adkll.postoffice.R;
import com.r0adkll.postoffice.model.Design;

/**
 * Created by r0adkll on 8/20/14.
 */
public class EditTextStyle implements Style {

    private EditText mInputField;
    private OnTextAcceptedListener mListener;

    /**
     * Constructor
     * @param ctx
     */
    private EditTextStyle(Context ctx){
        mInputField = new EditText(ctx);


    }

    /**
     * Get the content view for this style
     * @return
     */
    @Override
    public View getContentView() {
        return mInputField;
    }

    /**
     * Apply a design to the style
     * @param design    the design, i.e. Holo, Material, Light, Dark
     */
    @Override
    public void applyDesign(Design design, int themeColor) {
        Context ctx = mInputField.getContext();

        int smallPadId = design.isMaterial() ? R.dimen.default_margin : R.dimen.default_margin_small;
        int largePadId = design.isMaterial() ? R.dimen.material_edittext_spacing : R.dimen.default_margin_small;

        int padLR = ctx.getResources().getDimensionPixelSize(largePadId);
        int padTB = ctx.getResources().getDimensionPixelSize(smallPadId);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(padLR, padTB, padLR, padTB);
        mInputField.setLayoutParams(params);

        if(design.isLight())
            mInputField.setTextColor(mInputField.getResources().getColor(R.color.background_material_dark));
        else
            mInputField.setTextColor(mInputField.getResources().getColor(R.color.background_material_light));

        StateListDrawable drawable;
        if(design.isMaterial()) {
            drawable = (StateListDrawable) mInputField.getResources().getDrawable(R.drawable.edittext_mtrl_alpha);
        }else{
            drawable = (StateListDrawable) mInputField.getBackground();
        }

        drawable.setColorFilter(themeColor, PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mInputField.setBackground(drawable);
        else
            mInputField.setBackgroundDrawable(drawable);

    }

    /**
     * Called when one of the three available buttons are clicked
     * so that this style can perform a special action such as calling a content
     * delivery callback.
     *
     * @param which                 which button was pressed
     * @param dialogInterface
     */
    @Override
    public void onButtonClicked(int which, DialogInterface dialogInterface) {
        switch (which){
            case Dialog.BUTTON_POSITIVE:
                if(mListener != null) mListener.onAccepted(getText());
                break;
            case Dialog.BUTTON_NEGATIVE:
                // Do nothing for the negative click
                break;
        }
    }

    /**
     * Get the core {@link android.widget.EditText} widget for this style
     * to apply special/custom attributes to
     *
     * @return      the raw edit text view of this Style
     */
    public EditText getEditTextView(){
        return mInputField;
    }

    /**
     * Get the text content of the input field
     *
     * @return      the user entered text content
     */
    public String getText(){
        return mInputField.getText().toString();
    }

    /**
     * This classes builder class, to chain-construct
     * this object
     */
    public static class Builder{

        // The style being built
        private EditTextStyle style;

        /**
         * Constructor
         * @param ctx
         */
        public Builder(Context ctx){
            style = new EditTextStyle(ctx);
        }

        public Builder setText(CharSequence text){
            style.getEditTextView().setText(text);
            return this;
        }

        public Builder setHint(CharSequence hint){
            style.getEditTextView().setHint(hint);
            return this;
        }

        public Builder setTextColor(int color){
            style.getEditTextView().setTextColor(color);
            return this;
        }

        public Builder setHintColor(int hintColor){
            style.getEditTextView().setHintTextColor(hintColor);
            return this;
        }

        public Builder addTextWatcher(TextWatcher watcher){
            style.getEditTextView().addTextChangedListener(watcher);
            return this;
        }

        public Builder setInputType(int type){
            style.getEditTextView().setInputType(type);
            return this;
        }

        public Builder setOnTextAcceptedListener(OnTextAcceptedListener listener){
            style.mListener = listener;
            return this;
        }

        public EditTextStyle build(){
            return style;
        }

    }

    public static interface OnTextAcceptedListener{
        public void onAccepted(String text);
    }

}
