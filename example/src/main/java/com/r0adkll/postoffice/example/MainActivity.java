package com.r0adkll.postoffice.example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.r0adkll.postoffice.PostOffice;
import com.r0adkll.postoffice.model.Delivery;
import com.r0adkll.postoffice.model.Design;
import com.r0adkll.postoffice.styles.EditTextStyle;
import com.r0adkll.postoffice.styles.ProgressStyle;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.alert_holo)            Button mAlertHolo;
    @InjectView(R.id.alert_material)        Button mAlertMaterial;
    @InjectView(R.id.edittext_holo)         Button mEdittextHolo;
    @InjectView(R.id.edittext_material)     Button mEdittextMaterial;
    @InjectView(R.id.progress_holo)         Button mProgressHolo;
    @InjectView(R.id.progress_material)     Button mProgressMaterial;
    @InjectView(R.id.list_holo)             Button mListHolo;
    @InjectView(R.id.list_material)         Button mListMaterial;
    @InjectView(R.id.defaultDialog)         Button mDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mAlertHolo.setOnClickListener(this);
        mAlertMaterial.setOnClickListener(this);
        mEdittextHolo.setOnClickListener(this);
        mEdittextMaterial.setOnClickListener(this);
        mProgressHolo.setOnClickListener(this);
        mProgressMaterial.setOnClickListener(this);
        mListHolo.setOnClickListener(this);
        mListMaterial.setOnClickListener(this);
        mDefault.setOnClickListener(this);

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        Delivery delivery = null;
        String tag = "";
        switch (view.getId()){
            case R.id.defaultDialog:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title)
                        .setMessage(R.string.message)
                        .setPositiveButton(R.string.action1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Default Closed.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
                break;

            case R.id.alert_holo:
                tag = "ALERT_HOLO";

                // Create and show holo alert style
                delivery = PostOffice.newMail(this)
                        .setTitle(R.string.title)
                        .setThemeColor(R.color.background_material_light)
                        .setMessage(R.string.message)
                        .setDesign(Design.HOLO_DARK)
                        .setButton(Dialog.BUTTON_POSITIVE, R.string.action1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something with teh clicks
                                Toast.makeText(MainActivity.this, "Alert Holo Closed.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .build();


                break;
            case R.id.alert_material:
                tag = "ALERT_MATERIAL";

                // Create and show holo alert style
                delivery = PostOffice.newMail(this)
                        .setTitle(R.string.title)
                        .setThemeColor(R.color.material_green_700)
                        .setMessage(R.string.message)
                        .setDesign(Design.MATERIAL_DARK)
                        .setCanceledOnTouchOutside(false)
                        .setButton(Dialog.BUTTON_POSITIVE, R.string.action1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something with teh clicks
                                Toast.makeText(MainActivity.this, "Alert Material Closed.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .build();
                break;

            case R.id.edittext_holo:
                tag = "EDITTEXT_HOLO";

                delivery = PostOffice.newMail(this)
                        .setTitle(R.string.title)
                        .setThemeColor(R.color.material_red_700)
                        .setDesign(Design.HOLO_DARK)
                        .showKeyboardOnDisplay(true)
                        .setButton(Dialog.BUTTON_POSITIVE, R.string.action1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setButton(Dialog.BUTTON_NEGATIVE, R.string.action2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setStyle(new EditTextStyle.Builder(this)
                                        .setHint("Email")
                                        .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                                        .setOnTextAcceptedListener(new EditTextStyle.OnTextAcceptedListener() {
                                            @Override
                                            public void onAccepted(String text) {
                                                Toast.makeText(MainActivity.this, "Text was accepted: " + text, Toast.LENGTH_SHORT).show();
                                            }
                                        }).build())
                        .build();

                break;
            case R.id.edittext_material:
                tag = "EDITTEXT_MATERIAL";

                delivery = PostOffice.newMail(this)
                        .setTitle(R.string.title)
                        .setThemeColor(R.color.material_blue_500)
                        .setDesign(Design.MATERIAL_DARK)
                        .showKeyboardOnDisplay(true)
                        .setButton(Dialog.BUTTON_POSITIVE, R.string.action1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setButton(Dialog.BUTTON_NEGATIVE, R.string.action2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setStyle(new EditTextStyle.Builder(this)
                                .setHint("Email")
                                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                                .setOnTextAcceptedListener(new EditTextStyle.OnTextAcceptedListener() {
                                    @Override
                                    public void onAccepted(String text) {
                                        Toast.makeText(MainActivity.this, "Text was accepted: " + text, Toast.LENGTH_SHORT).show();
                                    }
                                }).build())
                        .build();

                break;

            case R.id.progress_holo:
                tag = "PROGRESS_HOLO";
                delivery = PostOffice.newMail(this)
                        .setThemeColor(R.color.material_yellow_700)
                        .setDesign(Design.HOLO_LIGHT)
                        .setStyle(new ProgressStyle(this))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .build();

                break;
            case R.id.progress_material:
                tag = "PROGRESS_MATERIAL";
                delivery = PostOffice.newMail(this)
                        .setThemeColor(R.color.material_blue_500)
                        .setDesign(Design.MATERIAL_DARK)
                        .setStyle(new ProgressStyle(this))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .build();

                break;

            case R.id.list_holo:
                tag = "LIST_HOLO";

                break;
            case R.id.list_material:
                tag = "LIST_MATERIAL";

                break;
        }

        // Show the delivery
        if(delivery != null)
            delivery.show(getFragmentManager(), tag);
    }
}
