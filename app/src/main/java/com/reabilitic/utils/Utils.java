package com.reabilitic.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.DisplayMetrics;

import com.reabilitic.R;

public class Utils {
    private static ProgressDialog pd;

    public static int calculateNoOfColumns(Context context, int width) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / width);
        return noOfColumns;
    }


    public static void showLoading(Context context) {
        pd = new ProgressDialog(context);
        pd.setCancelable(true);
        pd.setTitle(R.string.all_text_loading_dialog_title);
        pd.setMessage(context.getString(R.string.all_text_loading_dialog_desc));
        pd.show();
    }

    public static void showLoading(Context context, String title) {
        pd = new ProgressDialog(context);
        pd.setCancelable(true);
        pd.setTitle(title);
        pd.setMessage(context.getString(R.string.all_text_loading_dialog_desc));
        pd.show();
    }

    public static void hideLoading() {
        if (pd != null)
            pd.dismiss();
    }
}
