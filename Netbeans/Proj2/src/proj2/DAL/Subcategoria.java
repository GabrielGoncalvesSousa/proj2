/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nunop
 */
@Entity
@Table(name = "SUBCATEGORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subcategoria.findAll", query = "SELECT s FROM Subcategoria s"),
    @NamedQuery(name = "Subcategoria.findByIdSubcategoria", query = "SELECT s FROM Subcategoria s WHERE s.idSubcategoria = :idSubcategoria"),
    @NamedQuery(name = "Subcategoria.findByNome", query = "SELECT s FROM Subcategoria s WHERE s.nome = :nome"),
    @NamedQuery(name = "Subcategoria.findByDescricao", query = "SELECT s FROM Subcategoria s WHERE s.descricao = :descricao")})
public class Subcategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SUBCATEGORIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal idSubcategoria;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(mappedBy = "idSubcategoria")
    private List<Pedido> pedidoList;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    @ManyToOne(optional = false)
    private Categoria idCategoria;

    public Subcategoria() {
    }

    public Subcategoria(BigDecimal idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public Subcategoria(BigDecimal idSubcategoria, String nome) {
        this.idSubcategoria = idSubcategoria;
        this.nome = nome;
    }

    public BigDecimal getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(BigDecimal idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubcategoria != null ? idSubcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subcategoria)) {
            return false;
        }
        Subcategoria other = (Subcategoria) object;
        if ((this.idSubcategoria == null && other.idSubcategoria != null) || (this.idSubcategoria != null && !this.idSubcategoria.equals(other.idSubcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proj2.DAL.Subcategoria[ idSubcategoria=" + idSubcategoria + " ]";
    }
    
}
