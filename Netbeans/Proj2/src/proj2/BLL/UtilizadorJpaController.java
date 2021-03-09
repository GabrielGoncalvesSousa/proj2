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
import proj2.DAL.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proj2.BLL.exceptions.IllegalOrphanException;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.DAL.Utilizador;

/**
 *
 * @author nunop
 */
public class UtilizadorJpaController implements Serializable {

    public UtilizadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilizador utilizador) {
        if (utilizador.getPedidoList() == null) {
            utilizador.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : utilizador.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            utilizador.setPedidoList(attachedPedidoList);
            em.persist(utilizador);
            for (Pedido pedidoListPedido : utilizador.getPedidoList()) {
                Utilizador oldIdUtilizadorOfPedidoListPedido = pedidoListPedido.getIdUtilizador();
                pedidoListPedido.setIdUtilizador(utilizador);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdUtilizadorOfPedidoListPedido != null) {
                    oldIdUtilizadorOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdUtilizadorOfPedidoListPedido = em.merge(oldIdUtilizadorOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Utilizador utilizador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilizador persistentUtilizador = em.find(Utilizador.class, utilizador.getIdUtilizador());
            List<Pedido> pedidoListOld = persistentUtilizador.getPedidoList();
            List<Pedido> pedidoListNew = utilizador.getPedidoList();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its idUtilizador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            utilizador.setPedidoList(pedidoListNew);
            utilizador = em.merge(utilizador);
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Utilizador oldIdUtilizadorOfPedidoListNewPedido = pedidoListNewPedido.getIdUtilizador();
                    pedidoListNewPedido.setIdUtilizador(utilizador);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdUtilizadorOfPedidoListNewPedido != null && !oldIdUtilizadorOfPedidoListNewPedido.equals(utilizador)) {
                        oldIdUtilizadorOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdUtilizadorOfPedidoListNewPedido = em.merge(oldIdUtilizadorOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = utilizador.getIdUtilizador();
                if (findUtilizador(id) == null) {
                    throw new NonexistentEntityException("The utilizador with id " + id + " no longer exists.");
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
            Utilizador utilizador;
            try {
                utilizador = em.getReference(Utilizador.class, id);
                utilizador.getIdUtilizador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilizador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pedido> pedidoListOrphanCheck = utilizador.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilizador (" + utilizador + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable idUtilizador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(utilizador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Utilizador> findUtilizadorEntities() {
        return findUtilizadorEntities(true, -1, -1);
    }

    public List<Utilizador> findUtilizadorEntities(int maxResults, int firstResult) {
        return findUtilizadorEntities(false, maxResults, firstResult);
    }

    private List<Utilizador> findUtilizadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilizador.class));
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

    public Utilizador findUtilizador(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilizador.class, id);
        } finally {
            em.close();
        }
    }

    public int getUtilizadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilizador> rt = cq.from(Utilizador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
