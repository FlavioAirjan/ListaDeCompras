package com.listadecompras.listadecompras;


import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragmentPasta())
                    .commit();
        }


    }

    public static class PlaceholderFragmentPasta extends Fragment {

        private PastaListas pastaLista;

        public PlaceholderFragmentPasta() {
            this.pastaLista=new PastaListas();
        }

        public void addItens(List<String> list,ListaItens listaDeItens){
            for(int i=0;i<listaDeItens.getListaItens().size();i++) {
                list.add(listaDeItens.getListaItens().get(i).getNome());
            }
        }

        public void addListas(List<String> list,PastaListas listaDeLista){
            for(int i=0;i<listaDeLista.getListaListas().size();i++) {
                list.add(listaDeLista.getListaListas().get(i).getNome());
            }
        }




        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            pastaLista.addLista("Primeira Lista");

            for(int i=0;i<10;i++){
            pastaLista.getListItens("Primeira Lista").AddItem(new Item("Item: "+String.valueOf(i)));
            }
            // Criando uma lista (ArrayList) com os dados criados acima
            List<String> listOfLastPosts = new ArrayList<>();

            //addItens(listOfLastPosts,pastaLista.getListItens("NOVA"));
            addListas(listOfLastPosts,pastaLista);

            // Agora que já temos os dados, vamos criar um Adapter, no caso um ArrayAdapter
            ArrayAdapter<String> listOfLastPostsAdapter = new ArrayAdapter<String>(
                    getActivity(), // O contexto atual
                    R.layout.list_item_last_posts, // O arquivo de layout de cada item
                    R.id.list_item_post_title_textview, // O ID do campo a ser preenchido
                    listOfLastPosts // A fonte dos dados
            );
            // Inflamos o layout principal
            View rootView = inflater.inflate(R.layout.activity_main, container, false);

            // Cria uma referência para a ListView
            final ListView listView = (ListView) rootView.findViewById(R.id.list_last_posts);
            listView.setAdapter(listOfLastPostsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String item = pastaLista.getListaListas().get(position).getNome();//getListItens("NOVA").getListaItens().get(position).getNome();
                    Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                    //Vai para a lista de itens clicada
                    Intent i = new Intent(view.getContext(), ListActivity.class);
                    i.putExtra("listObject", pastaLista.getListaListas().get(position));
                    startActivity(i);

                }
            });

            // Retornamos tudo
            return rootView;
        }
    }

}

