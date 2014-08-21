package com.r0adkll.postoffice.styles;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.r0adkll.postoffice.R;
import com.r0adkll.postoffice.model.Design;

/**
 * Created by r0adkll on 8/21/14.
 */
public class ProgressStyle implements Style {

    View layout;
    ProgressBar progress;
    TextView prog, total;

    public ProgressStyle(Context ctx){
        layout = LayoutInflater.from(ctx).inflate(R.layout.layout_progress_style, null, false);
        progress = (ProgressBar) layout.findViewById(R.id.progressBar);
        prog = (TextView) layout.findViewById(R.id.progress);
        total = (TextView) layout.findViewById(R.id.total);
    }

    @Override
    public View getContentView() {
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, layout.getResources().getDisplayMetrics());
        layout.setPadding(padding, padding, padding, padding);
        return layout;
    }

    @Override
    public void applyDesign(Design design, int themeColor) {

        LayerDrawable drawable = (LayerDrawable) layout.getResources().getDrawable(R.drawable.progress_material_horizontal);

        Drawable bg = drawable.getDrawable(0);
        int color = design.isLight() ? R.color.material_grey_700 : R.color.material_grey_500;
        bg.setColorFilter(layout.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

        Drawable secProg = drawable.getDrawable(1);
        secProg.setColorFilter(lighten(themeColor), PorterDuff.Mode.SRC_ATOP);

        Drawable prg = drawable.getDrawable(2);
        prg.setColorFilter(themeColor, PorterDuff.Mode.SRC_ATOP);

        progress.setProgressDrawable(drawable);

        if(design.isLight()){
            prog.setTextColor(layout.getResources().getColor(R.color.tertiary_text_material_light));
            total.setTextColor(layout.getResources().getColor(R.color.tertiary_text_material_light));
        }else{
            prog.setTextColor(layout.getResources().getColor(R.color.tertiary_text_material_dark));
            total.setTextColor(layout.getResources().getColor(R.color.tertiary_text_material_dark));
        }

    }

    @Override
    public void onButtonClicked(int which, DialogInterface dialogInterface) {

    }

    private int lighten(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = 1.0f - 0.8f * (1.0f - hsv[2]);
        color = Color.HSVToColor(hsv);
        return color;
    }
}
