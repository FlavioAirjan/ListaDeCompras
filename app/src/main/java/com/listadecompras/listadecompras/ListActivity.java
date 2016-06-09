package com.listadecompras.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
    int position;
    private DbController database;
    private TextView numItens;

    public void notifyData(){
        adapter.notifyDataSetChanged();
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        //inicia DB

        //povoaClasses();

        Intent i = getIntent();
        listaItens=(ListaItens)i.getSerializableExtra("listObject");
        position=(int)i.getSerializableExtra("position");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista_compras);
        context = this;
        database= new DbController(context);
        lv= (ListView) findViewById(R.id.list_itens);
        adapter=new CustomAdapterListaItens(this, listaItens);
        lv.setAdapter(adapter);



        myAwesomeTextView = (TextView)findViewById(R.id.list_text_view_name);
        myAwesomeTextView.setText(listaItens.getNome());


        final Button button = (Button) findViewById(R.id.add_item_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(context, "Add Item", Toast.LENGTH_LONG).show();
                long key=database.insereItem("novo","Un",0,0,listaItens.getKey());
                adapter.addItem(new Item( "novo" , key));
                updateNumItens();
                adapter.notifyDataSetChanged();
                lv.setSelection(lv.getAdapter().getCount()-1);
            }
        });

        final Button buttonMenu = (Button) findViewById(R.id.menu_inicial);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "Menu Inicial", Toast.LENGTH_LONG).show();
               // Intent i = new Intent(v.getContext(), MainActivity.class);
               // startActivity(i);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",listaItens);
                returnIntent.putExtra("position",position);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        numItens= (TextView) findViewById(R.id.list_item_number);
        updateNumItens();//numItens.setText(String.valueOf(listaItens.getListaItens().size()-listaItens.getNumItensChecked()));

    }

    public void modifyQuant(int pos, float quant){
        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                listaItens.getListaItens().get(pos).getNome(),
                listaItens.getListaItens().get(pos).getTipo(),
                quant,
                listaItens.getListaItens().get(pos).checkInt());

        listaItens.getListaItens().get(pos).setQuantidade(quant);
        notifyData();
    }

    public void modifyTipo(int pos, String tipo){
        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                listaItens.getListaItens().get(pos).getNome(),
                tipo,
                listaItens.getListaItens().get(pos).getQuantidade(),
                listaItens.getListaItens().get(pos).checkInt());

        listaItens.getListaItens().get(pos).setTipo(tipo);
        notifyData();
    }

    public void modifyCheck(int pos, int check){
        //atualiza o db com o item checked
        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                listaItens.getListaItens().get(pos).getNome(),
                listaItens.getListaItens().get(pos).getTipo(),
                listaItens.getListaItens().get(pos).getQuantidade(),
                check);

        if(check==1) {
            listaItens.getListaItens().get(pos).setCheck(true);
            listaItens.setNumItensChecked(listaItens.getNumItensChecked()+1);
        }else{
            listaItens.getListaItens().get(pos).setCheck(false);
            listaItens.setNumItensChecked(listaItens.getNumItensChecked()-1);
        }

        //Atualiza o db com a lista com o número checked atualizado
        database.alteraLista(listaItens.getKey(),listaItens.getNome(),listaItens.getNumItensChecked());
        updateNumItens();
        notifyData();
    }

    //atualiza o textview com o novo valor de itens não checked
    private void updateNumItens(){
        if(listaItens.getListaItens().size()-listaItens.getNumItensChecked()>0) {
            numItens.setTextColor(Color.RED);
        }else{
            numItens.setTextColor(Color.rgb(63,81,181));
        }
        numItens.setText(String.valueOf(listaItens.getListaItens().size()-listaItens.getNumItensChecked()));
    }


    public void removeItem(final int pos){
        runOnUiThread(new Runnable() {
            public void run() {
                database.deletaItem(listaItens.getListaItens().get(pos).getId());

                //Se o item deletado estava checked
                if(listaItens.getListaItens().get(pos).isCheck()){
                    listaItens.getListaItens().remove(pos);
                    listaItens.setNumItensChecked(listaItens.getNumItensChecked()-1);
                    database.alteraLista(listaItens.getKey(),listaItens.getNome(),listaItens.getNumItensChecked());
                }else{
                    listaItens.getListaItens().remove(pos);
                    updateNumItens();
                }

                adapter.notifyDataSetChanged();
            }
        });



    }

    public void modifyName(String nome,int pos){

        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                nome,
                listaItens.getListaItens().get(pos).getTipo(),
                listaItens.getListaItens().get(pos).getQuantidade(),
                listaItens.getListaItens().get(pos).checkInt());
        listaItens.getListaItens().get(pos).setNome(nome);
        notifyData();
    }



}

