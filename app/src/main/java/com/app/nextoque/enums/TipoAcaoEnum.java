package com.app.nextoque.enums;

public enum TipoAcaoEnum {

    ALTERACAO("alteracao", "Alteração"),
    RETIRADA("retirada", "Retirada"),
    DEVOLUCAO("devolucao", "Devolução"),
    ALTERACAO_OBRA("alteracao_obra", "Alteração");

    private String tipo;
    private String label;

    TipoAcaoEnum(String tipo, String label) {
        this.tipo = tipo;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
