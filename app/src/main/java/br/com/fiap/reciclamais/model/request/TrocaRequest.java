package br.com.fiap.reciclamais.model.request;

public class TrocaRequest {

    private String cpf;
    private String voucher;

    public TrocaRequest(String cpf, String voucher) {
        this.cpf = cpf;
        this.voucher = voucher;
    }

    public TrocaRequest() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
