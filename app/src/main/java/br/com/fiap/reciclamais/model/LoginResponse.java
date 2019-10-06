package br.com.fiap.reciclamais.model;

import java.util.List;

public class LoginResponse {

    private LoginResults results;
    private String status;
    private String descricao;

    public LoginResponse(LoginResults results, String status, String descricao) {
        this.results = results;
        this.status = status;
        this.descricao = descricao;
    }

    public LoginResponse() {
    }

    public LoginResults getResults() {
        return results;
    }

    public void setResults(LoginResults results) {
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
