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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import proj2.BLL.exceptions.IllegalOrphanException;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.BLL.exceptions.PreexistingEntityException;
import proj2.DAL.Categoria;
import proj2.DAL.Utilizador;
import proj2.DAL.Subcategoria;


/**
 *
 * @author nunop
 */
public class CategoriaJpaController implements Serializable {
 private static final String PERSISTENCE_UNIT_NAME = "Proj2PU";
    private static EntityManagerFactory factory = null;
    private static EntityManager em = null;
   
    public static void create(Categoria cat){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(cat);
        em.getTransaction().commit();
    }
    
    public static void update(Categoria cat){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(cat);
        em.getTransaction().commit();
        
    }
        
    public static List<Categoria> readAll(){
        List<Categoria> cats = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Categoria.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Categoria cat = ((Categoria)obj);
            cats.add(cat);
        }        
        return cats;
    }   
    public static Categoria readByIdCategoria(Categoria cat){
        int id =cat.getIdCategoria();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Categoria.findByIdCategoria");
        q1.setParameter("idCategoria", id);
       Object obj = q1.getSingleResult();
        
        if(obj != null){
            cat = ((Categoria)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        return cat;
    }   
   
    public static void delete(Categoria cat){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(cat);
        em.getTransaction().commit();
    }
    
    public static List<Utilizador> getUtilizadorList(Categoria cat){
        return cat.getUtilizadorList();
    }
    
    public static List<Subcategoria> getSubcategoriaList(Categoria cat){
        return cat.getSubcategoriaList();
    }
    
    
    
}
