package com.r0adkll.postoffice.model;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.r0adkll.postoffice.styles.Style;
import com.r0adkll.postoffice.ui.Mail;

/**
 * This is the main construct that contains all the configuration data needed
 * to construct a dialog.
 *
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
    private Mail mActiveMail;

    private CharSequence mTitle;
    private CharSequence mMessage;
    private int mIcon = -1;
    private int mThemeColor = -1;

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

    /**
     * Get the title of this Delivery
     *
     * @return      the dialog title
     */
    public CharSequence getTitle(){
        return mTitle;
    }

    /**
     * Get the theme color of this Delivery
     *
     * @return      the theme color
     */
    public int getThemeColor(){
        return mThemeColor;
    }

    /**
     * Get the message of this Delivery
     *
     * @return      the dialog message
     */
    public CharSequence getMessage() {
        return mMessage;
    }

    /**
     * Get the Delivery icon
     *
     * @return      the dialog icon
     */
    public int getIcon() {
        return mIcon;
    }

    /**
     * Return whether or not this is to show the keyboard on display
     *
     * @return      the flag
     */
    public boolean isShowKeyboardOnDisplay() {
        return mShowKeyboardOnDisplay;
    }

    /**
     * Return whether or not this Delivery is cancelable
     *
     * @return      the flag
     */
    public boolean isCancelable() {
        return mCancelable;
    }

    /**
     * Return whether or not this Delivery is canceled on outside touch
     *
     * @return      the flag
     */
    public boolean isCanceledOnTouchOutside() {
        return mCanceledOnTouchOutside;
    }

    /**
     * Get the design of the delivery
     *
     * @return      the delivery design
     */
    public Design getDesign() {
        return mDesign;
    }

    /**
     * Get the number of buttons in this Delivery
     *
     * @return      the number of registered button configurations
     */
    public int getButtonCount(){
        return mButtonMap.size();
    }

    /**
     * Get a button configuration for a given button index
     *
     * @see Dialog#BUTTON_POSITIVE
     * @see Dialog#BUTTON_NEGATIVE
     * @see Dialog#BUTTON_NEUTRAL
     * @param whichButton       the button to get the config for
     * @return      the associated button configuration
     */
    public ButtonConfig getButtonConfig(int whichButton){
        return mButtonMap.get(whichButton);
    }

    /**
     * Get the text color for a given button
     *
     * @see Dialog#BUTTON_POSITIVE
     * @see Dialog#BUTTON_NEGATIVE
     * @see Dialog#BUTTON_NEUTRAL
     * @param whichButton       the button to get the color for
     * @return      the button text color, or 0
     */
    public int getButtonTextColor(int whichButton){
        return mButtonTextColorMap.get(whichButton);
    }

    /**
     * Get all the button configurations
     *
     * @return      the map of button config
     */
    public SparseArray<ButtonConfig> getButtonConfig(){
        return mButtonMap;
    }

    /**
     * Get the {@link android.content.DialogInterface.OnCancelListener} for the dialog
     */
    public DialogInterface.OnCancelListener getOnCancelListener(){
        return mOnCancelListener;
    }

    /**
     * Get the {@link android.content.DialogInterface.OnDismissListener} for the dialog
     */
    public DialogInterface.OnDismissListener getOnDismissListener(){
        return mOnDismissListener;
    }

    /**
     * Get the {@link android.content.DialogInterface.OnShowListener} for the dialog
     */
    public DialogInterface.OnShowListener getOnShowListener(){
        return mOnShowListener;
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
     * Update the design of this delivery
     * @param design
     */
    public void updateDesign(Design design){
        mDesign = design;
        if(mStyle != null) mStyle.applyDesign(mDesign, mThemeColor);
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
     * Get the active outgoing mail (dialog) reference if there is one
     *
     * @return      the active outgoing mail, or null
     */
    public Mail getMail(){
        return mActiveMail;
    }

    /**
     * Generate and configure a new DialogFragment
     *
     * @return
     */
    private Mail generateDialogFragment(){
        Mail mail = Mail.createInstance();
        mail.setConfiguration(this);
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
        mActiveMail = generateDialogFragment();
        mActiveMail.show(manager, tag);
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
        mActiveMail = generateDialogFragment();
        mActiveMail.show(transaction, tag);
    }

    /**
     * Show/Create a DialogFragment on the provided FragmentManager with
     * the given tag.
     *
     * @see android.app.DialogFragment#show(android.app.FragmentManager, String)
     * @param manager       the fragment manager used to add the Dialog into the UI
     */
    public void show(FragmentManager manager){
        // Generate the appropriate DialogFragment
        mActiveMail = generateDialogFragment();
        mActiveMail.show(manager, null);
    }

    /**
     * Show/Create a DialogFragment on the provided FragmentTransaction
     * to be executed and shown.
     *
     * @see android.app.DialogFragment#show(android.app.FragmentTransaction, String)
     * @param transaction   the fragment transaction used to show the dialog
     */
    public void show(FragmentTransaction transaction){
        // Generate the appropriate DialogFragment
        mActiveMail = generateDialogFragment();
        mActiveMail.show(transaction, null);
    }

    /**
     * Dismiss whatever dialog spawned by show(...)
     *
     * @see android.app.Dialog#dismiss()
     * @see android.app.DialogFragment#dismiss()
     */
    public void dismiss(){
        if(mActiveMail != null){
            mActiveMail.dismiss();
        }
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
         * @param ctx       the application context
         */
        public Builder(Context ctx){
            this.ctx = ctx;
            delivery = new Delivery(ctx);
        }

        /**
         * Set the dialog title
         *
         * @param title     the dialog title
         * @return          self for chaining
         */
        public Builder setTitle(CharSequence title){
            delivery.mTitle = title;
            return this;
        }

        /**
         * Set the dialog title
         *
         * @param titleResId    the dialog title resource id
         * @return              self for chaining
         */
        public Builder setTitle(int titleResId){
            delivery.mTitle = ctx.getString(titleResId);
            return this;
        }

        /**
         * Set the dialog title
         *
         * @param titleResId    the dialog title resource id
         * @param args          the title format arguments
         * @return              self for chaining
         */
        public Builder setTitle(int titleResId, Object... args){
            delivery.mTitle = ctx.getString(titleResId, args);
            return this;
        }

        /**
         * Set the theme color
         *
         * @param color     the color to set
         * @return          self for chaining
         */
        public Builder setThemeColor(int color){
            delivery.mThemeColor = color; //= ctx.getResources().getColor(color);
            return this;
        }

        /**
         * Set the theme color from a resource file
         *
         * @param colorResId        the color resource to set
         * @return                  self for chaining
         */
        public Builder setThemeColorFromResource(int colorResId){
            delivery.mThemeColor = ctx.getResources().getColor(colorResId);
            return this;
        }

        /**
         * Set the dialog message
         *
         * @param msg       the dialog message
         * @return          self for chaining
         */
        public Builder setMessage(CharSequence msg){
            delivery.mMessage = msg;
            return this;
        }

        /**
         * Set the dialog message from resource
         *
         * @param msgResId      the message resource id
         * @return              self for chaining
         */
        public Builder setMessage(int msgResId){
            delivery.mMessage = ctx.getString(msgResId);
            return this;
        }

        /**
         * Set the dialog message with arguments
         *
         * @param msgResId      the message resource id with format arguments
         * @param args          the arguments to become formatted
         * @return              self for chaining
         */
        public Builder setMessage(int msgResId, Object... args){
            delivery.mMessage = ctx.getString(msgResId, args);
            return this;
        }

        /**
         * Set the dialog title from resources
         *
         * @param iconResId     the icon resource id
         * @return              self for chaining
         */
        public Builder setIcon(int iconResId){
            delivery.mIcon = iconResId;
            return this;
        }

        /**
         * Set a button configuration
         *
         * @see android.app.Dialog#BUTTON_POSITIVE
         * @see android.app.Dialog#BUTTON_NEGATIVE
         * @see android.app.Dialog#BUTTON_NEUTRAL
         *
         * @param whichButton       the button to set
         * @param title             the title of the button
         * @param listener          the button click listener
         * @return                  self for chaining
         */
        public Builder setButton(int whichButton, CharSequence title, DialogInterface.OnClickListener listener){
            delivery.mButtonMap.put(whichButton, new ButtonConfig(title, listener));
            return this;
        }

        /**
         * Set a button configuration
         *
         * @see android.app.Dialog#BUTTON_POSITIVE
         * @see android.app.Dialog#BUTTON_NEGATIVE
         * @see android.app.Dialog#BUTTON_NEUTRAL
         *
         * @param whichButton       the button to set
         * @param titleResId        the title resource id of the title to set
         * @param listener          the button click listener
         * @return                  self for chaining
         */
        public Builder setButton(int whichButton, int titleResId, DialogInterface.OnClickListener listener){
            delivery.mButtonMap.put(whichButton, new ButtonConfig(ctx.getString(titleResId), listener));
            return this;
        }

        /**
         * Set a buttons text color
         *
         * @see android.app.Dialog#BUTTON_POSITIVE
         * @see android.app.Dialog#BUTTON_NEGATIVE
         * @see android.app.Dialog#BUTTON_NEUTRAL
         *
         * @param whichButton       the button to set for
         * @param color             the color of the button
         * @return                  self for chaining
         */
        public Builder setButtonTextColor(int whichButton, int color){
            delivery.mButtonTextColorMap.put(whichButton, ctx.getResources().getColor(color));
            return this;
        }

        /**
         * Set whether to show the keyboard on dialog showing
         * if the style is applicable
         *
         * @param show      the flag
         * @return          self for chaining
         */
        public Builder showKeyboardOnDisplay(boolean show){
            delivery.mShowKeyboardOnDisplay = show;
            return this;
        }

        /**
         * Set whether or not the dialog is cancelable
         *
         * @param flag      the flag
         * @return          self for chaining
         */
        public Builder setCancelable(boolean flag){
            delivery.mCancelable = flag;
            return this;
        }

        /**
         * Set whether or not the dialog is canceled when the user touches outside
         * of it.
         *
         * @param cancel        the flag
         * @return              self for chaining
         */
        public Builder setCanceledOnTouchOutside(boolean cancel){
            delivery.mCanceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * Set the design of the dialog
         *
         * @see Design#HOLO_LIGHT
         * @see Design#HOLO_DARK
         * @see Design#MATERIAL_LIGHT
         * @see Design#MATERIAL_DARK
         *
         * @param design        the design to set
         * @return              self for chaining
         */
        public Builder setDesign(Design design){
            delivery.mDesign = design;
            if(delivery.mStyle != null) delivery.getStyle().applyDesign(design, delivery.mThemeColor);
            return this;
        }

        /**
         * Set the style of the dialog, leavde null for an
         * AlertDialog style. This essentially just defines the content in the dialog between
         * the title and the buttons
         *
         * @param style     the dialog style
         * @return          self for chaining
         */
        public Builder setStyle(Style style){
            delivery.mStyle = style;
            delivery.mStyle.applyDesign(delivery.getDesign(), delivery.mThemeColor);
            return this;
        }

        /**
         * Build this delivery and deliver it
         *
         * This will create a single 'Ok' button with a dismiss listener
         * if the style is left null and no buttons were set.
         *
         * @return  the built delivery
         */
        public Delivery build(){

            // Ensure that simple alerts have an ok to dismiss button
            if(delivery.getStyle() == null && delivery.getButtonCount() == 0){
                this.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }

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
