package proj2.DAL;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proj2.DAL.Estado;
import proj2.DAL.PedidoFotos;
import proj2.DAL.Subcategoria;
import proj2.DAL.Utilizador;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-27T14:30:38")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Utilizador> idAdministrador;
    public static volatile SingularAttribute<Pedido, String> obsRes;
    public static volatile SingularAttribute<Pedido, String> localidade;
    public static volatile SingularAttribute<Pedido, String> obsAdmin;
    public static volatile SingularAttribute<Pedido, Integer> idPedido;
    public static volatile SingularAttribute<Pedido, String> coordenadas;
    public static volatile ListAttribute<Pedido, PedidoFotos> pedidoFotosList;
    public static volatile SingularAttribute<Pedido, String> descricao;
    public static volatile SingularAttribute<Pedido, Date> dtInicioRes;
    public static volatile SingularAttribute<Pedido, Date> dtFimRes;
    public static volatile SingularAttribute<Pedido, Utilizador> idUtilizador;
    public static volatile SingularAttribute<Pedido, Estado> idEstado;
    public static volatile SingularAttribute<Pedido, Utilizador> idEntidade;
    public static volatile SingularAttribute<Pedido, Date> dtAprovacao;
    public static volatile SingularAttribute<Pedido, Date> dtCriacao;
    public static volatile SingularAttribute<Pedido, Subcategoria> idSubcategoria;
    public static volatile SingularAttribute<Pedido, String> codPostal;
    public static volatile SingularAttribute<Pedido, BigInteger> urgencia;
    public static volatile SingularAttribute<Pedido, String> rua;

}