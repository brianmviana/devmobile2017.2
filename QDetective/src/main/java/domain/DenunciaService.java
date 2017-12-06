package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import persistence.JpaUtil;

public class DenunciaService {
	private List<Denuncia> lista;
	protected EntityManager em;
	protected String mensagemCampoDuplicado;
	protected String mensagemErroExclusao;

	public DenunciaService() {
		super();
		this.em = JpaUtil.getEntityManager();
		this.mensagemCampoDuplicado = "Um item igual ja foi cadastrado no banco.";
		this.mensagemErroExclusao = "Erro ao excluir o item do tipo " + Denuncia.class.getName() + ".";
	}

	public Denuncia salvar(Denuncia denuncia) {
		Denuncia d = this.buscarPorId(denuncia.getId());
		if (d != null) {
			this.atualizar(denuncia);
			return denuncia;
		}
		try {
			denuncia.setUriMidia(resolverNomeArquivo(denuncia.getUriMidia()));
			em.persist(denuncia);
			em.flush();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		return denuncia;
	}

	public void atualizar(Denuncia denuncia) {
		try {
			denuncia.setUriMidia(resolverNomeArquivo(denuncia.getUriMidia()));
			denuncia = em.merge(denuncia);
			em.persist(denuncia);
			em.flush();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Denuncia> listar() {
		return em.createQuery("SELECT d FROM Denuncia d ").getResultList();
	}

	public Denuncia buscarPorId(Integer i) {
		return em.find(Denuncia.class, i);
	}

	public void excluir(Denuncia denuncia) {
		try {
			denuncia = em.merge(denuncia);
			em.remove(denuncia);
			em.flush();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public void excluir(int id) {
		try {
			Denuncia denuncia = em.getReference(Denuncia.class, id);
			denuncia = em.merge(denuncia);
			em.remove(denuncia);
			em.flush();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();

		}
	}

	public List<Denuncia> listarPorData() {
		lista = new ArrayList<Denuncia>();
		try {
			lista = (List<Denuncia>) listar();
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
