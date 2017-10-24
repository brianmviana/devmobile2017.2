package br.ufc.qx.adaptercervejas;

/**
 * Created by Anibal on 24/10/2017.
 */

public class Cerveja {

    private Long id;
    private String nome;
    private String marca;
    private Integer imagem;

    public Cerveja(Long id, String nome, String marca, Integer imagem) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.imagem = imagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getImagem() {
        return imagem;
    }

    public void setImagem(Integer imagem) { this.imagem = imagem; }
}
