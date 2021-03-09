/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nunop
 */
@Entity
@Table(name = "UTILIZADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u"),
    @NamedQuery(name = "Utilizador.findByIdUtilizador", query = "SELECT u FROM Utilizador u WHERE u.idUtilizador = :idUtilizador"),
    @NamedQuery(name = "Utilizador.findByEstado", query = "SELECT u FROM Utilizador u WHERE u.estado = :estado"),
    @NamedQuery(name = "Utilizador.findByEmail", query = "SELECT u FROM Utilizador u WHERE u.email = :email"),
    @NamedQuery(name = "Utilizador.findByDtNasc", query = "SELECT u FROM Utilizador u WHERE u.dtNasc = :dtNasc"),
    @NamedQuery(name = "Utilizador.findByPassword", query = "SELECT u FROM Utilizador u WHERE u.password = :password"),
    @NamedQuery(name = "Utilizador.findByNome", query = "SELECT u FROM Utilizador u WHERE u.nome = :nome"),
    @NamedQuery(name = "Utilizador.findByCodPostal", query = "SELECT u FROM Utilizador u WHERE u.codPostal = :codPostal"),
    @NamedQuery(name = "Utilizador.findByLocalidade", query = "SELECT u FROM Utilizador u WHERE u.localidade = :localidade"),
    @NamedQuery(name = "Utilizador.findByMorada", query = "SELECT u FROM Utilizador u WHERE u.morada = :morada")})
public class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_UTILIZADOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal idUtilizador;
    @Column(name = "ESTADO")
    private Short estado;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DT_NASC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtNasc;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Column(name = "COD_POSTAL")
    private String codPostal;
    @Column(name = "LOCALIDADE")
    private String localidade;
    @Column(name = "MORADA")
    private String morada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtilizador")
    private List<Pedido> pedidoList;

    public Utilizador() {
    }

    public Utilizador(BigDecimal idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Utilizador(BigDecimal idUtilizador, String email, String password, String nome) {
        this.idUtilizador = idUtilizador;
        this.email = email;
        this.password = password;
        this.nome = nome;
    }

    public BigDecimal getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(BigDecimal idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtilizador != null ? idUtilizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
        if ((this.idUtilizador == null && other.idUtilizador != null) || (this.idUtilizador != null && !this.idUtilizador.equals(other.idUtilizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proj2.DAL.Utilizador[ idUtilizador=" + idUtilizador + " ]";
    }
    
}
