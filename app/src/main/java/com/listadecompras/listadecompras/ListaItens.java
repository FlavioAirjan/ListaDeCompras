package com.listadecompras.listadecompras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Flavio on 17/05/2016.
 */
public class ListaItens {

    private List<Item> listaItens;
    private String nome;
    private float total;


    public void sumTotal() {
        total=0;
        for (int i=0;i<this.listaItens.size();i++) {
            total += this.listaItens.get(i).getPreco();
        }
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<Item> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Item> listaItens) {
        this.listaItens = listaItens;
    }

    public ListaItens(String nome) {
        this.nome=nome;
        this.total=0;
            this.listaItens = new ArrayList<>();
    }

    public boolean AddItem(Item newItem){
        this.listaItens.add(newItem);
        if(this.listaItens.contains(newItem)) {
            return true;
        }else{
            return false;
        }
    }

    public boolean RemoveItem(Item deletItem){
        this.listaItens.remove(deletItem);
        if(this.listaItens.contains(deletItem)) {
            return false;
        }else{
            return true;
        }
    }

    public List<String> ListaNomesItens (  List<String>  retorno){
        //List<String> retorno=new ArrayList<String>();
        for(int i=0;i<this.listaItens.size();i++){
            retorno.add(this.listaItens.get(i).getNome());
        }
        /*if(!retorno.isEmpty()) {
            return true;
        }else{
            return false;
        }*/
        return retorno;
    }
}
