package br.com.projects.sibela.listasmercados.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.projects.sibela.listasmercados.BEAN.ListaCompra;

/**
 * Created by ASUS on 29/03/2015.
 */
public class ListaCompraDAO {

    private SQLiteDatabase banco;

    private GerenciaBanco gerenciaBanco;

    public static final String IDLISTA = "idLista";
    public static final String NOME = "nome";

    public static final String[] todasAsColunas = {
            IDLISTA, NOME
    };

    public static final String TABELA = "listas";

    public ListaCompraDAO(Context contexto) {
        gerenciaBanco = new GerenciaBanco(contexto);
    }

    public void open() throws SQLException { banco = gerenciaBanco.getWritableDatabase(); }

    public void close() { gerenciaBanco.close(); }

    public void insert(ListaCompra listaCompra) {

        ContentValues valores = new ContentValues();
        valores.put(IDLISTA, listaCompra.getIdLista());
        valores.put(NOME, listaCompra.getNome());

        banco.insert(TABELA, null, valores);

    }

    public void update(ListaCompra listaCompra) {
        ContentValues valores = new ContentValues();
        valores.put(IDLISTA, listaCompra.getIdLista());
        valores.put(NOME, listaCompra.getNome());

        banco.update(TABELA, valores, IDLISTA + " = " + listaCompra.getIdLista(), null);
    }

    public void delete(ListaCompra listaCompra) {

        banco.delete(TABELA, IDLISTA + " = " + listaCompra.getIdLista(), null);

    }

    private ListaCompra cursorToItem(Cursor cursor) {
        ListaCompra listaCompra = new ListaCompra();

        listaCompra.setIdLista(cursor.getInt(0));
        listaCompra.setNome(cursor.getString(1));

        return listaCompra;
    }

    public List<ListaCompra> selectTodos() {
        List<ListaCompra> arrayListaItens = new ArrayList<ListaCompra>();

        Cursor cursor = banco.query(TABELA,
                todasAsColunas, null, null, null, null, NOME);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            ListaCompra listaCompra = cursorToItem(cursor);

            arrayListaItens.add(listaCompra);

            cursor.moveToNext();
        }

        cursor.close();

        return arrayListaItens;
    }

    public ListaCompra selectUm(ListaCompra listaCompra) {

        Cursor cursor = banco.query(
                TABELA,
                todasAsColunas,
                IDLISTA + " = " +
                        listaCompra.getIdLista(), null, null, null, null);

        cursor.moveToFirst();

        return cursorToItem(cursor);
    }
}
