package com.listadecompras.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavio on 19/05/2016.
 */
public class ListActivity extends AppCompatActivity {

    private ListaItens listaItens;
    TextView myAwesomeTextView;
    ListView lv;
    Context context;
    CustomAdapterListaItens adapter;
    private static LayoutInflater inflater=null;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        Intent i = getIntent();
        listaItens=(ListaItens)i.getSerializableExtra("listObject");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista_compras);
        context = this;

        lv= (ListView) findViewById(R.id.list_itens);
        adapter=new CustomAdapterListaItens(this, listaItens);
        lv.setAdapter(adapter);

        myAwesomeTextView = (TextView)findViewById(R.id.list_text_view_name);
        myAwesomeTextView.setText(listaItens.getNome());

        final Button button = (Button) findViewById(R.id.add_item_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "Add Item", Toast.LENGTH_LONG).show();
                adapter.addItem(new Item("novo"));
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void removeItem(int pos){
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });



    }



}

