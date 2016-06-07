package com.listadecompras.listadecompras;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Flavio on 02/06/2016.
 */
public class CriaBanco extends SQLiteOpenHelper {
    private static final int VERSAO= 2;
    private static final String NOME_BANCO = "listaComprasBanco"+String.valueOf(VERSAO)+".db";

    private static final String TABELA_LISTA = "lista_itens";
    private static final String ID_LISTA = "lista_id";
    private static final String NOME_LISTA = "nome";
    private static final String CHECKED_ITENS = "checked_itens";



    private static final String TABELA_ITEM = "itens";
    private static final String ID_ITEM= "lista_id";
    private static final String ID_LISTA_FOREIGNKEY = "lista_id_fkey";
    private static final String NOME_ITEM = "nome";
    private static final String TIPO_ITEM = "tipo";
    private static final String QUANT_ITEM = "quantidade";
    private static final String CHECK_ITEM = "check_item";



    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlLista = "CREATE TABLE IF NOT EXISTS "+TABELA_LISTA+" ("
                + ID_LISTA + " integer primary key autoincrement, "
                + NOME_LISTA + " text, "
                + CHECKED_ITENS+ " integer"
                +")";
        db.execSQL(sqlLista);

        String sqlItem = "CREATE TABLE IF NOT EXISTS "+TABELA_ITEM+" ("
                + ID_ITEM + " integer primary key autoincrement, "
                + ID_LISTA_FOREIGNKEY + " integer, "
                + NOME_ITEM + " text, "
                + TIPO_ITEM + " text, "
                + QUANT_ITEM + " real, "
                + CHECK_ITEM + " integer, "
                + " FOREIGN KEY ("+ID_LISTA_FOREIGNKEY+") REFERENCES "+TABELA_LISTA+"("+ID_LISTA+")"
                +")";
        db.execSQL(sqlItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_LISTA);
        onCreate(db);
    }

    public static String getCheckedItens() {
        return CHECKED_ITENS;
    }

    public static String getNomeBanco() {
        return NOME_BANCO;
    }

    public static String getTabelaLista() {
        return TABELA_LISTA;
    }

    public static String getIdLista() {
        return ID_LISTA;
    }

    public static String getNomeLista() {
        return NOME_LISTA;
    }

    public static String getTabelaItem() {
        return TABELA_ITEM;
    }

    public static String getIdItem() {
        return ID_ITEM;
    }

    public static String getIdListaForeignkey() {
        return ID_LISTA_FOREIGNKEY;
    }

    public static String getNomeItem() {
        return NOME_ITEM;
    }

    public static String getTipoItem() {
        return TIPO_ITEM;
    }

    public static String getQuantItem() {
        return QUANT_ITEM;
    }

    public static String getCheckItem() {
        return CHECK_ITEM;
    }

    public static int getVERSAO() {
        return VERSAO;
    }
}
