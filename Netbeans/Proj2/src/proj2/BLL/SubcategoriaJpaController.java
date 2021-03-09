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
import proj2.DAL.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.DAL.Subcategoria;

/**
 *
 * @author nunop
 */
public class SubcategoriaJpaController implements Serializable {

    public SubcategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subcategoria subcategoria) {
        if (subcategoria.getPedidoList() == null) {
            subcategoria.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = subcategoria.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                subcategoria.setIdCategoria(idCategoria);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : subcategoria.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            subcategoria.setPedidoList(attachedPedidoList);
            em.persist(subcategoria);
            if (idCategoria != null) {
                idCategoria.getSubcategoriaList().add(subcategoria);
                idCategoria = em.merge(idCategoria);
            }
            for (Pedido pedidoListPedido : subcategoria.getPedidoList()) {
                Subcategoria oldIdSubcategoriaOfPedidoListPedido = pedidoListPedido.getIdSubcategoria();
                pedidoListPedido.setIdSubcategoria(subcategoria);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdSubcategoriaOfPedidoListPedido != null) {
                    oldIdSubcategoriaOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdSubcategoriaOfPedidoListPedido = em.merge(oldIdSubcategoriaOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subcategoria subcategoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subcategoria persistentSubcategoria = em.find(Subcategoria.class, subcategoria.getIdSubcategoria());
            Categoria idCategoriaOld = persistentSubcategoria.getIdCategoria();
            Categoria idCategoriaNew = subcategoria.getIdCategoria();
            List<Pedido> pedidoListOld = persistentSubcategoria.getPedidoList();
            List<Pedido> pedidoListNew = subcategoria.getPedidoList();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                subcategoria.setIdCategoria(idCategoriaNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            subcategoria.setPedidoList(pedidoListNew);
            subcategoria = em.merge(subcategoria);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getSubcategoriaList().remove(subcategoria);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getSubcategoriaList().add(subcategoria);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setIdSubcategoria(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Subcategoria oldIdSubcategoriaOfPedidoListNewPedido = pedidoListNewPedido.getIdSubcategoria();
                    pedidoListNewPedido.setIdSubcategoria(subcategoria);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdSubcategoriaOfPedidoListNewPedido != null && !oldIdSubcategoriaOfPedidoListNewPedido.equals(subcategoria)) {
                        oldIdSubcategoriaOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdSubcategoriaOfPedidoListNewPedido = em.merge(oldIdSubcategoriaOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = subcategoria.getIdSubcategoria();
                if (findSubcategoria(id) == null) {
                    throw new NonexistentEntityException("The subcategoria with id " + id + " no longer exists.");
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
            Subcategoria subcategoria;
            try {
                subcategoria = em.getReference(Subcategoria.class, id);
                subcategoria.getIdSubcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subcategoria with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategoria = subcategoria.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getSubcategoriaList().remove(subcategoria);
                idCategoria = em.merge(idCategoria);
            }
            List<Pedido> pedidoList = subcategoria.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setIdSubcategoria(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(subcategoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subcategoria> findSubcategoriaEntities() {
        return findSubcategoriaEntities(true, -1, -1);
    }

    public List<Subcategoria> findSubcategoriaEntities(int maxResults, int firstResult) {
        return findSubcategoriaEntities(false, maxResults, firstResult);
    }

    private List<Subcategoria> findSubcategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subcategoria.class));
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

    public Subcategoria findSubcategoria(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subcategoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubcategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subcategoria> rt = cq.from(Subcategoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
