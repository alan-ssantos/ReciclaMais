package br.com.fiap.reciclamais.model;

public class GenericResponse<T> {

    private T results;
    private String status;
    private String descricao;

    public GenericResponse(T results, String status, String descricao) {
        this.results = results;
        this.status = status;
        this.descricao = descricao;
    }

    public GenericResponse() {
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
