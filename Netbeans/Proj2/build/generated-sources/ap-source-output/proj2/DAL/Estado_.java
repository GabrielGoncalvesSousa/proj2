package proj2.DAL;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proj2.DAL.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-27T14:30:38")
@StaticMetamodel(Estado.class)
public class Estado_ { 

    public static volatile SingularAttribute<Estado, Integer> idEstado;
    public static volatile ListAttribute<Estado, Pedido> pedidoList;
    public static volatile SingularAttribute<Estado, String> descricao;

}