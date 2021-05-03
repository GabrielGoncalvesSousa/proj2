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
import proj2.DAL.Estado;
import proj2.DAL.Subcategoria;
import proj2.DAL.Utilizador;
import proj2.DAL.PedidoFotos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.BLL.exceptions.PreexistingEntityException;
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

    public void create(Pedido pedido) throws PreexistingEntityException, Exception {
        if (pedido.getPedidoFotosList() == null) {
            pedido.setPedidoFotosList(new ArrayList<PedidoFotos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            Utilizador idAdministrador = pedido.getIdAdministrador();
            if (idAdministrador != null) {
                idAdministrador = em.getReference(idAdministrador.getClass(), idAdministrador.getIdUtilizador());
                pedido.setIdAdministrador(idAdministrador);
            }
            Utilizador idUtilizador = pedido.getIdUtilizador();
            if (idUtilizador != null) {
                idUtilizador = em.getReference(idUtilizador.getClass(), idUtilizador.getIdUtilizador());
                pedido.setIdUtilizador(idUtilizador);
            }
            Utilizador idEntidade = pedido.getIdEntidade();
            if (idEntidade != null) {
                idEntidade = em.getReference(idEntidade.getClass(), idEntidade.getIdUtilizador());
                pedido.setIdEntidade(idEntidade);
            }
            List<PedidoFotos> attachedPedidoFotosList = new ArrayList<PedidoFotos>();
            for (PedidoFotos pedidoFotosListPedidoFotosToAttach : pedido.getPedidoFotosList()) {
                pedidoFotosListPedidoFotosToAttach = em.getReference(pedidoFotosListPedidoFotosToAttach.getClass(), pedidoFotosListPedidoFotosToAttach.getIdFoto());
                attachedPedidoFotosList.add(pedidoFotosListPedidoFotosToAttach);
            }
            pedido.setPedidoFotosList(attachedPedidoFotosList);
            em.persist(pedido);
            if (idEstado != null) {
                idEstado.getPedidoList().add(pedido);
                idEstado = em.merge(idEstado);
            }
            if (idSubcategoria != null) {
                idSubcategoria.getPedidoList().add(pedido);
                idSubcategoria = em.merge(idSubcategoria);
            }
            if (idAdministrador != null) {
                idAdministrador.getPedidoList().add(pedido);
                idAdministrador = em.merge(idAdministrador);
            }
            if (idUtilizador != null) {
                idUtilizador.getPedidoList().add(pedido);
                idUtilizador = em.merge(idUtilizador);
            }
            if (idEntidade != null) {
                idEntidade.getPedidoList().add(pedido);
                idEntidade = em.merge(idEntidade);
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
        } catch (Exception ex) {
            if (findPedido(pedido.getIdPedido()) != null) {
                throw new PreexistingEntityException("Pedido " + pedido + " already exists.", ex);
            }
            throw ex;
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
            Estado idEstadoOld = persistentPedido.getIdEstado();
            Estado idEstadoNew = pedido.getIdEstado();
            Subcategoria idSubcategoriaOld = persistentPedido.getIdSubcategoria();
            Subcategoria idSubcategoriaNew = pedido.getIdSubcategoria();
            Utilizador idAdministradorOld = persistentPedido.getIdAdministrador();
            Utilizador idAdministradorNew = pedido.getIdAdministrador();
            Utilizador idUtilizadorOld = persistentPedido.getIdUtilizador();
            Utilizador idUtilizadorNew = pedido.getIdUtilizador();
            Utilizador idEntidadeOld = persistentPedido.getIdEntidade();
            Utilizador idEntidadeNew = pedido.getIdEntidade();
            List<PedidoFotos> pedidoFotosListOld = persistentPedido.getPedidoFotosList();
            List<PedidoFotos> pedidoFotosListNew = pedido.getPedidoFotosList();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                pedido.setIdEstado(idEstadoNew);
            }
            if (idSubcategoriaNew != null) {
                idSubcategoriaNew = em.getReference(idSubcategoriaNew.getClass(), idSubcategoriaNew.getIdSubcategoria());
                pedido.setIdSubcategoria(idSubcategoriaNew);
            }
            if (idAdministradorNew != null) {
                idAdministradorNew = em.getReference(idAdministradorNew.getClass(), idAdministradorNew.getIdUtilizador());
                pedido.setIdAdministrador(idAdministradorNew);
            }
            if (idUtilizadorNew != null) {
                idUtilizadorNew = em.getReference(idUtilizadorNew.getClass(), idUtilizadorNew.getIdUtilizador());
                pedido.setIdUtilizador(idUtilizadorNew);
            }
            if (idEntidadeNew != null) {
                idEntidadeNew = em.getReference(idEntidadeNew.getClass(), idEntidadeNew.getIdUtilizador());
                pedido.setIdEntidade(idEntidadeNew);
            }
            List<PedidoFotos> attachedPedidoFotosListNew = new ArrayList<PedidoFotos>();
            for (PedidoFotos pedidoFotosListNewPedidoFotosToAttach : pedidoFotosListNew) {
                pedidoFotosListNewPedidoFotosToAttach = em.getReference(pedidoFotosListNewPedidoFotosToAttach.getClass(), pedidoFotosListNewPedidoFotosToAttach.getIdFoto());
                attachedPedidoFotosListNew.add(pedidoFotosListNewPedidoFotosToAttach);
            }
            pedidoFotosListNew = attachedPedidoFotosListNew;
            pedido.setPedidoFotosList(pedidoFotosListNew);
            pedido = em.merge(pedido);
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
            if (idAdministradorOld != null && !idAdministradorOld.equals(idAdministradorNew)) {
                idAdministradorOld.getPedidoList().remove(pedido);
                idAdministradorOld = em.merge(idAdministradorOld);
            }
            if (idAdministradorNew != null && !idAdministradorNew.equals(idAdministradorOld)) {
                idAdministradorNew.getPedidoList().add(pedido);
                idAdministradorNew = em.merge(idAdministradorNew);
            }
            if (idUtilizadorOld != null && !idUtilizadorOld.equals(idUtilizadorNew)) {
                idUtilizadorOld.getPedidoList().remove(pedido);
                idUtilizadorOld = em.merge(idUtilizadorOld);
            }
            if (idUtilizadorNew != null && !idUtilizadorNew.equals(idUtilizadorOld)) {
                idUtilizadorNew.getPedidoList().add(pedido);
                idUtilizadorNew = em.merge(idUtilizadorNew);
            }
            if (idEntidadeOld != null && !idEntidadeOld.equals(idEntidadeNew)) {
                idEntidadeOld.getPedidoList().remove(pedido);
                idEntidadeOld = em.merge(idEntidadeOld);
            }
            if (idEntidadeNew != null && !idEntidadeNew.equals(idEntidadeOld)) {
                idEntidadeNew.getPedidoList().add(pedido);
                idEntidadeNew = em.merge(idEntidadeNew);
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
                int id = pedido.getIdPedido();
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

    public void destroy(int id) throws NonexistentEntityException {
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
            Utilizador idAdministrador = pedido.getIdAdministrador();
            if (idAdministrador != null) {
                idAdministrador.getPedidoList().remove(pedido);
                idAdministrador = em.merge(idAdministrador);
            }
            Utilizador idUtilizador = pedido.getIdUtilizador();
            if (idUtilizador != null) {
                idUtilizador.getPedidoList().remove(pedido);
                idUtilizador = em.merge(idUtilizador);
            }
            Utilizador idEntidade = pedido.getIdEntidade();
            if (idEntidade != null) {
                idEntidade.getPedidoList().remove(pedido);
                idEntidade = em.merge(idEntidade);
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

    public Pedido findPedido(int id) {
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
