/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.BLL.exceptions.PreexistingEntityException;
import proj2.DAL.Pedido;
import proj2.DAL.PedidoFotos;

/**
 *
 * @author nunop
 */
public class PedidoFotosJpaController implements Serializable {

    public PedidoFotosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoFotos pedidoFotos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido idPedido = pedidoFotos.getIdPedido();
            if (idPedido != null) {
                idPedido = em.getReference(idPedido.getClass(), idPedido.getIdPedido());
                pedidoFotos.setIdPedido(idPedido);
            }
            em.persist(pedidoFotos);
            if (idPedido != null) {
                idPedido.getPedidoFotosList().add(pedidoFotos);
                idPedido = em.merge(idPedido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedidoFotos(pedidoFotos.getIdFoto()) != null) {
                throw new PreexistingEntityException("PedidoFotos " + pedidoFotos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidoFotos pedidoFotos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoFotos persistentPedidoFotos = em.find(PedidoFotos.class, pedidoFotos.getIdFoto());
            Pedido idPedidoOld = persistentPedidoFotos.getIdPedido();
            Pedido idPedidoNew = pedidoFotos.getIdPedido();
            if (idPedidoNew != null) {
                idPedidoNew = em.getReference(idPedidoNew.getClass(), idPedidoNew.getIdPedido());
                pedidoFotos.setIdPedido(idPedidoNew);
            }
            pedidoFotos = em.merge(pedidoFotos);
            if (idPedidoOld != null && !idPedidoOld.equals(idPedidoNew)) {
                idPedidoOld.getPedidoFotosList().remove(pedidoFotos);
                idPedidoOld = em.merge(idPedidoOld);
            }
            if (idPedidoNew != null && !idPedidoNew.equals(idPedidoOld)) {
                idPedidoNew.getPedidoFotosList().add(pedidoFotos);
                idPedidoNew = em.merge(idPedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pedidoFotos.getIdFoto();
                if (findPedidoFotos(id) == null) {
                    throw new NonexistentEntityException("The pedidoFotos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoFotos pedidoFotos;
            try {
                pedidoFotos = em.getReference(PedidoFotos.class, id);
                pedidoFotos.getIdFoto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidoFotos with id " + id + " no longer exists.", enfe);
            }
            Pedido idPedido = pedidoFotos.getIdPedido();
            if (idPedido != null) {
                idPedido.getPedidoFotosList().remove(pedidoFotos);
                idPedido = em.merge(idPedido);
            }
            em.remove(pedidoFotos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidoFotos> findPedidoFotosEntities() {
        return findPedidoFotosEntities(true, -1, -1);
    }

    public List<PedidoFotos> findPedidoFotosEntities(int maxResults, int firstResult) {
        return findPedidoFotosEntities(false, maxResults, firstResult);
    }

    private List<PedidoFotos> findPedidoFotosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PedidoFotos.class));
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

    public PedidoFotos findPedidoFotos(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoFotos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoFotosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PedidoFotos> rt = cq.from(PedidoFotos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
