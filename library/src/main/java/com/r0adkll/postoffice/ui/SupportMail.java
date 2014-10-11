package com.r0adkll.postoffice.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ftinc.fontloader.FontLoader;
import com.ftinc.fontloader.Types;
import com.r0adkll.postoffice.R;
import com.r0adkll.postoffice.model.Delivery;
import com.r0adkll.postoffice.styles.EditTextStyle;
import com.r0adkll.postoffice.styles.Style;
import com.r0adkll.postoffice.widgets.RippleView;

/**
 * Project: PostOffice
 * Package: com.r0adkll.postoffice.ui
 * Created by drew.heavner on 8/20/14.
 */
public class SupportMail extends DialogFragment {

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
    public static SupportMail createInstance(){
        SupportMail mail = new SupportMail();
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

    private InputMethodManager mImm;

    private TextView mTitle;
    private TextView mMessage;
    private LinearLayout mContentFrame;
    private FrameLayout mStyleContent;
    private LinearLayout mButtonContainer;
    private ScrollView mMessageScrollview;

    private Delivery mConstruct;

    /**
     * Empty constructor
     */
    public SupportMail(){}

    /**********************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Load mImm
        mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(mConstruct != null && mConstruct.getDesign().isMaterial()) {

            // Apply construct to UI
            if (mTitle != null && mMessage != null) {
                if (mConstruct.getTitle() != null){
                    mTitle.setText(mConstruct.getTitle());
                }else{
                    mTitle.setVisibility(View.GONE);
                }

                if (mConstruct.getMessage() != null) {
                    if(mConstruct.getMovementMethod() != null)
                        mMessage.setMovementMethod(mConstruct.getMovementMethod());

                    mMessage.setAutoLinkMask(mConstruct.getAutoLinkMask());
                    mMessage.setText(mConstruct.getMessage());
                }else
                    mContentFrame.removeView(mMessageScrollview);

                if (mConstruct.getDesign().isMaterial()) {
                    FontLoader.applyTypeface(mTitle, Types.ROBOTO_MEDIUM);
                    FontLoader.applyTypeface(mMessage, Types.ROBOTO_REGULAR);
                }
            }

            if (mButtonContainer != null && mConstruct.getButtonConfig().size() > 0) {

                // Iterate through config, and setup the button states
                SparseArray<Delivery.ButtonConfig> config = mConstruct.getButtonConfig();
                int N = config.size();
                for (int i = 0; i < N; i++) {
                    final int key = config.keyAt(i);
                    final Delivery.ButtonConfig cfg = config.get(key);

                    // Pull button Color
                    int textColor = mConstruct.getButtonTextColor(key);

                    // Create and add buttons (in order) to the button container
                    RippleView button = (RippleView)getActivity().getLayoutInflater().inflate(mConstruct.getDesign().isLight() ? R.layout.material_light_dialog_button : R.layout.material_dark_dialog_button, null, false);
                    FontLoader.applyTypeface(button, Types.ROBOTO_MEDIUM);
                    button.setId(key);
                    button.setText(cfg.title);
                    if(textColor != 0) button.setTextColor(textColor);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cfg.listener.onClick(getDialog(), key);
                            if(mConstruct.getStyle() != null){
                                mConstruct.getStyle().onButtonClicked(key, getDialog());
                            }
                        }
                    });

                    // add to layout
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            getResources().getDimensionPixelSize(R.dimen.material_button_height));

                    mButtonContainer.addView(button, params);
                }

            }else if(mButtonContainer != null && mConstruct.getButtonCount() == 0){
                mButtonContainer.setVisibility(View.GONE);
            }

            // Load Content
            if (mStyleContent != null && mConstruct.getStyle() != null) {
                mStyleContent.addView(mConstruct.getStyle().getContentView());
            }

        }

        if(mConstruct != null) {
            // Lastly apply the other dialog options
            setCancelable(mConstruct.isCancelable());
            getDialog().setCanceledOnTouchOutside(mConstruct.isCanceledOnTouchOutside());
            getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    if (mConstruct.getOnShowListener() != null)
                        mConstruct.getOnShowListener().onShow(dialog);
                    if (mConstruct.getStyle() != null)
                        mConstruct.getStyle().onDialogShow(getDialog());

                    if (mConstruct.isShowKeyboardOnDisplay()) {
                        if (mConstruct.getStyle() instanceof EditTextStyle) {
                            EditText et = ((EditTextStyle) mConstruct.getStyle()).getEditTextView();
                            mImm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }

                }
            });
        }else{
            dismiss();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mConstruct == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }else{
            View view;

            // Depending on the style, load the appropriate layout
            if(mConstruct.getDesign().isMaterial()){
                if(mConstruct.getDesign().isLight()){
                    view = inflater.inflate(R.layout.layout_material_light_dialog, container, false);
                }else{
                    view = inflater.inflate(R.layout.layout_material_dark_dialog, container, false);
                }

                mTitle = (TextView) view.findViewById(R.id.title);
                mMessage = (TextView) view.findViewById(R.id.message);
                mMessageScrollview = (ScrollView) view.findViewById(R.id.message_scrollview);
                mContentFrame = (LinearLayout) view.findViewById(R.id.content_frame);
                mStyleContent = (FrameLayout) view.findViewById(R.id.style_content);
                mButtonContainer = (LinearLayout) view.findViewById(R.id.button_container);
            }else{
                view = super.onCreateView(inflater, container, savedInstanceState);
            }

            return view;
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog diag;

        // Disable the title if the title data isn't null
        if(mConstruct != null && !mConstruct.getDesign().isMaterial()){

            // Construct the Dialog Object
            diag = buildAlertDialog(getActivity(), mConstruct);

            // Remove the Title Feature
            int theme = mConstruct.getDesign().isLight() ? android.R.style.Theme_Holo_Light : android.R.style.Theme_Holo;
            setStyle(STYLE_NO_TITLE, theme);

        }else{
            diag = super.onCreateDialog(savedInstanceState);
            if(mConstruct != null) {
                int theme = mConstruct.getDesign().isLight() ? android.R.style.Theme_Holo_Light : android.R.style.Theme_Holo;
                setStyle(STYLE_NO_TITLE, theme);
            }

        }


        return diag;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mConstruct != null && mConstruct.getOnDismissListener() != null) mConstruct.getOnDismissListener().onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if(mConstruct != null && mConstruct.getOnCancelListener() != null) mConstruct.getOnCancelListener().onCancel(dialog);
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
    public void setConfiguration(Delivery delivery){
        mConstruct = delivery;
    }

    /**
     * Modify the title of the dialog no matter what style it is
     *
     * @param title     the title to update with
     */
    public void setTitle(CharSequence title){
        if(mConstruct != null && !mConstruct.getDesign().isMaterial()){
            // Modify the dialog's title element
            getDialog().setTitle(title);
        }else{
            if(mTitle != null){
                mTitle.setText(title);
            }
        }
    }

    /**
     * Modify the title of the dialog no matter what style is being used
     *
     * @param titleResId        the resource id of the string value to use
     */
    public void setTitle(int titleResId){
        setTitle(getString(titleResId));
    }

    /**
     * Convienence function for building an AlertDialog stylized to the Delivery
     * construct.
     *
     * @param ctx           the application context
     * @param delivery      the delivery configuration construct
     * @return              the built AlertDialog
     */
    public static AlertDialog buildAlertDialog(Context ctx, final Delivery delivery){
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

        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_holo_dialog, null, false);
        view.setMinimumWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, ctx.getResources().getDisplayMetrics()));
        ImageView icon = (ImageView)view.findViewById(R.id.icon);
        TextView alertTitle = (TextView)view.findViewById(R.id.alertTitle);
        TextView mMessage = (TextView)view.findViewById(R.id.message);
        View divider = view.findViewById(R.id.titleDivider);
        FrameLayout customContent = (FrameLayout)view.findViewById(R.id.customPanel);
        LinearLayout topPanel = (LinearLayout)view.findViewById(R.id.topPanel);
        LinearLayout contentPanel = (LinearLayout)view.findViewById(R.id.contentPanel);

        if(delivery.getTitle() != null){
            alertTitle.setText(delivery.getTitle());
            int color = delivery.getThemeColor() == -1 ? ctx.getResources().getColor(R.color.blue_700) : delivery.getThemeColor();
            alertTitle.setTextColor(color);
            divider.setBackgroundColor(color);
        }else{
            topPanel.setVisibility(View.GONE);
        }

        if(delivery.getIcon() != -1){
            icon.setImageResource(delivery.getIcon());
        }else{
            icon.setVisibility(View.GONE);
        }

        // Set the content

        // if the style is null, and the message exists in the construct, set the alert dialog message
        if(delivery.getMessage() != null){
            if(delivery.getMovementMethod() != null)
                mMessage.setMovementMethod(delivery.getMovementMethod());

            mMessage.setAutoLinkMask(delivery.getAutoLinkMask());
            mMessage.setText(delivery.getMessage());
            mMessage.setTextColor(ctx.getResources().getColor(delivery.getDesign().isLight() ? R.color.background_material_dark : R.color.background_material_light));
        }else{
            contentPanel.setVisibility(View.GONE);
        }

        // set the custom content
        builder.setView(view);

        // If it isn't material design, apply the button constructs
        if(!delivery.getDesign().isMaterial()){

            // Iterate through config, and setup the button states
            SparseArray<Delivery.ButtonConfig> config = delivery.getButtonConfig();
            int N = config.size();
            for(int i=0; i<N; i++){
                int key = config.keyAt(i);
                final Delivery.ButtonConfig cfg = config.get(key);

                // Pull button Color
                int textColor = delivery.getButtonTextColor(key);
                Spannable title = new SpannableString(cfg.title);
                if(textColor != 0){
                    title.setSpan(new ForegroundColorSpan(textColor), 0, cfg.title.length(), 0);
                }

                // Ensure that they are using the correct which buttons
                switch (key){
                    case AlertDialog.BUTTON_POSITIVE:
                        builder.setPositiveButton(title, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cfg.listener.onClick(dialog, which);
                                if(delivery.getStyle() != null){
                                    delivery.getStyle().onButtonClicked(which, dialog);
                                }
                            }
                        });
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        builder.setNegativeButton(title, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cfg.listener.onClick(dialog, which);
                                if(delivery.getStyle() != null){
                                    delivery.getStyle().onButtonClicked(which, dialog);
                                }
                            }
                        });
                        break;
                    case AlertDialog.BUTTON_NEUTRAL:
                        builder.setNeutralButton(title, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cfg.listener.onClick(dialog, which);
                                if(delivery.getStyle() != null){
                                    delivery.getStyle().onButtonClicked(which, dialog);
                                }
                            }
                        });
                        break;
                }
            }
        }

        if(delivery.getStyle() != null && !delivery.getDesign().isMaterial()){
            Style style = delivery.getStyle();
            customContent.addView(style.getContentView());
        }

        // Create the dialog and return it
        return builder.create();
    }

    /**
     * Apply kerning to a string
     *
     * @param src       the source string
     * @param kerning   the amount of kerning
     * @return          the spannable output
     */
    public static Spannable applyKerning(CharSequence src, float kerning){
        if (src == null) return null;
        final int srcLength = src.length();
        if (srcLength < 2) return src instanceof Spannable
                ? (Spannable)src
                : new SpannableString(src);

        final String nonBreakingSpace = "\u00A0";
        final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                ? (SpannableStringBuilder)src
                : new SpannableStringBuilder(src);
        for (int i = src.length() - 1; i >= 1; i--)
        {
            builder.insert(i, nonBreakingSpace);
            builder.setSpan(new ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }

}
