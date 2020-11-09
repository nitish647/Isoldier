package com.deepak.isoldier;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Toast;

public class Helper_class {


    public static void show_toast(Context context , String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static GradientDrawable set_gradient(String color1 , String color2, int radius, GradientDrawable.Orientation orientation)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
//        int[] colors = {Color.parseColor(color1),Color.parseColor(color2)};
        gradientDrawable.setCornerRadius((float) radius);
        if(color1.contains("null"))
        gradientDrawable.setColors(new int[]{Color.TRANSPARENT,Color.TRANSPARENT});
        else
      gradientDrawable.setColors(new int[]{Color.parseColor(color1),Color.parseColor(color2)});
        gradientDrawable.setOrientation(orientation);
        return gradientDrawable;
    }
    public static GradientDrawable set_gradient2(String color1 , String color2, int radius, GradientDrawable.Orientation orientation)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
//        int[] colors = {Color.parseColor(color1),Color.parseColor(color2)};
       float [] radii = { 8, 80, 80, 8};
        gradientDrawable.setCornerRadii(radii);

        gradientDrawable.setColors(new int[]{Color.parseColor(color1),Color.parseColor(color2)});
        gradientDrawable.setOrientation(orientation);
        return gradientDrawable;
    }
}
