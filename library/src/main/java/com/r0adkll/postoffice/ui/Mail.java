package com.r0adkll.postoffice.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.r0adkll.postoffice.model.Delivery;
import com.r0adkll.postoffice.styles.Style;

/**
 * Project: PostOffice
 * Package: com.r0adkll.postoffice.ui
 * Created by drew.heavner on 8/20/14.
 */
public class Mail extends DialogFragment {

    /**********************************************************
     *
     * Static Creator
     *
     */

    /**
     * Create a new instance of this dialog fragment
     *
     * @return              the created/inflated dialog fragment
     */
    public static Mail createInstance(){
        Mail mail = new Mail();
        return mail;
    }

    /**********************************************************
     *
     * Constants
     *
     */


    /**********************************************************
     *
     * Variables
     *
     */

    private Delivery mConstruct;

    /**
     * Empty constructor
     */
    public Mail(){}

    /**********************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mConstruct == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }else{
            View view = null;

            // Depending on the style, load the appropriate layout

            return view;
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog diag;

        // Disable the title if the title data isn't null
        if(mConstruct != null){

            // Construct the Dialog Object
            if(mConstruct.getStyle() == null){
                diag = buildAlertDialog(getActivity(), mConstruct);
            }else{
                diag = super.onCreateDialog(savedInstanceState);
            }

            boolean cond1 = mConstruct.getTitle() == null && !mConstruct.getDesign().isMaterial();
            boolean cond2 = mConstruct.getDesign().isMaterial();

            if(cond1 || cond2) {

                // Remove the Title Feature
                diag.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        }else{
            diag = super.onCreateDialog(savedInstanceState);
        }

        return diag;
    }

    /**********************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Apply a delivery configuration to the fragment
     *
     * @param delivery      the delivery configuration construct
     */
    public void applyConfiguration(Delivery delivery){
        mConstruct = delivery;

        // Apply the construct

    }

    /**
     * Convienence function for building an AlertDialog stylized to the Delivery
     * construct.
     *
     * @param ctx           the application context
     * @param delivery      the delivery configuration construct
     * @return              the built AlertDialog
     */
    public static AlertDialog buildAlertDialog(Context ctx, Delivery delivery){
        int theme = AlertDialog.THEME_HOLO_LIGHT;
        switch(delivery.getDesign()){
            case HOLO_LIGHT:
                theme = AlertDialog.THEME_HOLO_LIGHT;
                break;
            case HOLO_DARK:
                theme = AlertDialog.THEME_HOLO_DARK;
                break;
            case MATERIAL_LIGHT:
                theme = AlertDialog.THEME_HOLO_LIGHT;
                break;
            case MATERIAL_DARK:
                theme = AlertDialog.THEME_HOLO_DARK;
                break;
        }

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, theme);

        boolean cond1 = delivery.getTitle() == null && !delivery.getDesign().isMaterial();
        boolean cond2 = delivery.getDesign().isMaterial();

        if(!cond1 && !cond2){
            builder.setTitle(delivery.getTitle());
        }

        // if the style is null, and the message exists in the construct, set the alert dialog message
        if(delivery.getStyle() == null && delivery.getMessage() != null && !delivery.getDesign().isMaterial()){
            builder.setMessage(delivery.getMessage());
        }

        // If it isn't material design, apply the button constructs
        if(!delivery.getDesign().isMaterial()){

            // Iterate through config, and setup the button states
            SparseArray<Delivery.ButtonConfig> config = delivery.getButtonConfig();
            int N = config.size();
            for(int i=0; i<N; i++){
                int key = config.keyAt(i);
                Delivery.ButtonConfig cfg = config.get(key);

                // Pull button Color
                int textColor = delivery.getButtonTextColor(key);
                Spannable title = new SpannableString(cfg.title);
                if(textColor != 0){
                    title.setSpan(new ForegroundColorSpan(textColor), 0, cfg.title.length(), 0);
                }

                // Ensure that they are using the correct which buttons
                switch (key){
                    case AlertDialog.BUTTON_POSITIVE:
                        builder.setPositiveButton(title, cfg.listener);
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        builder.setNegativeButton(title, cfg.listener);
                        break;
                    case AlertDialog.BUTTON_NEUTRAL:
                        builder.setNeutralButton(title, cfg.listener);
                        break;
                }
            }
        }

        if(delivery.getStyle() != null && !delivery.getDesign().isMaterial()){
            Style style = delivery.getStyle();
            builder.setView(style.getContentView());
        }

        // Create the dialog and return it
        return builder.create();
    }

}
