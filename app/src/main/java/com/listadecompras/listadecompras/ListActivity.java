package com.listadecompras.listadecompras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavio on 19/05/2016.
 */
public class ListActivity extends AppCompatActivity {

    TextView myAwesomeTextView;




    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);
        Intent i = getIntent();
        myAwesomeTextView = (TextView)findViewById(R.id.list_text_view_name);
        myAwesomeTextView.setText(((ListaItens)i.getSerializableExtra("listObject")).getNome());
        if (savedInstanceState == null) {
            PlaceholderFragmentList fragment=new PlaceholderFragmentList();
            fragment.setListaItens((ListaItens)i.getSerializableExtra("listObject"));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    public static class PlaceholderFragmentList extends Fragment {

        private ListaItens listaItens;

        public PlaceholderFragmentList() {
            this.listaItens=new ListaItens("novo");
        }

        public void addItens(List<String> list, ListaItens listaDeItens) {
            for (int i = 0; i < listaDeItens.getListaItens().size(); i++) {
                list.add(listaDeItens.getListaItens().get(i).getNome());
            }
        }

        public void setListaItens(ListaItens itens) {
            this.listaItens=itens;
        }




        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            List<String> listOfItens = new ArrayList<>();

            addItens(listOfItens, listaItens);
            //addListas(listOfLastPosts,pastaLista);

            // Agora que já temos os dados, vamos criar um Adapter, no caso um ArrayAdapter
            ArrayAdapter<String> listOfLastPostsAdapter = new ArrayAdapter<String>(
                    getActivity(), // O contexto atual
                    R.layout.list_item_last_posts, // O arquivo de layout de cada item
                    R.id.list_item_post_title_textview, // O ID do campo a ser preenchido
                    listOfItens // A fonte dos dados
            );
            // Inflamos o layout principal
            View rootView = inflater.inflate(R.layout.lista_compras, container, false);

            // Cria uma referência para a ListView
            final ListView listView = (ListView) rootView.findViewById(R.id.list_itens);
            listView.setAdapter(listOfLastPostsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String item = listaItens.getListaItens().get(position).getNome();
                    Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                    //Vai para a lista de itens clicada
                   // Intent i = new Intent(view.getContext(), ListActivity.class);
                    //startActivity(i);

                }
            });

            // Retornamos tudo
            return rootView;
        }
    }
}
