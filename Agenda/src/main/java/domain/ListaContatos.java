package domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "carros")
public class ListaContatos implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Contato> contatos;

	@XmlElement(name = "carro")
	public List<Contato> getContatos() {
		return contatos;
	}

	public void setCarros(List<Contato> contatos) {
		this.contatos = contatos;
	}

	@Override
	public String toString() {
		return "ListaContato [contatos=" + contatos + "]";
	}
}
