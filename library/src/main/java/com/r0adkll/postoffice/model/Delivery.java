package com.r0adkll.postoffice.model;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.r0adkll.postoffice.styles.Style;
import com.r0adkll.postoffice.ui.Mail;

/**
 * Project: PostOffice
 * Package: com.r0adkll.postoffice.model
 * Created by drew.heavner on 8/20/14.
 */
public class Delivery {

    /**********************************************************
     *
     * Variables
     *
     */

    private Context mCtx;

    private CharSequence mTitle;
    private CharSequence mMessage;
    private int mIcon;

    private SparseArray<ButtonConfig> mButtonMap;
    private SparseIntArray mButtonTextColorMap;

    private boolean mShowKeyboardOnDisplay;
    private boolean mCancelable;
    private boolean mCanceledOnTouchOutside;

    private Design mDesign = Design.HOLO_LIGHT;
    private Style mStyle;

    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnShowListener mOnShowListener;

    /**
     * Hidden constructor
     *
     * @param ctx       the application context
     */
    private Delivery(Context ctx){
        mCtx = ctx;
        mButtonMap = new SparseArray<>();
        mButtonTextColorMap = new SparseIntArray();
    }

    /**********************************************************
     *
     * Accessor Methods
     *
     */

    public CharSequence getTitle(){
        return mTitle;
    }

    public CharSequence getMessage() {
        return mMessage;
    }

    public int getIcon() {
        return mIcon;
    }

    public boolean isShowKeyboardOnDisplay() {
        return mShowKeyboardOnDisplay;
    }

    public boolean isCancelable() {
        return mCancelable;
    }

    public boolean isCanceledOnTouchOutside() {
        return mCanceledOnTouchOutside;
    }

    public Design getDesign() {
        return mDesign;
    }

    public int getButtonCount(){
        return mButtonMap.size();
    }

    public ButtonConfig getButtonConfig(int whichButton){
        return mButtonMap.get(whichButton);
    }

    public int getButtonTextColor(int whichButton){
        return mButtonTextColorMap.get(whichButton);
    }

    public SparseArray<ButtonConfig> getButtonConfig(){
        return mButtonMap;
    }

    /**********************************************************
     *
     * Helper Methods
     *
     */


    /**
     * Set the dialog onCancel listener that gets called whenever the dialog is canceled
     *
     * @see android.content.DialogInterface.OnCancelListener
     * @see android.app.Dialog#setOnCancelListener(android.content.DialogInterface.OnCancelListener)
     * @param listener      the interface callback to receive the cancel event
     * @return              self for chaining
     */
    public Delivery setOnCancelListener(DialogInterface.OnCancelListener listener){
        mOnCancelListener = listener;
        return this;
    }

    /**
     * Set the dialog onDismiss listener that gets called whenever the dialog is dismissed
     *
     * @see android.content.DialogInterface.OnDismissListener
     * @see android.app.Dialog#setOnDismissListener(android.content.DialogInterface.OnDismissListener)
     * @param listener      the interface callback to receive the dismiss event
     * @return              self for chaining
     */
    public Delivery setOnDismissListener(DialogInterface.OnDismissListener listener){
        mOnDismissListener = listener;
        return this;
    }

    /**
     * Set the dialog onShow listener that gets called whenever the dialog is shown
     *
     * @see android.content.DialogInterface.OnShowListener
     * @see android.app.Dialog#setOnShowListener(android.content.DialogInterface.OnShowListener)
     * @param listener      the interface callback to receive the show event
     * @return              self for chaining
     */
    public Delivery setOnShowListener(DialogInterface.OnShowListener listener){
        mOnShowListener = listener;
        return this;
    }

    /**
     * Get the current Mail style (this will be one of hte subclasses)
     *
     * @return      the style for this delivery
     */
    public Style getStyle(){
        return mStyle;
    }

    /**
     * Generate and configure a new DialogFragment
     *
     * @return
     */
    private Mail generateDialogFragment(){
        Mail mail = new Mail();
        mail.applyConfiguration(this);
        return mail;
    }

    /**********************************************************
     *
     * Display Methods
     *
     */


    /**
     * Show/Create a DialogFragment on the provided FragmentManager with
     * the given tag.
     *
     * @see android.app.DialogFragment#show(android.app.FragmentManager, String)
     * @param manager       the fragment manager used to add the Dialog into the UI
     * @param tag           the tag for the dialog fragment in the manager
     */
    public void show(FragmentManager manager, String tag){

        // Generate the appropriate DialogFragment


    }

    /**
     * Show/Create a DialogFragment on the provided FragmentTransaction
     * to be executed and shown.
     *
     * @see android.app.DialogFragment#show(android.app.FragmentTransaction, String)
     * @param transaction   the fragment transaction used to show the dialog
     * @param tag           the tag for the dialog fragment in the manager
     */
    public void show(FragmentTransaction transaction, String tag){

        // Generate the appropriate DialogFragment

    }

    /**
     * Show/Create an {@link android.app.AlertDialog}
     *
     * @see android.app.Dialog#show()
     */
    public void show(){

        // Generate the appropriate AlertDialog

    }

    /**
     * Dismiss whatever dialog spawned by show(...)
     *
     * @see android.app.Dialog#dismiss()
     * @see android.app.DialogFragment#dismiss()
     */
    public void dismiss(){

    }

    /**********************************************************
     *
     * Builder Class
     *
     */

    /**
     * The Delivery Builder class to construct delivery objects
     *
     */
    public static class Builder{

        // Context ref
        private Context ctx;

        // The Building object
        private Delivery delivery;

        /**
         * Constructor
         * @param ctx
         */
        public Builder(Context ctx){
            this.ctx = ctx;
            delivery = new Delivery(ctx);
        }

        public Builder setTitle(CharSequence title){
            delivery.mTitle = title;
            return this;
        }

        public Builder setTitle(int titleResId){
            delivery.mTitle = ctx.getString(titleResId);
            return this;
        }

        public Builder setTitle(int titleResId, Object... args){
            delivery.mTitle = ctx.getString(titleResId, args);
            return this;
        }

        public Builder setMessage(CharSequence msg){
            delivery.mMessage = msg;
            return this;
        }

        public Builder setMessage(int msgResId){
            delivery.mMessage = ctx.getString(msgResId);
            return this;
        }

        public Builder setMessage(int msgResId, Object... args){
            delivery.mMessage = ctx.getString(msgResId, args);
            return this;
        }

        public Builder setIcon(int iconResId){
            delivery.mIcon = iconResId;
            return this;
        }

        public Builder setButton(int whichButton, CharSequence title, DialogInterface.OnClickListener listener){
            delivery.mButtonMap.put(whichButton, new ButtonConfig(title, listener));
            return this;
        }

        public Builder setButton(int whichButton, int titleResId, DialogInterface.OnClickListener listener){
            delivery.mButtonMap.put(whichButton, new ButtonConfig(ctx.getString(titleResId), listener));
            return this;
        }

        public Builder setButtonTextColor(int whichButton, int color){
            delivery.mButtonTextColorMap.put(whichButton, color);
            return this;
        }

        public Builder showKeyboardOnDisplay(boolean show){
            delivery.mShowKeyboardOnDisplay = show;
            return this;
        }

        public Builder setCancelable(boolean flag){
            delivery.mCancelable = flag;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancel){
            delivery.mCanceledOnTouchOutside = cancel;
            return this;
        }

        public Builder setDesign(Design design){
            delivery.mDesign = design;
            return this;
        }

        public Builder setStyle(Style style){
            delivery.mStyle = style;
            return this;
        }

        public Delivery build(){
            return delivery;
        }

    }

    /**
     * Convenience class for button configurations
     *
     */
    public static class ButtonConfig{

        public CharSequence title;
        public DialogInterface.OnClickListener listener;

        /**
         * Constructor
         *
         * @param title         the button title
         * @param listener      the button click listener
         */
        public ButtonConfig(CharSequence title, DialogInterface.OnClickListener listener){
            this.title = title;
            this.listener = listener;
        }
    }

}
