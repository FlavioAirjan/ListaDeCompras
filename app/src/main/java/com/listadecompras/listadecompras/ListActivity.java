package com.listadecompras.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
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
    private TextView total;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0.##");

    public void notifyData(){
        adapter.notifyDataSetChanged();
    }

    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",listaItens);
        returnIntent.putExtra("position",position);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
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


        myAwesomeTextView = (EditText)findViewById(R.id.list_text_view_name);
        myAwesomeTextView.setText(listaItens.getNome());
        //myAwesomeTextView.setClickable(true);
        myAwesomeTextView.setFocusableInTouchMode(false);
        myAwesomeTextView.setTextIsSelectable(false);




        myAwesomeTextView.setOnLongClickListener(
                new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    myAwesomeTextView.setFocusableInTouchMode(true);
                   // holder.menos.setClickable(false);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    //holder.name.setPressed(true);
                    //holder.name.findViewById(R.id.nome_lista).setPressed(true);
                    // holder.name.findViewById(R.id.nome_lista).isInEditMode();

                    return true;
            }

        });

        myAwesomeTextView.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {

                            // the user is done typing.
                            modifyData(v.getEditableText().toString());
                            myAwesomeTextView.setFocusableInTouchMode(false);
                            myAwesomeTextView.setFocusable(false);
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(),0);
                            return true; // consume.


                        }
                        return false;

                    }
                });



        final Button button = (Button) findViewById(R.id.add_item_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(context, "Add Item", Toast.LENGTH_LONG).show();
                long key=database.insereItem("Novo Item",0,0,0,listaItens.getKey());
                adapter.addItem(new Item( "Novo Item" , key));
                updateNumItens();
                adapter.notifyDataSetChanged();
                lv.setSelection(0);
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

        total=(TextView) findViewById(R.id.list_preco);
        total.setText(String.valueOf(REAL_FORMATTER.format(listaItens.getTotal())));

    }

    public void modifyQuant(int pos, float quant){
        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                listaItens.getListaItens().get(pos).getNome(),
                listaItens.getListaItens().get(pos).getPreco(),
                quant,
                listaItens.getListaItens().get(pos).checkInt());

        if(listaItens.getListaItens().get(pos).isCheck()) {
            atualizaPrecoTotal((listaItens.getListaItens().get(pos).getPreco()*quant)-listaItens.getListaItens().get(pos).getPrecoTotal(), pos);
        }
        listaItens.getListaItens().get(pos).setQuantidade(quant);
        notifyData();
    }

    public void modifyPreco(int pos, float preco){
        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                listaItens.getListaItens().get(pos).getNome(),
                preco,
                listaItens.getListaItens().get(pos).getQuantidade(),
                listaItens.getListaItens().get(pos).checkInt());

        if(listaItens.getListaItens().get(pos).isCheck()) {
            atualizaPrecoTotal(preco * listaItens.getListaItens().get(pos).getQuantidade()-listaItens.getListaItens().get(pos).getPrecoTotal(), pos);
        }
        listaItens.getListaItens().get(pos).setPreco(preco);

        notifyData();
    }

    public void atualizaPrecoTotal(float novoPreco,int pos){
            listaItens.setTotal(listaItens.getTotal() + novoPreco);
            database.alteraLista(listaItens.getKey(), listaItens.getNome(), listaItens.getNumItensChecked(), listaItens.getTotal());

            total.setText(String.valueOf(REAL_FORMATTER.format(listaItens.getTotal())));

    }

    public void modifyCheck(int pos, int check){
        //atualiza o db com o item checked
        database.alteraItem(listaItens.getListaItens().get(pos).getId(),
                listaItens.getKey(),
                listaItens.getListaItens().get(pos).getNome(),
                listaItens.getListaItens().get(pos).getPreco(),
                listaItens.getListaItens().get(pos).getQuantidade(),
                check);

        //check item e muda posicao na lista
        if(check==1) {
            listaItens.getListaItens().get(pos).setCheck(true);
            atualizaPrecoTotal(listaItens.getListaItens().get(pos).getPrecoTotal(),pos);
            listaItens.getListaItens().add(listaItens.getListaItens().get(pos));
            listaItens.getListaItens().remove(pos);
            listaItens.setNumItensChecked(listaItens.getNumItensChecked()+1);

        }else{
            listaItens.getListaItens().get(pos).setCheck(false);
            atualizaPrecoTotal(-listaItens.getListaItens().get(pos).getPrecoTotal(),pos);
            listaItens.getListaItens().add(0,listaItens.getListaItens().get(pos));
            listaItens.getListaItens().remove(pos+1);
            listaItens.setNumItensChecked(listaItens.getNumItensChecked()-1);

        }

        //Atualiza o db com a lista com o número checked atualizado
        database.alteraLista(listaItens.getKey(),listaItens.getNome(),listaItens.getNumItensChecked(),listaItens.getTotal());
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
                    atualizaPrecoTotal(-listaItens.getListaItens().get(pos).getPrecoTotal(),pos);
                    listaItens.getListaItens().remove(pos);
                    listaItens.setNumItensChecked(listaItens.getNumItensChecked()-1);

                    database.alteraLista(listaItens.getKey(),listaItens.getNome(),listaItens.getNumItensChecked(),listaItens.getTotal());
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
                listaItens.getListaItens().get(pos).getPreco(),
                listaItens.getListaItens().get(pos).getQuantidade(),
                listaItens.getListaItens().get(pos).checkInt());
        listaItens.getListaItens().get(pos).setNome(nome);
        notifyData();
    }

    public void modifyData(String name){
        listaItens.setNome(name);
        database.alteraLista(listaItens.getKey(),name,listaItens.getNumItensChecked(),listaItens.getTotal());
        notifyData();
    }



}

