package br.com.projects.sibela.listasmercados.BEAN;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 29/03/2015.
 */
public class ListaCompra {

    private int idLista;
    private String nome;

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome();
    }
}

