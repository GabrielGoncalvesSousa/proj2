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
import javax.persistence.Persistence;
import proj2.BLL.exceptions.IllegalOrphanException;
import proj2.BLL.exceptions.NonexistentEntityException;
import proj2.BLL.exceptions.PreexistingEntityException;
import proj2.DAL.Utilizador;

/**
 *
 * @author nunop
 */
public class UtilizadorJpaController implements Serializable {
    private static final String PERSISTENCE_UNIT_NAME = "Proj2PU";
    private static EntityManagerFactory factory = null;
    private static EntityManager em = null;
   
    public static void create(Utilizador user){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
    
    public static void update(Utilizador user){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        
    }
        
    public static List<Utilizador> readAll(){
        List<Utilizador> users = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Utilizador.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Utilizador usr = ((Utilizador)obj);
            users.add(usr);
        }        
        return users;
    } 
    public static List<Utilizador> readByTipoUtil(Short id){
        List<Utilizador> users = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Utilizador.findByTipoutil");
        q1.setParameter("tipoutil", id);
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Utilizador usr = ((Utilizador)obj);
            users.add(usr);
        }        
        return users;
    }
    
    public static void changeEstado(int id,int estado){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        Query q1 = em.createNativeQuery("UPDATE Utilizador u SET u.estado = :estado WHERE u.idUtilizador = :idUtilizador");
        q1.setParameter("estado", estado);
        q1.setParameter("idUtilizador", id);
        em.getTransaction().commit();
    }
    
     public static Utilizador readUser(int id){
        Utilizador user = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Utilizador.findByIdUtilizador");
        q1.setParameter("idUtilizador", id);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            user = ((Utilizador)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return user;
    }
    public static int login(String email, String password){
            List<Utilizador> users = new ArrayList<>();           
            users = readAll();
         for(Utilizador u: users){
             if(email.equals(u.getEmail())){
                 System.out.println(u.getEmail());
                 if(password.equals(u.getPassword())){  
                     System.out.println(u.getPassword());
                     System.out.println("Email e Pass correto");
                     return u.getIdUtilizador();
                 }else{                    
                     //password errada
                 }
             }else{
                 //email nao existe
             }
         }
         System.out.println("Falhou for");
         return 5;
         //falhou
     }
   
    public static void delete(Utilizador user){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
    public static List<Pedido> getPedidoList(Utilizador user){
        return user.getPedidoList();
    }
    
    public static List<Pedido> getPedidoList1(Utilizador user){
        return user.getPedidoList1();
    }
    public static List<Pedido> getPedidoList2(Utilizador user){
        return user.getPedidoList2();
    }
    
    
}
