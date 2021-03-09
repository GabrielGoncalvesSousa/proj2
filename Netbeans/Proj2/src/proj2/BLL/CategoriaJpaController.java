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
import proj2.DAL.Entidade;
import proj2.DAL.Subcategoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proj2.BLL.exceptions.IllegalOrphanException;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.DAL.Categoria;

/**
 *
 * @author nunop
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getSubcategoriaList() == null) {
            categoria.setSubcategoriaList(new ArrayList<Subcategoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidade idEntidade = categoria.getIdEntidade();
            if (idEntidade != null) {
                idEntidade = em.getReference(idEntidade.getClass(), idEntidade.getIdEntidade());
                categoria.setIdEntidade(idEntidade);
            }
            List<Subcategoria> attachedSubcategoriaList = new ArrayList<Subcategoria>();
            for (Subcategoria subcategoriaListSubcategoriaToAttach : categoria.getSubcategoriaList()) {
                subcategoriaListSubcategoriaToAttach = em.getReference(subcategoriaListSubcategoriaToAttach.getClass(), subcategoriaListSubcategoriaToAttach.getIdSubcategoria());
                attachedSubcategoriaList.add(subcategoriaListSubcategoriaToAttach);
            }
            categoria.setSubcategoriaList(attachedSubcategoriaList);
            em.persist(categoria);
            if (idEntidade != null) {
                idEntidade.getCategoriaList().add(categoria);
                idEntidade = em.merge(idEntidade);
            }
            for (Subcategoria subcategoriaListSubcategoria : categoria.getSubcategoriaList()) {
                Categoria oldIdCategoriaOfSubcategoriaListSubcategoria = subcategoriaListSubcategoria.getIdCategoria();
                subcategoriaListSubcategoria.setIdCategoria(categoria);
                subcategoriaListSubcategoria = em.merge(subcategoriaListSubcategoria);
                if (oldIdCategoriaOfSubcategoriaListSubcategoria != null) {
                    oldIdCategoriaOfSubcategoriaListSubcategoria.getSubcategoriaList().remove(subcategoriaListSubcategoria);
                    oldIdCategoriaOfSubcategoriaListSubcategoria = em.merge(oldIdCategoriaOfSubcategoriaListSubcategoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdCategoria());
            Entidade idEntidadeOld = persistentCategoria.getIdEntidade();
            Entidade idEntidadeNew = categoria.getIdEntidade();
            List<Subcategoria> subcategoriaListOld = persistentCategoria.getSubcategoriaList();
            List<Subcategoria> subcategoriaListNew = categoria.getSubcategoriaList();
            List<String> illegalOrphanMessages = null;
            for (Subcategoria subcategoriaListOldSubcategoria : subcategoriaListOld) {
                if (!subcategoriaListNew.contains(subcategoriaListOldSubcategoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subcategoria " + subcategoriaListOldSubcategoria + " since its idCategoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEntidadeNew != null) {
                idEntidadeNew = em.getReference(idEntidadeNew.getClass(), idEntidadeNew.getIdEntidade());
                categoria.setIdEntidade(idEntidadeNew);
            }
            List<Subcategoria> attachedSubcategoriaListNew = new ArrayList<Subcategoria>();
            for (Subcategoria subcategoriaListNewSubcategoriaToAttach : subcategoriaListNew) {
                subcategoriaListNewSubcategoriaToAttach = em.getReference(subcategoriaListNewSubcategoriaToAttach.getClass(), subcategoriaListNewSubcategoriaToAttach.getIdSubcategoria());
                attachedSubcategoriaListNew.add(subcategoriaListNewSubcategoriaToAttach);
            }
            subcategoriaListNew = attachedSubcategoriaListNew;
            categoria.setSubcategoriaList(subcategoriaListNew);
            categoria = em.merge(categoria);
            if (idEntidadeOld != null && !idEntidadeOld.equals(idEntidadeNew)) {
                idEntidadeOld.getCategoriaList().remove(categoria);
                idEntidadeOld = em.merge(idEntidadeOld);
            }
            if (idEntidadeNew != null && !idEntidadeNew.equals(idEntidadeOld)) {
                idEntidadeNew.getCategoriaList().add(categoria);
                idEntidadeNew = em.merge(idEntidadeNew);
            }
            for (Subcategoria subcategoriaListNewSubcategoria : subcategoriaListNew) {
                if (!subcategoriaListOld.contains(subcategoriaListNewSubcategoria)) {
                    Categoria oldIdCategoriaOfSubcategoriaListNewSubcategoria = subcategoriaListNewSubcategoria.getIdCategoria();
                    subcategoriaListNewSubcategoria.setIdCategoria(categoria);
                    subcategoriaListNewSubcategoria = em.merge(subcategoriaListNewSubcategoria);
                    if (oldIdCategoriaOfSubcategoriaListNewSubcategoria != null && !oldIdCategoriaOfSubcategoriaListNewSubcategoria.equals(categoria)) {
                        oldIdCategoriaOfSubcategoriaListNewSubcategoria.getSubcategoriaList().remove(subcategoriaListNewSubcategoria);
                        oldIdCategoriaOfSubcategoriaListNewSubcategoria = em.merge(oldIdCategoriaOfSubcategoriaListNewSubcategoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = categoria.getIdCategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Subcategoria> subcategoriaListOrphanCheck = categoria.getSubcategoriaList();
            for (Subcategoria subcategoriaListOrphanCheckSubcategoria : subcategoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Subcategoria " + subcategoriaListOrphanCheckSubcategoria + " in its subcategoriaList field has a non-nullable idCategoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidade idEntidade = categoria.getIdEntidade();
            if (idEntidade != null) {
                idEntidade.getCategoriaList().remove(categoria);
                idEntidade = em.merge(idEntidade);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
