package org.saarang.instieventsapp.Utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Moochi on 26-08-2015.
 */
public class UIUtils {


    public static void showSnackBar(View view, String message){

        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackView = snackBar.getView();
        TextView tv = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.show();

    }
}
