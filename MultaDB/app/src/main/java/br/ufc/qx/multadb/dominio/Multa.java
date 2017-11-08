package br.ufc.qx.multadb.dominio;

import java.util.Date;

public class Multa {

    private Long id;
    private String tipoVeiculo;
    private String tipoMulta;
    private String placa;
    private String imagemVeiculo;
    private Date dataMulta;

    public Multa() {
    }

    public Multa(Long id, String tipoVeiculo, String tipoMulta, String placa, String imagemVeiculo, Date dataMulta) {
        this.id = id;
        this.tipoVeiculo = tipoVeiculo;
        this.tipoMulta = tipoMulta;
        this.placa = placa;
        this.imagemVeiculo = imagemVeiculo;
        this.dataMulta = dataMulta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getTipoMulta() {
        return tipoMulta;
    }

    public void setTipoMulta(String tipoMulta) {
        this.tipoMulta = tipoMulta;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getImagemVeiculo() {
        return imagemVeiculo;
    }

    public void setImagemVeiculo(String imagemVeiculo) {
        this.imagemVeiculo = imagemVeiculo;
    }

    public Date getDataMulta() {
        return dataMulta;
    }

    public void setDataMulta(Date dataMulta) {
        this.dataMulta = dataMulta;
    }
}
