package com.app.nextoque.enums;

public enum StatusRetiradaEnum {

    USADO_EM_OBRA("usado_em_obra", "Usado em obra"),
    DEVOLVIDO("devolvido", "Devolvido"),
    PENDENTE("pendente", "Devolver"),
    INCONSISTENTE("inconsistente", "Inconsistente");

    private String status;
    private String label;

    StatusRetiradaEnum(String status, String label) {
        this.status = status;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return status;
    }
}
