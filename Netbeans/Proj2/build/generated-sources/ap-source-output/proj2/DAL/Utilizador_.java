package proj2.DAL;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proj2.DAL.Categoria;
import proj2.DAL.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-27T14:30:38")
@StaticMetamodel(Utilizador.class)
public class Utilizador_ { 

    public static volatile SingularAttribute<Utilizador, Short> estado;
    public static volatile SingularAttribute<Utilizador, String> telefone;
    public static volatile SingularAttribute<Utilizador, String> localidade;
    public static volatile ListAttribute<Utilizador, Pedido> pedidoList;
    public static volatile SingularAttribute<Utilizador, String> nif;
    public static volatile SingularAttribute<Utilizador, String> nome;
    public static volatile SingularAttribute<Utilizador, String> descricao;
    public static volatile SingularAttribute<Utilizador, Integer> idUtilizador;
    public static volatile SingularAttribute<Utilizador, String> password;
    public static volatile ListAttribute<Utilizador, Pedido> pedidoList1;
    public static volatile ListAttribute<Utilizador, Pedido> pedidoList2;
    public static volatile SingularAttribute<Utilizador, Categoria> idCategoria;
    public static volatile SingularAttribute<Utilizador, String> codPostal;
    public static volatile SingularAttribute<Utilizador, String> email;
    public static volatile SingularAttribute<Utilizador, Short> tipoutil;
    public static volatile SingularAttribute<Utilizador, String> morada;

}