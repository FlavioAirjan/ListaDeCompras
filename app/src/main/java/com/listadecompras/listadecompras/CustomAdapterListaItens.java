package com.listadecompras.listadecompras;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
        TextView name;
        TextView quantidade;
        TextView tipo;
        Button menos;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.lista_itens, null);

        holder.name=(TextView) rowView.findViewById(R.id.nomeItem);
        holder.quantidade=(TextView) rowView.findViewById(R.id.quantItem);
        holder.tipo=(TextView) rowView.findViewById(R.id.tipoItem);
        holder.name.setText(listaItens.getListaItens().get(position).getNome());
        holder.quantidade.setText(String.valueOf(listaItens.getListaItens().get(position).getQuantidade()));
        holder.tipo.setText(listaItens.getListaItens().get(position).getTipo());

        holder.menos=(Button) rowView.findViewById(R.id.btn_menos);

        rowView.findViewById(R.id.btn_menos).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Iem: "+listaItens.getListaItens().get(position).getNome(), Toast.LENGTH_LONG).show();
                listaItens.getListaItens().remove(position);
                listActivity.removeItem(position);
            }
        });

        rowView.findViewById(R.id.nomeItem).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Iem: "+listaItens.getListaItens().get(position).getNome(), Toast.LENGTH_LONG).show();
            }
        });

        rowView.findViewById(R.id.quantItem).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Quantidade: "+listaItens.getListaItens().get(position).getQuantidade(), Toast.LENGTH_LONG).show();
            }
        });

        rowView.findViewById(R.id.tipoItem).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Tipo: "+listaItens.getListaItens().get(position).getTipo(), Toast.LENGTH_LONG).show();
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
