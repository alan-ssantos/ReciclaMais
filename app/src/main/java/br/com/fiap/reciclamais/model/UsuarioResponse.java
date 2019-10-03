package br.com.fiap.reciclamais.model;

import br.com.fiap.reciclamais.utils.enums.PerfilEnum;

public class UsuarioResponse {

    private String nome;
    private PerfilEnum perfil;

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
