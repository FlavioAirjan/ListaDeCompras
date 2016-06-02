package com.listadecompras.listadecompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Flavio on 02/06/2016.
 */
public class DbController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public DbController(Context context){
        banco = new CriaBanco(context);
    }

    //insere lista e retorna o id no objeto inserido
    public long insereLista(String nome){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.getNomeLista(), nome);

        resultado = db.insert(CriaBanco.getTabelaLista(), null, valores);

        db.close();

        if (resultado ==-1)
            return -1;
        else
            return resultado;

    }

    public void alteraLista(long id, String nome){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.getIdLista() + "=" + id;
        valores = new ContentValues();
        valores.put(CriaBanco.getNomeLista(), nome);

        db.update(CriaBanco.getTabelaLista(),valores,where,null);
        db.close();
    }

    public void alteraItem(long id,long fkey ,String nome,String tipo, float quant, int check){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.getIdItem() + "=" + id;
        valores = new ContentValues();
        valores.put(CriaBanco.getNomeItem(), nome);
        valores.put(CriaBanco.getTipoItem(), tipo);
        valores.put(CriaBanco.getCheckItem(), check);
        valores.put(CriaBanco.getQuantItem(), quant);
        valores.put(CriaBanco.getIdListaForeignkey(), fkey);

        db.update(CriaBanco.getTabelaItem(),valores,where,null);
        db.close();
    }


    public void deletaLista(long id){
        String where = CriaBanco.getIdLista() + " = " + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.getTabelaLista(),where,null);
        db.close();
    }

    public void deletaItem(long id){
        String where = CriaBanco.getIdItem() + " = " + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.getTabelaItem(),where,null);
        db.close();
    }

    public long insereItem(String nome,String tipo,int check,float quant,long fkey){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.getNomeItem(), nome);
        valores.put(CriaBanco.getTipoItem(), tipo);
        valores.put(CriaBanco.getCheckItem(), check);
        valores.put(CriaBanco.getQuantItem(), quant);
        valores.put(CriaBanco.getIdListaForeignkey(), fkey);

        resultado = db.insert(CriaBanco.getTabelaItem(), null, valores);
        db.close();

        if (resultado ==-1)
            return -1;
        else
            return resultado;

    }

    public Cursor carregaLista(){
        Cursor cursor;
        String[] campos =  {banco.getIdLista(),banco.getNomeLista()};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.getTabelaLista(), campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaItem(long key){
        Cursor cursor;
        String[] campos =  {banco.getIdItem(),banco.getIdListaForeignkey(),banco.getNomeItem(),banco.getTipoItem(),banco.getQuantItem(),banco.getCheckItem()};
        db = banco.getReadableDatabase();
        String[] args = { String.valueOf(key)};
        cursor = db.query(banco.getTabelaItem(), campos, banco.getIdListaForeignkey()+"=?", args, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public  String getNomeBanco() {
        return banco.getNomeBanco();
    }

    public  String getTabelaLista() {
        return banco.getTabelaLista();
    }

    public  String getIdLista() {
        return banco.getIdLista();
    }

    public  String getNomeLista() {
        return banco.getNomeLista();
    }

    public  String getTabelaItem() {
        return banco.getTabelaItem();
    }

    public  String getIdItem() {
        return banco.getIdItem();
    }

    public  String getIdListaForeignkey() {
        return banco.getIdListaForeignkey();
    }

    public  String getNomeItem() {
        return banco.getNomeItem();
    }

    public  String getTipoItem() {
        return banco.getTipoItem();
    }

    public  String getQuantItem() {
        return banco.getQuantItem();
    }

    public  String getCheckItem() {
        return banco.getCheckItem();
    }

    public int getVERSAO() {
        return banco.getVERSAO();
    }


}
