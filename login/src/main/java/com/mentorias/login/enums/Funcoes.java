package com.mentorias.login.enums;

public enum Funcoes {
    ADMIN("admin"), FUNCIONARIO("funcionario");

    private final String funcao;


    Funcoes(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }

}
