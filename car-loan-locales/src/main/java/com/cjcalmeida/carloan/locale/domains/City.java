package com.cjcalmeida.carloan.locale.domains;

/*
https://servicodados.ibge.gov.br/api/v1/localidades/estados/35/microrregioes
 */
public class City {

    private Integer id;
    private String name;

    @Deprecated
    public City() {
        //Only for framework
    }

    public City(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
