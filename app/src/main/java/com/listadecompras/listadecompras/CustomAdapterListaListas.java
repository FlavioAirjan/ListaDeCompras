package com.listadecompras.listadecompras;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Flavio on 24/05/2016.
 */
public class CustomAdapterListaListas extends BaseAdapter {
    PastaListas pastaListas;
    MainActivity listActivity;
    Context context;
    int click;

    private static LayoutInflater inflater=null;

    public CustomAdapterListaListas (MainActivity listActivity,PastaListas pastaListas) {
        // TODO Auto-generated constructor stub
        this.pastaListas=pastaListas;
        this.context=listActivity;
        this.listActivity=listActivity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public void makeBoolDialog(Context context, String title,String text,String posit,String negat,final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);//new ContextThemeWrapper(context, R.style.AlertDialogCustom));

        LayoutInflater li =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.alert_dialog, null);
        builder.setView(view);

        TextView input = (TextView)view.findViewById(R.id.text);
        input.setGravity(Gravity.CENTER);
        input.setText(text);


        TextView title_edited = new TextView(context);
// You Can Customise your Title here
        title_edited.setText(title);
        title_edited.setBackgroundColor(Color.DKGRAY);
        title_edited.setPadding(10, 10, 10, 10);
        title_edited.setGravity(Gravity.CENTER);
        title_edited.setTextColor(Color.WHITE);
        title_edited.setTextSize(20);

        builder.setCustomTitle(title_edited);

// Set up the buttons
        builder.setPositiveButton(posit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //return true;
                listActivity.removeItem(position);
            }
        });

        builder.setNegativeButton(negat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();


        dialog.show();

    }

    public void addItem(ListaItens item){
        pastaListas.addListaIten(item);
        //DbController crud = new DbController(context);
        //long id=crud.insereLista(item.getNome());
        //item.setKey(id);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pastaListas.getListaListas().size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public class Holder
    {
        EditText name;
        Button menos;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.list_item_last_posts, null);

        holder.name=(EditText) rowView.findViewById(R.id.nome_lista);
        holder.name.setText(pastaListas.getListaListas().get(position).getNome());
        holder.name.setFocusableInTouchMode(false);
        holder.name.setClickable(true);

        holder.menos=(Button) rowView.findViewById(R.id.btn_menos_lista);

        rowView.findViewById(R.id.btn_menos_lista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(pastaListas.getListaListas().size()>position) {
                    makeBoolDialog(context, "Remove", "Você quer deletar a lista '" + pastaListas.getListaListas().get(position).getNome() + "'?", "Sim", "Não", position);
                }
            }
        });


        rowView.findViewById(R.id.nome_lista).setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View arg0) {
                //makeDialog(position);
                //holder.name.findViewById(R.id.nome_lista).setFocusable(true);
                //holder.name.findViewById(R.id.nome_lista).setClickable(false);
                holder.name.setFocusableInTouchMode(true);
                holder.menos.setClickable(false);

                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                //holder.name.setPressed(true);
                //holder.name.findViewById(R.id.nome_lista).setPressed(true);
               // holder.name.findViewById(R.id.nome_lista).isInEditMode();

                return true;
            }
        });


        ((EditText) rowView.findViewById(R.id.nome_lista)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {

                            // the user is done typing.
                            listActivity.modifyData(position,v.getEditableText().toString());
                            holder.name.setFocusableInTouchMode(false);
                            holder.menos.setClickable(true);
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(rowView.getWindowToken(),0);
                            //holder.name.findViewById(R.id.nome_lista).setClickable(true);
                            return true; // consume.


                        }
                        return false;

                    }
                });



        rowView.findViewById(R.id.nome_lista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String item = pastaListas.getListaListas().get(position).getNome();//getListItens("NOVA").getListaItens().get(position).getNome();
                //Toast.makeText(context, item, Toast.LENGTH_LONG).show();
                //holder.name.findViewById(R.id.nome_lista).setFocusable(true);
                if(holder.name.isFocusableInTouchMode()) {

                }else{
                    listActivity.startActivity(position);
                }
            }
        });
        /*click = 0;
        rowView.findViewById(R.id.nome_lista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click++;
                Handler handler = new Handler();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        if (click == 1) {
                            String item = pastaListas.getListaListas().get(position).getNome();//getListItens("NOVA").getListaItens().get(position).getNome();
                            Toast.makeText(context, item, Toast.LENGTH_LONG).show();
                            listActivity.startActivity(position);
                        }
                        click = 0;
                    }
                };

                if (click == 1) {
                    //Single click
                    handler.postDelayed(r, 400);

                    //holder.name.setClickable(true);
                    // TODO Auto-generated method stub

                } else if (click == 2) {
                    //Double click
                    click = 0;

                    makeDialog(position);
                    Toast.makeText(context, "teste", Toast.LENGTH_LONG).show();




                }


            }
        });*/


        return rowView;
    }



}
