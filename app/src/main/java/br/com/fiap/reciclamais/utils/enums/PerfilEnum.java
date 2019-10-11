package br.com.fiap.reciclamais.utils.enums;

public enum PerfilEnum {

    CLIENTE(1),
    FUNCIONARIO(2),
    ADMINISTRADOR(3);

    private Integer codigo;

    PerfilEnum(Integer codigo) {
        this.codigo = codigo;
    }
}
