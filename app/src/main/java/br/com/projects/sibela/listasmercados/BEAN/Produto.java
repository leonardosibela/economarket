package br.com.projects.sibela.listasmercados.BEAN;

/**
 * Created by ASUS on 29/03/2015.
 */
public class Produto {

    private int codigoBarras;
    private String nome;
    private boolean decimal;

    public int getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(int codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDecimal() {
        return decimal;
    }

    public void setDecimal(boolean decimal) {
        this.decimal = decimal;
    }

    @Override
    public String toString() {
        return getNome();
    }
}