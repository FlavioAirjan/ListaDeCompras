package com.listadecompras.listadecompras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

/**
 * Created by Flavio on 17/05/2016.
 */
public class ListaItens implements Serializable {
    public static float MAXPreco=100000*10000*100;
    private List<Item> listaItens;
    private String nome;
    private long key;
    private float total;
    private int numItensChecked;



    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void sumTotal() {

        total=0;
        for (int i=0;i<this.listaItens.size();i++) {
            if(this.listaItens.get(i).isCheck()) {
                total += this.listaItens.get(i).getPrecoTotal();
            }
        }

        if(total>MAXPreco){
            total=MAXPreco;
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
        if(total<=0) {
            sumTotal();
        }else{
            if(total>MAXPreco) {
                this.total = MAXPreco;
            }else {
                this.total = total;
            }
        }
    }

    public List<Item> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Item> listaItens) {
        this.listaItens = listaItens;
    }

    public ListaItens(String nome,long key,int checked,float preco) {
        this.nome=nome;
        this.key=key;
        this.numItensChecked=checked;
        this.total=preco;
        this.listaItens = new ArrayList<>();

    }


    public boolean AddItem(Item newItem){
        if(newItem.isCheck()){
            this.listaItens.add(newItem);
        }else{
            this.listaItens.add(0,newItem);
        }


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

    public int getNumItensChecked() {
        return numItensChecked;
    }

    public void setNumItensChecked(int numItensChecked) {
        this.numItensChecked = numItensChecked;
    }
}
