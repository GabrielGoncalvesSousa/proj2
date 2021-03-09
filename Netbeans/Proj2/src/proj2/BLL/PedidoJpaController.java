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
import proj2.DAL.Administrador;
import proj2.DAL.Estado;
import proj2.DAL.Subcategoria;
import proj2.DAL.Utilizador;
import proj2.DAL.PedidoFotos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.DAL.Pedido;

/**
 *
 * @author nunop
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        if (pedido.getPedidoFotosList() == null) {
            pedido.setPedidoFotosList(new ArrayList<PedidoFotos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador idAdministrador = pedido.getIdAdministrador();
            if (idAdministrador != null) {
                idAdministrador = em.getReference(idAdministrador.getClass(), idAdministrador.getIdAdministrador());
                pedido.setIdAdministrador(idAdministrador);
            }
            Estado idEstado = pedido.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                pedido.setIdEstado(idEstado);
            }
            Subcategoria idSubcategoria = pedido.getIdSubcategoria();
            if (idSubcategoria != null) {
                idSubcategoria = em.getReference(idSubcategoria.getClass(), idSubcategoria.getIdSubcategoria());
                pedido.setIdSubcategoria(idSubcategoria);
            }
            Utilizador idUtilizador = pedido.getIdUtilizador();
            if (idUtilizador != null) {
                idUtilizador = em.getReference(idUtilizador.getClass(), idUtilizador.getIdUtilizador());
                pedido.setIdUtilizador(idUtilizador);
            }
            List<PedidoFotos> attachedPedidoFotosList = new ArrayList<PedidoFotos>();
            for (PedidoFotos pedidoFotosListPedidoFotosToAttach : pedido.getPedidoFotosList()) {
                pedidoFotosListPedidoFotosToAttach = em.getReference(pedidoFotosListPedidoFotosToAttach.getClass(), pedidoFotosListPedidoFotosToAttach.getIdFoto());
                attachedPedidoFotosList.add(pedidoFotosListPedidoFotosToAttach);
            }
            pedido.setPedidoFotosList(attachedPedidoFotosList);
            em.persist(pedido);
            if (idAdministrador != null) {
                idAdministrador.getPedidoList().add(pedido);
                idAdministrador = em.merge(idAdministrador);
            }
            if (idEstado != null) {
                idEstado.getPedidoList().add(pedido);
                idEstado = em.merge(idEstado);
            }
            if (idSubcategoria != null) {
                idSubcategoria.getPedidoList().add(pedido);
                idSubcategoria = em.merge(idSubcategoria);
            }
            if (idUtilizador != null) {
                idUtilizador.getPedidoList().add(pedido);
                idUtilizador = em.merge(idUtilizador);
            }
            for (PedidoFotos pedidoFotosListPedidoFotos : pedido.getPedidoFotosList()) {
                Pedido oldIdPedidoOfPedidoFotosListPedidoFotos = pedidoFotosListPedidoFotos.getIdPedido();
                pedidoFotosListPedidoFotos.setIdPedido(pedido);
                pedidoFotosListPedidoFotos = em.merge(pedidoFotosListPedidoFotos);
                if (oldIdPedidoOfPedidoFotosListPedidoFotos != null) {
                    oldIdPedidoOfPedidoFotosListPedidoFotos.getPedidoFotosList().remove(pedidoFotosListPedidoFotos);
                    oldIdPedidoOfPedidoFotosListPedidoFotos = em.merge(oldIdPedidoOfPedidoFotosListPedidoFotos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getIdPedido());
            Administrador idAdministradorOld = persistentPedido.getIdAdministrador();
            Administrador idAdministradorNew = pedido.getIdAdministrador();
            Estado idEstadoOld = persistentPedido.getIdEstado();
            Estado idEstadoNew = pedido.getIdEstado();
            Subcategoria idSubcategoriaOld = persistentPedido.getIdSubcategoria();
            Subcategoria idSubcategoriaNew = pedido.getIdSubcategoria();
            Utilizador idUtilizadorOld = persistentPedido.getIdUtilizador();
            Utilizador idUtilizadorNew = pedido.getIdUtilizador();
            List<PedidoFotos> pedidoFotosListOld = persistentPedido.getPedidoFotosList();
            List<PedidoFotos> pedidoFotosListNew = pedido.getPedidoFotosList();
            if (idAdministradorNew != null) {
                idAdministradorNew = em.getReference(idAdministradorNew.getClass(), idAdministradorNew.getIdAdministrador());
                pedido.setIdAdministrador(idAdministradorNew);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                pedido.setIdEstado(idEstadoNew);
            }
            if (idSubcategoriaNew != null) {
                idSubcategoriaNew = em.getReference(idSubcategoriaNew.getClass(), idSubcategoriaNew.getIdSubcategoria());
                pedido.setIdSubcategoria(idSubcategoriaNew);
            }
            if (idUtilizadorNew != null) {
                idUtilizadorNew = em.getReference(idUtilizadorNew.getClass(), idUtilizadorNew.getIdUtilizador());
                pedido.setIdUtilizador(idUtilizadorNew);
            }
            List<PedidoFotos> attachedPedidoFotosListNew = new ArrayList<PedidoFotos>();
            for (PedidoFotos pedidoFotosListNewPedidoFotosToAttach : pedidoFotosListNew) {
                pedidoFotosListNewPedidoFotosToAttach = em.getReference(pedidoFotosListNewPedidoFotosToAttach.getClass(), pedidoFotosListNewPedidoFotosToAttach.getIdFoto());
                attachedPedidoFotosListNew.add(pedidoFotosListNewPedidoFotosToAttach);
            }
            pedidoFotosListNew = attachedPedidoFotosListNew;
            pedido.setPedidoFotosList(pedidoFotosListNew);
            pedido = em.merge(pedido);
            if (idAdministradorOld != null && !idAdministradorOld.equals(idAdministradorNew)) {
                idAdministradorOld.getPedidoList().remove(pedido);
                idAdministradorOld = em.merge(idAdministradorOld);
            }
            if (idAdministradorNew != null && !idAdministradorNew.equals(idAdministradorOld)) {
                idAdministradorNew.getPedidoList().add(pedido);
                idAdministradorNew = em.merge(idAdministradorNew);
            }
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getPedidoList().remove(pedido);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getPedidoList().add(pedido);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idSubcategoriaOld != null && !idSubcategoriaOld.equals(idSubcategoriaNew)) {
                idSubcategoriaOld.getPedidoList().remove(pedido);
                idSubcategoriaOld = em.merge(idSubcategoriaOld);
            }
            if (idSubcategoriaNew != null && !idSubcategoriaNew.equals(idSubcategoriaOld)) {
                idSubcategoriaNew.getPedidoList().add(pedido);
                idSubcategoriaNew = em.merge(idSubcategoriaNew);
            }
            if (idUtilizadorOld != null && !idUtilizadorOld.equals(idUtilizadorNew)) {
                idUtilizadorOld.getPedidoList().remove(pedido);
                idUtilizadorOld = em.merge(idUtilizadorOld);
            }
            if (idUtilizadorNew != null && !idUtilizadorNew.equals(idUtilizadorOld)) {
                idUtilizadorNew.getPedidoList().add(pedido);
                idUtilizadorNew = em.merge(idUtilizadorNew);
            }
            for (PedidoFotos pedidoFotosListOldPedidoFotos : pedidoFotosListOld) {
                if (!pedidoFotosListNew.contains(pedidoFotosListOldPedidoFotos)) {
                    pedidoFotosListOldPedidoFotos.setIdPedido(null);
                    pedidoFotosListOldPedidoFotos = em.merge(pedidoFotosListOldPedidoFotos);
                }
            }
            for (PedidoFotos pedidoFotosListNewPedidoFotos : pedidoFotosListNew) {
                if (!pedidoFotosListOld.contains(pedidoFotosListNewPedidoFotos)) {
                    Pedido oldIdPedidoOfPedidoFotosListNewPedidoFotos = pedidoFotosListNewPedidoFotos.getIdPedido();
                    pedidoFotosListNewPedidoFotos.setIdPedido(pedido);
                    pedidoFotosListNewPedidoFotos = em.merge(pedidoFotosListNewPedidoFotos);
                    if (oldIdPedidoOfPedidoFotosListNewPedidoFotos != null && !oldIdPedidoOfPedidoFotosListNewPedidoFotos.equals(pedido)) {
                        oldIdPedidoOfPedidoFotosListNewPedidoFotos.getPedidoFotosList().remove(pedidoFotosListNewPedidoFotos);
                        oldIdPedidoOfPedidoFotosListNewPedidoFotos = em.merge(oldIdPedidoOfPedidoFotosListNewPedidoFotos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pedido.getIdPedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getIdPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Administrador idAdministrador = pedido.getIdAdministrador();
            if (idAdministrador != null) {
                idAdministrador.getPedidoList().remove(pedido);
                idAdministrador = em.merge(idAdministrador);
            }
            Estado idEstado = pedido.getIdEstado();
            if (idEstado != null) {
                idEstado.getPedidoList().remove(pedido);
                idEstado = em.merge(idEstado);
            }
            Subcategoria idSubcategoria = pedido.getIdSubcategoria();
            if (idSubcategoria != null) {
                idSubcategoria.getPedidoList().remove(pedido);
                idSubcategoria = em.merge(idSubcategoria);
            }
            Utilizador idUtilizador = pedido.getIdUtilizador();
            if (idUtilizador != null) {
                idUtilizador.getPedidoList().remove(pedido);
                idUtilizador = em.merge(idUtilizador);
            }
            List<PedidoFotos> pedidoFotosList = pedido.getPedidoFotosList();
            for (PedidoFotos pedidoFotosListPedidoFotos : pedidoFotosList) {
                pedidoFotosListPedidoFotos.setIdPedido(null);
                pedidoFotosListPedidoFotos = em.merge(pedidoFotosListPedidoFotos);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
