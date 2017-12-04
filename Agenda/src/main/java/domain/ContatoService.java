package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import persistence.JpaUtil;

public class ContatoService {
	private List<Contato> lista;
	protected EntityManager em;
	protected String mensagemCampoDuplicado;
	protected String mensagemErroExclusao;

	public ContatoService() {
		super();
		this.em = JpaUtil.getEntityManager();
		this.mensagemCampoDuplicado = "Um item igual ja foi cadastrado no banco.";
		this.mensagemErroExclusao = "Erro ao excluir o item do tipo " + Contato.class.getName() + ".";
	}

	public Contato salvar(Contato contato) {
		Contato c = this.buscarPorId(contato.getId());
		if (c != null) {
			this.atualizar(contato);
			return contato;
		}
		try {
			contato.setUriFoto(resolverNomeArquivo(contato.getUriFoto()));
			em.persist(contato);
			em.flush();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		return contato;
	}

	public void atualizar(Contato contato) {
		try {
			contato.setUriFoto(resolverNomeArquivo(contato.getUriFoto()));			
			contato = em.merge(contato);
			em.persist(contato);
			em.flush();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Contato> listar() {
		return em.createQuery("SELECT c FROM Contato c ").getResultList();
	}

	public Contato buscarPorId(Long i) {
		return em.find(Contato.class, i);
	}

	public void excluir(Contato contato) {
		try {
			contato = em.merge(contato);
			em.remove(contato);
			em.flush();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public void excluir(Long id) {
		try {
			Contato contato = em.getReference(Contato.class, id);
			contato = em.merge(contato);
			em.remove(contato);
			em.flush();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();

		}
	}

	public List<Contato> listarPorData() {
		lista = new ArrayList<Contato>();
		try {
			lista = (List<Contato>) listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public String resolverNomeArquivo(String nomeArquivo) {
        int beginIndex = nomeArquivo.lastIndexOf("/") + 1;
        String nome = nomeArquivo.substring(beginIndex);
        return nome;
    }
}
