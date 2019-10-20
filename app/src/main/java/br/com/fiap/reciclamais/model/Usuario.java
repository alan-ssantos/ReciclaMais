package br.com.fiap.reciclamais.model;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String cep;
    private String rua;
    private Integer numero;
    private String estado;
    private String cidade;

    //Construtor para Cadastrar
    public Usuario(String nome, String email, String senha, String cpf, String cep, String rua, Integer numero, String estado, String cidade) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
    }

    //Construtor para Alterar
    public Usuario(String nome, String email, String senha, String cep, String rua, Integer numero, String estado, String cidade) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
