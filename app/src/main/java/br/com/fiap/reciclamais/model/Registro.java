package br.com.fiap.reciclamais.model;

public class Registro {

    private String uuid;
    private String data;

    public Registro(String uuid, String data) {
        this.uuid = uuid;
        this.data = data;
    }

    public Registro() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
