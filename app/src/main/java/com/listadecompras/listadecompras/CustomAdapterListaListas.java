package com.listadecompras.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

    public void addItem(ListaItens item){
        pastaListas.addListaIten(item);
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
        TextView name;
        Button menos;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item_last_posts, null);

        holder.name=(TextView) rowView.findViewById(R.id.nome_lista);
        holder.name.setText(pastaListas.getListaListas().get(position).getNome());


        holder.menos=(Button) rowView.findViewById(R.id.btn_menos_lista);

        rowView.findViewById(R.id.btn_menos_lista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Iem: "+pastaListas.getListaListas().get(position).getNome(), Toast.LENGTH_LONG).show();
                pastaListas.getListaListas().remove(position);
                listActivity.removeItem(position);

            }
        });

        click = 0;
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
                    handler.postDelayed(r, 600);
                    // TODO Auto-generated method stub

                } else if (click == 2) {
                    //Double click
                    click = 0;
                    Toast.makeText(context, "Muda o nome", Toast.LENGTH_LONG).show();

                }


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
