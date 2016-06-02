package com.listadecompras.listadecompras;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Flavio on 23/05/2016.
 */
public class CustomAdapterListaItens  extends BaseAdapter{
    ListaItens listaItens;
    ListActivity listActivity;
    Context context;
    int click;

    private static LayoutInflater inflater=null;

    public CustomAdapterListaItens (ListActivity listActivity,ListaItens listaItens) {
        // TODO Auto-generated constructor stub
        this.listaItens=listaItens;
        this.context=listActivity;
        this.listActivity=listActivity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(Item item){
        listaItens.AddItem(item);
    }


    private void makeDialog(int position,String tipo){
        final int pos=position;
        final String tip=tipo;
        final String result;
        boolean dialog=false;
        //String text=tipo;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);//new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        builder.setTitle(tip);

        final EditText input = new EditText(context);

        switch (tip) {
            case "Nome": // Set up the input
                input.setText(listaItens.getListaItens().get(pos).getNome());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                builder.setView(input);
                dialog=true;
                break;
            case "Quantidade":// Set up the input
                input.setText(String.valueOf(listaItens.getListaItens().get(pos).getQuantidade()));
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                dialog=true;
                break;
            case "Tipo":// Set up the input
                //input.setText(String.valueOf(listaItens.getListaItens().get(pos).getQuantidade()));
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                String[] types = {"Un", "Kg"};
                builder.setItems(types,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch(which){
                            case 0:
                                listActivity.modifyTipo(pos,"Un");
                                break;
                            case 1:
                                listActivity.modifyTipo(pos,"Kg");
                                break;
                        }

                        listActivity.notifyData();
                    }

                });

                builder.show();
                break;

        }


// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (tip) {
                    case "Nome": listaItens.getListaItens().get(pos).setNome(input.getText().toString());
                        listActivity.notifyData();
                        break;
                    case "Quantidade": listaItens.getListaItens().get(pos).setQuantidade(Float.valueOf(input.getText().toString()));
                        listActivity.notifyData();
                        break;

                }
                /*makeChange(input.getText().toString(),pos,tip);
                //listaItens.getListaItens().get(pos).setNome(input.getText().toString());
                listActivity.notifyData();*/

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        if(dialog) {
            builder.show();
        }
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

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaItens.getListaItens().size();
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
        EditText quantidade;
        TextView tipo;
        Button menos;
        CheckBox checkBox;
    }
    @Override
    public View getView(final int position,final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.lista_itens, null);

        holder.name=(EditText) rowView.findViewById(R.id.nomeItem);
        holder.quantidade=(EditText) rowView.findViewById(R.id.quantItem);
        holder.tipo=(TextView) rowView.findViewById(R.id.tipoItem);
        holder.checkBox=(CheckBox) rowView.findViewById(R.id.checkItem);

        holder.name.setText(listaItens.getListaItens().get(position).getNome());
        holder.quantidade.setText(String.valueOf(listaItens.getListaItens().get(position).getQuantidade()));
        holder.tipo.setText(listaItens.getListaItens().get(position).getTipo());
        holder.checkBox.setChecked(listaItens.getListaItens().get(position).isCheck());


        holder.menos=(Button) rowView.findViewById(R.id.btn_menos);




        rowView.findViewById(R.id.btn_menos).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                makeBoolDialog(context,"Remove","Você quer deletar o item '" +listaItens.getListaItens().get(position).getNome()+"'?","Sim","Não",position);
            }
        });


        rowView.findViewById(R.id.checkItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listaItens.getListaItens().get(position).isCheck()) {
                    listActivity.modifyCheck(position,1);

                    Toast.makeText(context, "You Checked "+listaItens.getListaItens().get(position).getNome(), Toast.LENGTH_LONG).show();
                }else{
                    listActivity.modifyCheck(position,0);
                    Toast.makeText(context, "You Unchecked "+listaItens.getListaItens().get(position).getNome(), Toast.LENGTH_LONG).show();
                }

                listActivity.notifyData();
            }
        });



        ((EditText) rowView.findViewById(R.id.nomeItem)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {

                    // the user is done typing.


                    listActivity.modifyName(v.getEditableText().toString(),position);

                    if (rowView.hasFocus()) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(rowView.getWindowToken(), 0);
                    }
                        holder.name.setFocusable(false);
                    holder.checkBox.setClickable(true);
                    holder.menos.setClickable(true);
                    holder.quantidade.setClickable(true);
                    holder.tipo.setClickable(true);
                    return true; // consume.


                }
                return false;

            }
        });

        rowView.findViewById(R.id.nomeItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // makeDialog(position,"Nome");
                holder.name.setFocusable(true); //setFocusableInTouchMode(true);
                holder.checkBox.setClickable(false);
                holder.menos.setClickable(false);
                holder.quantidade.setClickable(false);
                holder.tipo.setClickable(false);

               // InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
              //  imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            }
        });

        ((EditText) rowView.findViewById(R.id.quantItem)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {

                            // the user is done typing.

                            listActivity.modifyQuant(position,Float.valueOf(v.getEditableText().toString()));

                            if (rowView.hasFocus()) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(rowView.getWindowToken(), 0);
                            }
                            holder.quantidade.setFocusable(false);
                            holder.checkBox.setClickable(true);
                            holder.menos.setClickable(true);
                            holder.name.setClickable(true);
                            holder.tipo.setClickable(true);
                            return true; // consume.


                        }
                        return false;

                    }
                });

        rowView.findViewById(R.id.quantItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // makeDialog(position,"Nome");
                holder.quantidade.setFocusable(true); //setFocusableInTouchMode(true);
                holder.checkBox.setClickable(false);
                holder.menos.setClickable(false);
                holder.name.setClickable(false);
                holder.tipo.setClickable(false);
                // InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                //  imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            }
        });



        /*rowView.findViewById(R.id.quantItem).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                makeDialog(position,"Quantidade");
            }
        });*/

        rowView.findViewById(R.id.tipoItem).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                makeDialog(position,"Tipo");

               // Toast.makeText(context, "You Clicked "+listaItens.getListaItens().get(position).getTipo(), Toast.LENGTH_LONG).show();
            }
        });

        /*rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+listaItens.getListaItens().get(position).getNome(), Toast.LENGTH_LONG).show();
            }
        });*/



        return rowView;
    }



}
