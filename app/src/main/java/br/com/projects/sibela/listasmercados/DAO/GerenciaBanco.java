package br.com.projects.sibela.listasmercados.DAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 29/03/2015.
 */
public class GerenciaBanco extends SQLiteOpenHelper {

    //Nome do banco
    private static final String NOME_BANCO = "economarket.db";

    //Versão do banco (incrementado conforme o banco é alterado ao longo da vida
    private static final int VERSAO_BANCO = 1;

    //Construtor
    public GerenciaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tabelaListaItens =
                "CREATE TABLE listaItens " +
                        "(idLista INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT)";

        db.execSQL(tabelaListaItens);


        String tabelaProdutos =
                "CREATE TABLE produtos " +
                        "(codigoBarras INTEGER PRIMARY KEY, " +
                        "nome TEXT, " +
                        "decimal INTEGER DEFAULT 0)";

        db.execSQL(tabelaProdutos);


        String tabelaItens =
                "CREATE TABLE itens " +
                        "(FOREIGN KEY(idLista) REFERENCES listaItens(idLista), " +
                        "FOREIGN KEY (codigoBarras) REFERENCES produtos(codigoBarras), " +
                        "quantidade REAL)";

        db.execSQL(tabelaItens);

    }

    //Usado quando você precisar fazer alterações (patch/ALTER_TABLE) no banco, você irá usar esse método
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
