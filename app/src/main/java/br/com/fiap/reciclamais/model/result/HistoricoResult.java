package br.com.fiap.reciclamais.model.result;

public class HistoricoResult {

    private String data;
    private int ponto;

    public HistoricoResult() {
    }

    public HistoricoResult(String data, int ponto) {
        this.data = data;
        this.ponto = ponto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPonto() {
        return ponto;
    }

    public void setPonto(int ponto) {
        this.ponto = ponto;
    }
}
