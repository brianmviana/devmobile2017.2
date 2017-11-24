package br.ufc.qx.agendaws;

import java.io.Serializable;
import java.util.Date;

public class Contato implements Serializable {

    public Integer id;
    public String nome;
    public String email;
    public String celular;
    public String uriFoto;
    public Date data_aniversario;

    public Contato() {
    }

    public Contato(Integer id, String nome, String email, String celular, String uriFoto, Date data_aniversario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.uriFoto = uriFoto;
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

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    public Date getData_aniversario() {
        return data_aniversario;
    }

    public void setData_aniversario(Date data_aniversario) {
        this.data_aniversario = data_aniversario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contato)) return false;

        Contato contato = (Contato) o;

        if (!getId().equals(contato.getId())) return false;
        if (getNome() != null ? !getNome().equals(contato.getNome()) : contato.getNome() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(contato.getEmail()) : contato.getEmail() != null)
            return false;
        if (getCelular() != null ? !getCelular().equals(contato.getCelular()) : contato.getCelular() != null)
            return false;
        if (getUriFoto() != null ? !getUriFoto().equals(contato.getUriFoto()) : contato.getUriFoto() != null)
            return false;
        return getData_aniversario() != null ? getData_aniversario().equals(contato.getData_aniversario()) : contato.getData_aniversario() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getNome() != null ? getNome().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getCelular() != null ? getCelular().hashCode() : 0);
        result = 31 * result + (getUriFoto() != null ? getUriFoto().hashCode() : 0);
        result = 31 * result + (getData_aniversario() != null ? getData_aniversario().hashCode() : 0);
        return result;
    }
}
