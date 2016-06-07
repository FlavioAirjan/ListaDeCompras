package com.listadecompras.listadecompras;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private DbController database;


    public void addContext(Context context) {
        this.context = context;
    }


   /* protected void addListaBD(String text) {
       // ContentValues values = new ContentValues();
        //values.put(DataProvider.Lista.NOMELISTA, text);

        getContentResolver().insert(
                DataProvider.Lista.CONTENT_URI, values);
    }*/

    public void removeItem(final int pos){
        runOnUiThread(new Runnable() {
            public void run() {
                if(pastaLista.getListaListas().size()>pos) {
                    for(int i=0;i<pastaLista.getListaListas().get(pos).getListaItens().size();i++){
                        database.deletaItem(pastaLista.getListaListas().get(pos).getListaItens().get(i).getId());
                    }
                    database.deletaLista(pastaLista.getListaListas().get(pos).getKey());
                    pastaLista.getListaListas().remove(pos);
                    adapter.notifyDataSetChanged();
                }

            }
        });


    }
    public void modifyData(int position,String name){
        pastaLista.getListaListas().get(position).setNome(name);
        database.alteraLista(pastaLista.getListaListas().get(position).getKey(),name,pastaLista.getListaListas().get(position).getNumItensChecked());
        notifyData();
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
    public void povoaItens(long fkey,ListaItens itens){
        Cursor cursor=database.carregaItem(fkey);
        String name;
        long key;
        String tipo;
        int check;
        float quant;


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(database.getNomeItem()));
            key=cursor.getLong(cursor.getColumnIndex(database.getIdItem()));
            tipo=cursor.getString(cursor.getColumnIndex(database.getTipoItem()));
            check=cursor.getInt(cursor.getColumnIndex(database.getCheckItem()));
            quant=cursor.getLong(cursor.getColumnIndex(database.getQuantItem()));
            itens.AddItem(new Item(name, quant, tipo,check,key));
        }
        cursor.close();




    }




    private void povoaClasses(){
        Cursor cursor=database.carregaLista();
        String name;
        long key;
        int checked;

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(database.getNomeLista()));
                key=cursor.getLong(cursor.getColumnIndex(database.getIdLista()));
                checked=cursor.getInt(cursor.getColumnIndex(database.getCheckedItens()));
                pastaLista.addLista(name, key,checked);
                povoaItens(key,pastaLista.getListItens(key));

            }
        cursor.close();




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        pastaLista = new PastaListas();
        database= new DbController(context);
        povoaClasses();

       // pastaLista.addLista("Primeira Lista");


        /*for (int i = 0; i < 10; i++) {
            pastaLista.getListItens("Primeira Lista").AddItem(new Item("Item: " + String.valueOf(i), i + 1, "Un"));

        }*/


        final Button button = (Button) findViewById(R.id.add_list_button);

        lv= (ListView) findViewById(R.id.list_last_posts);
        adapter=new CustomAdapterListaListas(this, pastaLista);
        lv.setAdapter(adapter);

        click=0;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    Toast.makeText(context, "Add Lista", Toast.LENGTH_LONG).show();

                    adapter.addItem(new ListaItens("Nova Lista",database.insereLista("Nova Lista",0),0));
                    //addListaBD("outra Lista");
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



