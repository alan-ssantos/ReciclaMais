package br.com.fiap.reciclamais.model;

import java.util.List;

public class RegistroResult {

    private List<String> usuarios;
    private String descricao;

    public RegistroResult(List<String> usuarios, String descricao) {
        this.usuarios = usuarios;
        this.descricao = descricao;
    }

    public RegistroResult() {
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
