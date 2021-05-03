/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.BLL;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import proj2.DAL.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.BLL.exceptions.PreexistingEntityException;
import proj2.DAL.Estado;

/**
 *
 * @author nunop
 */
public class EstadoJpaController implements Serializable {
 private static final String PERSISTENCE_UNIT_NAME = "Proj2PU";
    private static EntityManagerFactory factory = null;
    private static EntityManager em = null;
   
    public static void create(Estado est){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(est);
        em.getTransaction().commit();
    }
    
    public static void update(Estado est){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(est);
        em.getTransaction().commit();
        
    }
        
    public static List<Estado> readAll(){
        List<Estado> ests = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Estado.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Estado est = ((Estado)obj);
            ests.add(est);
        }        
        return ests;
    }   
   
    public static void delete(Estado est){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(est);
        em.getTransaction().commit();
    }
    
     public static List<Pedido> getUtilizadorList(Estado est){
        return est.getPedidoList();
    }
    
}
