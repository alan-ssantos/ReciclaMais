package br.com.fiap.reciclamais.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

import br.com.fiap.reciclamais.utils.enums.StatusResponseEnum;

public class CadastroResponse implements Serializable {

    private String results;
    private StatusResponseEnum status;
    private String descricao;

    public CadastroResponse(String results, StatusResponseEnum status, String descricao) {
        this.results = results;
        this.status = status;
        this.descricao = descricao;
    }

    public CadastroResponse() {
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public StatusResponseEnum getStatus() {
        return status;
    }

    public void setStatus(StatusResponseEnum status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    @Override
    public String toString() {
        return getResults() + " - " +
                getStatus() + " - " +
                getDescricao();
    }
}
