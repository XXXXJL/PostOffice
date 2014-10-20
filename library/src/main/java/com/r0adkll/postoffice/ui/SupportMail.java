package com.r0adkll.postoffice.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.ftinc.fontloader.FontLoader;
import com.ftinc.fontloader.Types;
import com.r0adkll.postoffice.R;
import com.r0adkll.postoffice.model.Delivery;
import com.r0adkll.postoffice.styles.EditTextStyle;
import com.r0adkll.postoffice.styles.Style;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Project: PostOffice
 * Package: com.r0adkll.postoffice.ui
 * Created by drew.heavner on 8/20/14.
 */
public class SupportMail extends DialogFragment implements MailboxInterface{

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
     * Variables
     *
     */

    private Mailbox mMailbox;

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
        mMailbox.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = mMailbox.onCreateView(inflater, container, savedInstanceState);
        if(layout == null)
            layout = super.onCreateView(inflater, container, savedInstanceState);

        return layout;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return mMailbox.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mMailbox.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mMailbox.onCancel(dialog);
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
        mMailbox = Mailbox.createInstance(delivery, this);
    }

    /**
     * Create the default {@link android.app.Dialog} object for this fragment
     *
     * @param icicle        the savedInstanceState
     * @return              the native Dialog object
     */
    @Override
    public Dialog createSuperDialog(Bundle icicle) {
        return super.onCreateDialog(icicle);
    }

}
