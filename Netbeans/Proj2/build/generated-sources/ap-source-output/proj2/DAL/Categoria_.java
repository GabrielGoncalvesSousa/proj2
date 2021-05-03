package proj2.DAL;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proj2.DAL.Subcategoria;
import proj2.DAL.Utilizador;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-27T14:30:38")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile ListAttribute<Categoria, Utilizador> utilizadorList;
    public static volatile ListAttribute<Categoria, Subcategoria> subcategoriaList;
    public static volatile SingularAttribute<Categoria, String> nome;
    public static volatile SingularAttribute<Categoria, Integer> idCategoria;

}