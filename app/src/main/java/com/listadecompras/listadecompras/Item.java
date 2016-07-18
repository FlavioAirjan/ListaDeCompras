package com.listadecompras.listadecompras;

import java.io.Serializable;

/**
 * Created by Flavio on 17/05/2016.
 */

public class Item implements Serializable{
    public static float MAXQuant=10000;
    public static float MAXPreco=100000;
    private String nome;
    private float quantidade;
    private String tipo;
    private float preco;
    private float precoTotal;

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal() {

        if(quantidade*preco>MAXPreco*MAXQuant) {
            this.precoTotal = MAXPreco*MAXQuant;
        }else{
            this.precoTotal = quantidade*preco;
        }

    }

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
        this.quantidade = 1;
        this.tipo = "Un";
        this.preco = 0;
        this.id= id;
        this.check=false;
        this.setPrecoTotal();
    }

    public Item(String nome, float quantidade,long id) {
        this.nome = nome;
        if(quantidade<=0){
            this.quantidade = 1;
        }else{
            this.quantidade = quantidade;
        }
        this.tipo = "Un";
        this.preco = 0;
        this.id= id;
        this.check=false;
        this.setPrecoTotal();
    }

    public Item(String nome, float quantidade, float preco,long id) {
        this.nome = nome;
        if(quantidade<=0){
            this.quantidade = 1;
        }else{
            this.quantidade = quantidade;
        }
        this.preco =preco ;
        this.id= id;
        this.check=false;
        this.setPrecoTotal();
    }

    public Item(String nome, float quantidade, float preco,int check,long id) {
        this.nome = nome;
        if(quantidade<=0){
            this.quantidade = 1;
        }else{
            this.quantidade = quantidade;
        }

        this.preco = preco;
        this.id= id;
        if(check==1){
            this.check=true;
        }else{
            this.check=false;
        }
        this.setPrecoTotal();

    }

    public Item(String nome, float quantidade, String tipo, float preco,int check,long id) {
        this.nome = nome;
        if(quantidade<=0){
            this.quantidade = 1;
        }else{
            this.quantidade = quantidade;
        }
        this.tipo = tipo;
        this.preco = preco;
        this.id= id;
        if(check==1){
            this.check=true;
        }else{
            this.check=false;
        }
        this.setPrecoTotal();
    }

    //get_set quantidade
    public float getQuantidade() {
        return quantidade;

    }

    public void setQuantidade(float quantidade) {
        if(quantidade<=0){
            this.quantidade = 1;
        }else {
            if(quantidade>=MAXQuant) {
                this.quantidade=MAXQuant;
            }else {
                this.quantidade = quantidade;
            }
        }
        setPrecoTotal();
    }

    //get_set tipo
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

    //get_set preco
    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        if(preco<0){
            preco=preco*-1;
        }
        if(preco>MAXPreco){
            preco=MAXPreco;
        }
        this.preco = preco;
        setPrecoTotal();
    }

    //get_set nome
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
