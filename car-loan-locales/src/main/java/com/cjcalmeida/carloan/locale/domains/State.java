package com.cjcalmeida.carloan.locale.domains;

/**
 * https://servicodados.ibge.gov.br/api/v1/localidades/estados/35/microrregioes
 */
public class State {

    private Integer id;
    private String abbreviation;

    @Deprecated
    public State() {
        //Only for framework
    }

    public State(Integer id, String abbreviation) {
        this.id = id;
        this.abbreviation = abbreviation;
    }

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
