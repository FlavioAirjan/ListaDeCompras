package com.listadecompras.listadecompras;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Flavio on 25/05/2016.
 */
public class Dialog {
    private String returno;
    private AlertDialog.Builder builder;

    public Dialog() {

    }


    public void makeBoolDialog(Context context, String title,String text,String posit,String negat){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);//new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        builder.setTitle(title);

        TextView input = new TextView(context);
        input.setText(text);
        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton(posit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            //return true;
            }
        });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });

        builder.show();

    }

}
