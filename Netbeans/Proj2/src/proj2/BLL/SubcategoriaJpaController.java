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
import javax.persistence.Persistence;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.BLL.exceptions.PreexistingEntityException;
import proj2.DAL.Subcategoria;

/**
 *
 * @author nunop
 */
public class SubcategoriaJpaController implements Serializable {
private static final String PERSISTENCE_UNIT_NAME = "Proj2PU";
    private static EntityManagerFactory factory = null;
    private static EntityManager em = null;
   
    public static void create(Subcategoria sub){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(sub);
        em.getTransaction().commit();
    }
    
    public static void update(Subcategoria sub){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(sub);
        em.getTransaction().commit();
        
    }
        
    public static List<Subcategoria> readAll(){
        List<Subcategoria> subs = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Subcategoria.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Subcategoria sub = ((Subcategoria)obj);
            subs.add(sub);
        }        
        return subs;
    }   
    public static Subcategoria readByIdSubcategoria(int id){
        Subcategoria sub=null;
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Subcategoria.findByIdSubcategoria");
        q1.setParameter("idSubcategoria", id);
       Object obj = q1.getSingleResult();
        
        if(obj != null){
            sub = ((Subcategoria)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        return sub;
    }   
   
    public static void delete(Categoria cat){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(cat);
        em.getTransaction().commit();
    }
    
    public static List<Pedido> getPedidoList(Subcategoria sub){
        return sub.getPedidoList();
    }
    
    public static Categoria getCategoriaList(Subcategoria sub){
        return sub.getIdCategoria();
    }
    
}
