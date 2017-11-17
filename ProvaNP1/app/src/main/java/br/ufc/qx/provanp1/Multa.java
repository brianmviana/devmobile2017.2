package br.ufc.qx.provanp1;

import java.util.Date;


public class Multa {

    private Integer id;
    private Integer imagem;
    private String veiculo;
    private String placa;
    private Date data;

    public Multa(Integer id, Integer imagem, String veiculo, String placa, Date data) {
        this.id = id;
        this.imagem = imagem;
        this.veiculo = veiculo;
        this.placa = placa;
        this.data = data;
    }

    public Multa() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImagem() {
        return imagem;
    }

    public void setImagem(Integer imagem) {
        this.imagem = imagem;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
