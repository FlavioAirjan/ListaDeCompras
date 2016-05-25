package com.listadecompras.listadecompras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavio on 17/05/2016.
 */
public class PastaListas implements Serializable {

    private List<ListaItens> listaListas;




    public PastaListas() {
        this.listaListas = new ArrayList<>();
    }

    public List<ListaItens> getListaListas() {
        return listaListas;
    }

    public void setListaListas(List<ListaItens> listaListas) {
        this.listaListas = listaListas;
    }

    public void addLista(String nome){
        this.listaListas.add(new ListaItens(nome));
    }

    public void removeLista(ListaItens lista){
        this.listaListas.remove(lista);
    }

    public void updateListaIten(ListaItens item,int position){
        this.listaListas.remove(position);
        this.listaListas.add(position,item);
    }

    public void addListaIten(ListaItens item){
        this.listaListas.add(item);
    }

    public ListaItens getListItens(String nome){
        int index=getIndexByName(nome);
        if(index>=0) {
            return this.listaListas.get(index);
        }else{
            return null;
        }
    }

    private int getIndexByName(String nome){
        boolean find=false;
        int index=-1;
        for(int i=0;(i<this.listaListas.size())&&find==false;i++){
            if(this.listaListas.get(i).getNome().equals(nome)) {
                index = i;
                find = true;
            }
        }
        if(index>=0){
            return index;
        }else{
            return -1;
        }

    }

}
