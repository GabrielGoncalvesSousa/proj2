/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import proj2.DAL.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.DAL.Entidade;

/**
 *
 * @author nunop
 */
public class EntidadeJpaController implements Serializable {

    public EntidadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entidade entidade) {
        if (entidade.getCategoriaList() == null) {
            entidade.setCategoriaList(new ArrayList<Categoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Categoria> attachedCategoriaList = new ArrayList<Categoria>();
            for (Categoria categoriaListCategoriaToAttach : entidade.getCategoriaList()) {
                categoriaListCategoriaToAttach = em.getReference(categoriaListCategoriaToAttach.getClass(), categoriaListCategoriaToAttach.getIdCategoria());
                attachedCategoriaList.add(categoriaListCategoriaToAttach);
            }
            entidade.setCategoriaList(attachedCategoriaList);
            em.persist(entidade);
            for (Categoria categoriaListCategoria : entidade.getCategoriaList()) {
                Entidade oldIdEntidadeOfCategoriaListCategoria = categoriaListCategoria.getIdEntidade();
                categoriaListCategoria.setIdEntidade(entidade);
                categoriaListCategoria = em.merge(categoriaListCategoria);
                if (oldIdEntidadeOfCategoriaListCategoria != null) {
                    oldIdEntidadeOfCategoriaListCategoria.getCategoriaList().remove(categoriaListCategoria);
                    oldIdEntidadeOfCategoriaListCategoria = em.merge(oldIdEntidadeOfCategoriaListCategoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entidade entidade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidade persistentEntidade = em.find(Entidade.class, entidade.getIdEntidade());
            List<Categoria> categoriaListOld = persistentEntidade.getCategoriaList();
            List<Categoria> categoriaListNew = entidade.getCategoriaList();
            List<Categoria> attachedCategoriaListNew = new ArrayList<Categoria>();
            for (Categoria categoriaListNewCategoriaToAttach : categoriaListNew) {
                categoriaListNewCategoriaToAttach = em.getReference(categoriaListNewCategoriaToAttach.getClass(), categoriaListNewCategoriaToAttach.getIdCategoria());
                attachedCategoriaListNew.add(categoriaListNewCategoriaToAttach);
            }
            categoriaListNew = attachedCategoriaListNew;
            entidade.setCategoriaList(categoriaListNew);
            entidade = em.merge(entidade);
            for (Categoria categoriaListOldCategoria : categoriaListOld) {
                if (!categoriaListNew.contains(categoriaListOldCategoria)) {
                    categoriaListOldCategoria.setIdEntidade(null);
                    categoriaListOldCategoria = em.merge(categoriaListOldCategoria);
                }
            }
            for (Categoria categoriaListNewCategoria : categoriaListNew) {
                if (!categoriaListOld.contains(categoriaListNewCategoria)) {
                    Entidade oldIdEntidadeOfCategoriaListNewCategoria = categoriaListNewCategoria.getIdEntidade();
                    categoriaListNewCategoria.setIdEntidade(entidade);
                    categoriaListNewCategoria = em.merge(categoriaListNewCategoria);
                    if (oldIdEntidadeOfCategoriaListNewCategoria != null && !oldIdEntidadeOfCategoriaListNewCategoria.equals(entidade)) {
                        oldIdEntidadeOfCategoriaListNewCategoria.getCategoriaList().remove(categoriaListNewCategoria);
                        oldIdEntidadeOfCategoriaListNewCategoria = em.merge(oldIdEntidadeOfCategoriaListNewCategoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = entidade.getIdEntidade();
                if (findEntidade(id) == null) {
                    throw new NonexistentEntityException("The entidade with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidade entidade;
            try {
                entidade = em.getReference(Entidade.class, id);
                entidade.getIdEntidade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidade with id " + id + " no longer exists.", enfe);
            }
            List<Categoria> categoriaList = entidade.getCategoriaList();
            for (Categoria categoriaListCategoria : categoriaList) {
                categoriaListCategoria.setIdEntidade(null);
                categoriaListCategoria = em.merge(categoriaListCategoria);
            }
            em.remove(entidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entidade> findEntidadeEntities() {
        return findEntidadeEntities(true, -1, -1);
    }

    public List<Entidade> findEntidadeEntities(int maxResults, int firstResult) {
        return findEntidadeEntities(false, maxResults, firstResult);
    }

    private List<Entidade> findEntidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entidade.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Entidade findEntidade(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entidade> rt = cq.from(Entidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
