package agendadb.qx.ufc.br.agendadb;

import java.util.Date;

public class Contato {

    public Integer id;
    public String nome;
    public String email;
    public String celular;
    public String foto;
    public Date data_aniversario;

    public Contato() {
    }

    public Contato(Integer id, String nome, String email, String celular, String foto, Date data_aniversario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.foto = foto;
        this.data_aniversario = data_aniversario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getData_aniversario() {
        return data_aniversario;
    }

    public void setData_aniversario(Date data_aniversario) {
        this.data_aniversario = data_aniversario;
    }
}
