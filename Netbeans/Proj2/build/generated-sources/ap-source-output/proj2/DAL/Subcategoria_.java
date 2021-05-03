package proj2.DAL;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proj2.DAL.Categoria;
import proj2.DAL.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-27T14:30:38")
@StaticMetamodel(Subcategoria.class)
public class Subcategoria_ { 

    public static volatile SingularAttribute<Subcategoria, Integer> idSubcategoria;
    public static volatile ListAttribute<Subcategoria, Pedido> pedidoList;
    public static volatile SingularAttribute<Subcategoria, String> nome;
    public static volatile SingularAttribute<Subcategoria, Categoria> idCategoria;
    public static volatile SingularAttribute<Subcategoria, String> descricao;

}