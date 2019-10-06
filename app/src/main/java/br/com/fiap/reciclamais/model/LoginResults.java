package br.com.fiap.reciclamais.model;

import br.com.fiap.reciclamais.utils.enums.PerfilEnum;

public class LoginResults {

    private String nome;
    private PerfilEnum perfil;

    public LoginResults(String nome, PerfilEnum perfil) {
        this.nome = nome;
        this.perfil = perfil;
    }

    public LoginResults() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }
}
