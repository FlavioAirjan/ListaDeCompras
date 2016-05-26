package com.listadecompras.listadecompras;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by Flavio on 25/05/2016.
 */
public class Dialog {
    private String returno;

    public Dialog() {

    }

    public String getText(String text, Context context){
        makeDialog(text, context);
        return returno;
    }
    private void makeDialog(String text, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(text);


// Set up the input
        final EditText input = new EditText(context);
        input.setText(text);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returno = input.getText().toString();


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
