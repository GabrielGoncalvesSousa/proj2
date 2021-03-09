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
@Table(name = "ENTIDADE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidade.findAll", query = "SELECT e FROM Entidade e"),
    @NamedQuery(name = "Entidade.findByIdEntidade", query = "SELECT e FROM Entidade e WHERE e.idEntidade = :idEntidade"),
    @NamedQuery(name = "Entidade.findByNome", query = "SELECT e FROM Entidade e WHERE e.nome = :nome"),
    @NamedQuery(name = "Entidade.findByEstado", query = "SELECT e FROM Entidade e WHERE e.estado = :estado"),
    @NamedQuery(name = "Entidade.findByDescricao", query = "SELECT e FROM Entidade e WHERE e.descricao = :descricao"),
    @NamedQuery(name = "Entidade.findByEmail", query = "SELECT e FROM Entidade e WHERE e.email = :email"),
    @NamedQuery(name = "Entidade.findByTelefone", query = "SELECT e FROM Entidade e WHERE e.telefone = :telefone")})
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ENTIDADE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal idEntidade;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Column(name = "ESTADO")
    private Short estado;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONE")
    private String telefone;
    @OneToMany(mappedBy = "idEntidade")
    private List<Categoria> categoriaList;

    public Entidade() {
    }

    public Entidade(BigDecimal idEntidade) {
        this.idEntidade = idEntidade;
    }

    public Entidade(BigDecimal idEntidade, String nome) {
        this.idEntidade = idEntidade;
        this.nome = nome;
    }

    public BigDecimal getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(BigDecimal idEntidade) {
        this.idEntidade = idEntidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @XmlTransient
    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidade != null ? idEntidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidade)) {
            return false;
        }
        Entidade other = (Entidade) object;
        if ((this.idEntidade == null && other.idEntidade != null) || (this.idEntidade != null && !this.idEntidade.equals(other.idEntidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proj2.DAL.Entidade[ idEntidade=" + idEntidade + " ]";
    }
    
}
