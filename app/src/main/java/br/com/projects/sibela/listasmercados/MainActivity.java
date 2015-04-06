package br.com.projects.sibela.listasmercados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView lsvListas;
    private List<String> arrayListas = new ArrayList<String>();
    private ArrayAdapter<String> adapterListas;

    public String listaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mapeando sua
        lsvListas = (ListView)findViewById(R.id.lsvListas);

        // São adicionadas duas listas no nosso array
        arrayListas.add("BigBom");
        arrayListas.add("Lista 1");


        adapterListas = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, arrayListas);

        lsvListas.setBackgroundResource(R.drawable.roundedcorners);
        lsvListas.setAdapter(adapterListas);



        lsvListas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent lista = new Intent(getBaseContext(), ListaProdutosActivity.class);
                String listaSelecionada = lsvListas.getItemAtPosition(position).toString();
                lista.putExtra("LISTA", listaSelecionada);
                startActivity(lista);
            }
        });

        lsvListas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listaSelecionada = lsvListas.getItemAtPosition(position).toString();
                return false;
            }
        });



        registerForContextMenu(lsvListas);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listas_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuBaixar:
                Toast.makeText(getBaseContext(), "Baixando " + listaSelecionada + "...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuImprimir:
                Toast.makeText(getBaseContext(), "Imprimindo " + listaSelecionada + "...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuRemover:

                AlertDialog.Builder alertaRemoverLista = new AlertDialog.Builder(MainActivity.this);
                alertaRemoverLista.setTitle("Deletar!");
                alertaRemoverLista.setMessage("Deseja remover a lista \"" + listaSelecionada + "\"?");

                alertaRemoverLista.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Toast.makeText(getBaseContext(), listaSelecionada + " removido", Toast.LENGTH_SHORT).show();
                        adapterListas.remove(listaSelecionada);

                    }
                });

                alertaRemoverLista.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alertaRemoverLista.show();

                break;

            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.novaLista :

                AlertDialog.Builder alertaNovaLista = new AlertDialog.Builder(MainActivity.this);
                alertaNovaLista.setTitle("Nova lista");
                alertaNovaLista.setMessage("Insira o nome da lista");

                // Set an EditText view to get user input
                final EditText nomeLista = new EditText(getBaseContext());
                nomeLista.setTextColor(Color.parseColor("#000000"));
                alertaNovaLista.setView(nomeLista);

                nomeLista.requestFocus();
                // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // imm.showSoftInput(nomeLista, InputMethodManager.SHOW_IMPLICIT);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                alertaNovaLista.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String stringNomeLista = nomeLista.getText().toString();

                        Intent intent = new Intent(getBaseContext(), ListaProdutosActivity.class);
                        intent.putExtra("LISTA", stringNomeLista);
                        startActivity(intent);
                        // Do something with value!
                        // Go to next activity setting it's title with list name,
                    }
                });

                alertaNovaLista.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alertaNovaLista.show();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
