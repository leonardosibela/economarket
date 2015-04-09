package br.com.projects.sibela.listasmercados.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import br.com.projects.sibela.listasmercados.BEAN.Item;
import br.com.projects.sibela.listasmercados.BEAN.ListaCompra;


/**
 * Created by ASUS on 29/03/2015.
 */
public class ItemDAO {

    private SQLiteDatabase banco;

    private GerenciaBanco gerenciaBanco;

    public static final String CODIGOBARRAS = "codigoBarras";
    public static final String IDLISTA = "idLista";
    public static final String QUANTIDADE = "qantidade";
    public ProdutoDAO produtoDAO;

    public static final String[] todasAsColunas = {
            IDLISTA, CODIGOBARRAS, QUANTIDADE
    };

    public static final String TABELA_ITENS = "itens";

    private  static final String TAG = "CADASTRO_ITEM";

    public ItemDAO(Context contexto) {
        gerenciaBanco = new GerenciaBanco(contexto);
        produtoDAO = new ProdutoDAO(contexto);
    }

    public void open() throws android.database.SQLException {
        banco = gerenciaBanco.getWritableDatabase();
    }

    public void close() {
        gerenciaBanco.close();
    }

    public void insert(Item item) {

        //Objeto para armazenar os valores
        ContentValues valores = new ContentValues();

        //Povoando o MAPA DE VALORES com [chave:valor]
        valores.put("codigoBarras", item.getProduto().getCodigoBarras());
        valores.put("idLista", item.getIdLista());
        valores.put("quantidade", item.getQuantidade());

        //Armazena os dados do ITEM no banco
        banco.insert(TABELA_ITENS, null, valores);
        Log.i(TAG, "Item cadastrado: " + item.toString());

    }

    public void update(Item item) {
        ContentValues valores = new ContentValues();

        valores.put("codigoBarras", item.getProduto().getCodigoBarras());
        valores.put("idLista", item.getIdLista());
        valores.put("quantidade", item.getQuantidade());

        banco.update(TABELA_ITENS, valores, IDLISTA + " = " + item.getIdLista() + " AND " + CODIGOBARRAS + " = " + item.getProduto().getCodigoBarras(), null);

        Log.i(TAG, "Item alterado: " + item.toString());
    }

    public void delete(Item item) {
        banco.delete(TABELA_ITENS, IDLISTA + " = " + item.getIdLista() + " AND " + CODIGOBARRAS + " = " + item.getProduto().getCodigoBarras(), null);

        Log.i(TAG, "Item removido: " + item.toString());
    }


    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();

        item.setIdLista(cursor.getInt(1));
        item.getProduto().setCodigoBarras(cursor.getInt(2));
        item.setQuantidade(cursor.getLong(3));

        return item;
    }


    public Item selectUm(Item item) {

        Cursor cursor = banco.query(
                TABELA_ITENS,
                todasAsColunas,
                IDLISTA + " = " + item.getIdLista() + " AND " +
                        CODIGOBARRAS + " = " + item.getProduto().getCodigoBarras(), null, null, null, null);

        cursor.moveToFirst();

        return cursorToItem(cursor);
    }


    public List<Item> selectItensFromLista(ListaCompra listaCompra) {

        List<Item> itens = new ArrayList<Item>();

        Cursor cursor = banco.query(
                TABELA_ITENS,
                todasAsColunas,
                IDLISTA + " = " + listaCompra.getIdLista(), null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            Item item = cursorToItem(cursor);

            item.setProduto(produtoDAO.selectUm(item.getProduto().getCodigoBarras()));

            itens.add(item);

            cursor.moveToNext();
        }

        cursor.close();

        return itens;
    }

}