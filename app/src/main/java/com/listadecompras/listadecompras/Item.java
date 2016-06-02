package com.listadecompras.listadecompras;

import java.io.Serializable;

/**
 * Created by Flavio on 17/05/2016.
 */
public class Item implements Serializable{
    private String nome;
    private float quantidade;
    private String tipo;
    private float preco;
    private boolean check;
    private long id;



    public boolean isCheck() {
        return check;
    }
    public int checkInt(){
        if(check) {
            return 1;
        }else{
            return 0;
        }
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Item(String nome,long id) {
        this.nome = nome;
        this.quantidade = 0;
        this.tipo = "Un";
        this.preco = 0;
        this.id= id;
        this.check=false;
    }

    public Item(String nome, float quantidade,long id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipo = "Un";
        this.preco = 0;
        this.id= id;
        this.check=false;
    }

    public Item(String nome, float quantidade, String tipo,long id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.preco = 0;
        this.id= id;
        this.check=false;
    }

    public Item(String nome, float quantidade, String tipo,int check,long id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.preco = 0;
        this.id= id;
        if(check==1){
            this.check=true;
        }else{
            this.check=false;
        }

    }

    public Item(String nome, float quantidade, String tipo, float preco,int check,long id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.preco = preco;
        this.id= id;
        if(check==1){
            this.check=true;
        }else{
            this.check=false;
        }
    }

    //get_set quantidade
    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    //get_set quantidade
    public String getTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        if(tipo==false) {
            this.tipo = "Kg";
        }else{
            this.tipo = "Un";
        }
    }

    public void setTipo(String tipo) {

            this.tipo = tipo;

    }

    //get_set quantidade
    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    //get_set quantidade
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
