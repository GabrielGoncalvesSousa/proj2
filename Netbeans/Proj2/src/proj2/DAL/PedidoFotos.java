/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nunop
 */
@Entity
@Table(name = "PEDIDO_FOTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PedidoFotos.findAll", query = "SELECT p FROM PedidoFotos p"),
    @NamedQuery(name = "PedidoFotos.findByIdFoto", query = "SELECT p FROM PedidoFotos p WHERE p.idFoto = :idFoto"),
    @NamedQuery(name = "PedidoFotos.findByPath", query = "SELECT p FROM PedidoFotos p WHERE p.path = :path")})
public class PedidoFotos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FOTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idFoto;
    @Basic(optional = false)
    @Column(name = "PATH")
    private String path;
    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID_PEDIDO")
    @ManyToOne
    private Pedido idPedido;

    public PedidoFotos() {
    }

    public PedidoFotos(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public PedidoFotos(Integer idFoto, String path) {
        this.idFoto = idFoto;
        this.path = path;
    }

    public Integer getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFoto != null ? idFoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidoFotos)) {
            return false;
        }
        PedidoFotos other = (PedidoFotos) object;
        if ((this.idFoto == null && other.idFoto != null) || (this.idFoto != null && !this.idFoto.equals(other.idFoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proj2.DAL.PedidoFotos[ idFoto=" + idFoto + " ]";
    }
    
}
