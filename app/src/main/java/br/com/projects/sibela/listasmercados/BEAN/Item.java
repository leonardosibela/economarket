package br.com.projects.sibela.listasmercados.BEAN;

/**
 * Created by ASUS on 29/03/2015.
 */
public class Item {

    //FIXME: Não terá como atributo o código de barras, pois o mesmo estará no atributo do tipo Produto
    private int idLista;
    private long quantidade;
    private Produto produto;

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return produto.getNome();
    }
}