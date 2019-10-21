package br.com.fiap.reciclamais.model.result;

public class UsuarioLoginResult {

    private String nome;
    private String cpf;
    private Integer pontuacaoTotal;
    private Integer reciclagemTotal;

    public UsuarioLoginResult() {
    }

    public UsuarioLoginResult(String nome, String cpf, Integer pontuacaoTotal, Integer reciclagemTotal) {
        this.nome = nome;
        this.cpf = cpf;
        this.pontuacaoTotal = pontuacaoTotal;
        this.reciclagemTotal = reciclagemTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(Integer pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public Integer getReciclagemTotal() {
        return reciclagemTotal;
    }

    public void setReciclagemTotal(Integer reciclagemTotal) {
        this.reciclagemTotal = reciclagemTotal;
    }
}
