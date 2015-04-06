package br.com.projects.sibela.listasmercados;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ListaProdutosActivity extends ActionBarActivity {

    private ListView lsvProdutos;
    private Button btnInserir;
    private EditText txtItem;
    private EditText txtQuantidade;

    private List<String> arrayProdutos = new ArrayList<String>();
    private ArrayAdapter<String> adapterProdutos;

    private String produtoSelecionado;

    public boolean listWasChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        // txtResultado = (TextView) findViewById(R.id.txtResultado);
        String lista = parametros.getString("LISTA");

        setTitle("Lista: " + lista);

        lsvProdutos = (ListView)findViewById(R.id.lsvProdutos);
        btnInserir = (Button)findViewById(R.id.btnInserir);
        txtItem = (EditText)findViewById(R.id.txtItem);
        txtQuantidade = (EditText)findViewById(R.id.txtQuantidade);

        if(lista!=""){
            switch (lista) {
                case "BigBom" :
                    arrayProdutos.add("Arroz - 4");
                    arrayProdutos.add("Feijão - 3");
                    arrayProdutos.add("Mamão - 2");
                    break;

                case  "Lista 1" :
                    arrayProdutos.add("Macarrão - 2");
                    arrayProdutos.add("Açucar - 3");
                    arrayProdutos.add("Feijoada - 1");

                default:
                    break;
            }
        }

        adapterProdutos = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, arrayProdutos);

        lsvProdutos.setBackgroundResource(R.drawable.roundedcorners);
        lsvProdutos.setAdapter(adapterProdutos);

        lsvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produtoSelecionado = lsvProdutos.getItemAtPosition(position).toString();
                Toast.makeText(getBaseContext(), produtoSelecionado, Toast.LENGTH_LONG).show();
                Log.e("PRODUTO_CLICADO", produtoSelecionado);
            }
        });

        lsvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produtoSelecionado = lsvProdutos.getItemAtPosition(position).toString();
                return false;
            }
        });

        txtQuantidade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        txtQuantidade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE) {

                    if(txtItem.getText().toString()!="") {
                        arrayProdutos.add(txtItem.getText().toString() + " - " + txtQuantidade.getText().toString());
                        listWasChanged = true;
                        txtItem.setText("");
                        txtQuantidade.setText("");
                        txtItem.requestFocus();
                        return(true);
                    }

                }

                return false;
            }
        });

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtItem.getText().toString()!="") {
                    arrayProdutos.add(txtItem.getText().toString() + " - " + txtQuantidade.getText().toString());
                    listWasChanged  = true;
                    txtItem.setText("");
                    txtQuantidade.setText("");
                    txtItem.setHint("Novo produto");
                }
            }
        });

        registerForContextMenu(lsvProdutos);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_produtos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.acbSalvar:
                Toast.makeText(getBaseContext(), "Salvar", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.produtos_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuEditar:
                Toast.makeText(getBaseContext(), "Editando " + produtoSelecionado + "...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuRemover:

                AlertDialog.Builder alertaRemoverProduto = new AlertDialog.Builder(ListaProdutosActivity.this);
                alertaRemoverProduto.setTitle("Remover!");
                alertaRemoverProduto.setMessage("Deseja remover o produto \n" + produtoSelecionado + "?");

                alertaRemoverProduto.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Toast.makeText(getBaseContext(), produtoSelecionado + " removido", Toast.LENGTH_SHORT).show();
                        adapterProdutos.remove(produtoSelecionado);
                        listWasChanged = true;
                    }
                });

                alertaRemoverProduto.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alertaRemoverProduto.show();

                break;

            default:
                break;
        }

        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && listWasChanged) {
            //Ask the user if they want to quit
            AlertDialog.Builder alertaRemoverLista = new AlertDialog.Builder(ListaProdutosActivity.this);
            alertaRemoverLista.setTitle("Atenção!");
            alertaRemoverLista.setMessage("Deseja cancelar as alterações realizadas na lista?");

            alertaRemoverLista.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    ListaProdutosActivity.this.finish();
                    Toast.makeText(getBaseContext(), "Alterações desfeitas", Toast.LENGTH_SHORT).show();

                }
            });

            alertaRemoverLista.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alertaRemoverLista.show();

            /**
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.quit)
                    .setMessage(R.string.really_quit)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            YourClass.this.finish();
                        }

                    })
                    .setNegativeButton(R.string.no, null)
                    .show();

             **/
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putStringArrayList("PRODUTOS_KEY", (ArrayList<String>) arrayProdutos);

        outState.putBoolean("LIST_WAS_CHANGED", listWasChanged);

        //Persistência do objeto Bundle
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        arrayProdutos = savedInstanceState.getStringArrayList("PRODUTOS_KEY");

        listWasChanged = savedInstanceState.getBoolean("LIST_WAS_CHANGED");

    }
}
