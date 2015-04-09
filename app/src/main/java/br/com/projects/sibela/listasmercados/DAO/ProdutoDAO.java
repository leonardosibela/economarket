package br.com.projects.sibela.listasmercados.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import br.com.projects.sibela.listasmercados.BEAN.Produto;

/**
 * Created by ASUS on 29/03/2015.
 */
public class ProdutoDAO {

    private SQLiteDatabase banco;

    private GerenciaBanco gerenciaBanco;

    public static final String CODIGOBARRAS = "codigoBarras";
    public static final String NOME = "nome";
    public static final String DECIMAL = "decimal";

    public static final String[] todasAsColunas = {
            CODIGOBARRAS, NOME, DECIMAL
    };

    public static final String TABELA_PRODUTO = "produto";

    private  static final String TAG = "CADASTRO_PRODUTO";

    public ProdutoDAO(Context contexto) {
        gerenciaBanco = new GerenciaBanco(contexto);
    }

    public void open() throws android.database.SQLException {
        banco = gerenciaBanco.getWritableDatabase();
    }

    public void close() {
        gerenciaBanco.close();
    }

    public void insert(Produto produto) {

        //Objeto para armazenar os valores
        ContentValues valores = new ContentValues();

        //Povoando o MAPA DE VALORES com [chave:valor]
        valores.put("codigoBarras", produto.getCodigoBarras());
        valores.put("nome", produto.getNome());
        valores.put("decimal", produto.isDecimal());

        //Armazena os dados do ITEM no banco
        banco.insert(TABELA_PRODUTO, null, valores);
        Log.i(TAG, "Item cadastrado: " + produto.toString());
    }

    public void update(Produto produto) {
        ContentValues valores = new ContentValues();

        valores.put("codigoBarras", produto.getCodigoBarras());
        valores.put("nome", produto.getNome());
        valores.put("decimal", produto.isDecimal());

        banco.update(TABELA_PRODUTO, valores, CODIGOBARRAS + " = " + produto.getCodigoBarras(), null);

        Log.i(TAG, "Item alterado: " + produto.toString());
    }

    private Produto cursorToProduto(Cursor cursor) {
        Produto produto = new Produto();

        produto.setCodigoBarras(cursor.getInt(0));
        produto.setNome(cursor.getString(1));
        if(cursor.getInt(2)==0)
            produto.setDecimal(false);
        else
            produto.setDecimal(true);

        return produto;
    }

    public List<Produto> selectTodos() {
        List<Produto> produtos = new ArrayList<Produto>();

        Cursor cursor = banco.query(TABELA_PRODUTO, todasAsColunas, null, null, null, null, NOME);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            Produto produto = cursorToProduto(cursor);

            produtos.add(produto);

            cursor.moveToNext();
        }

        cursor.close();

        return produtos;
    }

    public Produto selectUm(int codigoBarras) {

        Cursor cursor = banco.query(
                TABELA_PRODUTO,
                todasAsColunas,
                CODIGOBARRAS + " = " +
                        codigoBarras, null, null, null, null);

        cursor.moveToFirst();

        return cursorToProduto(cursor);
    }

}
