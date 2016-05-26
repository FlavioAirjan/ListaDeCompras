package com.listadecompras.listadecompras;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PastaListas pastaLista;
    private Context context;
    private ArrayAdapter<String> listOfLastPostsAdapter;
    ListView lv;
    CustomAdapterListaListas adapter;
    int click;


    public void addContext(Context context) {
        this.context = context;
    }

    public void addItens(List<String> list, ListaItens listaDeItens) {
        for (int i = 0; i < listaDeItens.getListaItens().size(); i++) {
            list.add(listaDeItens.getListaItens().get(i).getNome());
        }
    }

    public void addListas(List<String> list, PastaListas listaDeLista) {
        for (int i = 0; i < listaDeLista.getListaListas().size(); i++) {
            list.add(listaDeLista.getListaListas().get(i).getNome());
        }
    }

    public void removeItem(int pos){
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                pastaLista.updateListaIten((ListaItens)data.getSerializableExtra("result"),(int)data.getSerializableExtra("position"));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(context, "No result", Toast.LENGTH_LONG).show();
            }
        }
    }//onActivityResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        pastaLista = new PastaListas();



        pastaLista.addLista("Primeira Lista");


        for (int i = 0; i < 10; i++) {
            pastaLista.getListItens("Primeira Lista").AddItem(new Item("Item: " + String.valueOf(i), i + 1, "un"));

        }

        final Button button = (Button) findViewById(R.id.add_list_button);

        lv= (ListView) findViewById(R.id.list_last_posts);
        adapter=new CustomAdapterListaListas(this, pastaLista);
        lv.setAdapter(adapter);

        click=0;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    Toast.makeText(context, "Add Lista", Toast.LENGTH_LONG).show();
                    adapter.addItem(new ListaItens("outra Lista"));
                    adapter.notifyDataSetChanged();


            }
        });


    }
    public void startActivity(int position){
        Intent i = new Intent(this, ListActivity.class);

        i.putExtra("listObject", pastaLista.getListaListas().get(position));
        i.putExtra("position",position);
        //startActivity(i);
        startActivityForResult(i, 2);
    }
    public void notifyData(){
        adapter.notifyDataSetChanged();
    }

}



