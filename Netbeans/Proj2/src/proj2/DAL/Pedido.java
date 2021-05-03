/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nunop
 */
@Entity
@Table(name = "PEDIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByIdPedido", query = "SELECT p FROM Pedido p WHERE p.idPedido = :idPedido"),
    @NamedQuery(name = "Pedido.findByDescricao", query = "SELECT p FROM Pedido p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Pedido.findByDtCriacao", query = "SELECT p FROM Pedido p WHERE p.dtCriacao = :dtCriacao"),
    @NamedQuery(name = "Pedido.findByCodPostal", query = "SELECT p FROM Pedido p WHERE p.codPostal = :codPostal"),
    @NamedQuery(name = "Pedido.findByLocalidade", query = "SELECT p FROM Pedido p WHERE p.localidade = :localidade"),
    @NamedQuery(name = "Pedido.findByRua", query = "SELECT p FROM Pedido p WHERE p.rua = :rua"),
    @NamedQuery(name = "Pedido.findByCoordenadas", query = "SELECT p FROM Pedido p WHERE p.coordenadas = :coordenadas"),
    @NamedQuery(name = "Pedido.findByDtAprovacao", query = "SELECT p FROM Pedido p WHERE p.dtAprovacao = :dtAprovacao"),
    @NamedQuery(name = "Pedido.findByObsAdmin", query = "SELECT p FROM Pedido p WHERE p.obsAdmin = :obsAdmin"),
    @NamedQuery(name = "Pedido.findByUrgencia", query = "SELECT p FROM Pedido p WHERE p.urgencia = :urgencia"),
    @NamedQuery(name = "Pedido.findByDtInicioRes", query = "SELECT p FROM Pedido p WHERE p.dtInicioRes = :dtInicioRes"),
    @NamedQuery(name = "Pedido.findByDtFimRes", query = "SELECT p FROM Pedido p WHERE p.dtFimRes = :dtFimRes"),
    @NamedQuery(name = "Pedido.findByObsRes", query = "SELECT p FROM Pedido p WHERE p.obsRes = :obsRes")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PEDIDO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idPedido;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCriacao;
    @Column(name = "COD_POSTAL")
    private String codPostal;
    @Column(name = "LOCALIDADE")
    private String localidade;
    @Column(name = "RUA")
    private String rua;
    @Column(name = "COORDENADAS")
    private String coordenadas;
    @Column(name = "DT_APROVACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAprovacao;
    @Column(name = "OBS_ADMIN")
    private String obsAdmin;
    @Column(name = "URGENCIA")
    private BigInteger urgencia;
    @Column(name = "DT_INICIO_RES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicioRes;
    @Column(name = "DT_FIM_RES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFimRes;
    @Column(name = "OBS_RES")
    private String obsRes;
    @OneToMany(mappedBy = "idPedido")
    private List<PedidoFotos> pedidoFotosList;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "ID_SUBCATEGORIA", referencedColumnName = "ID_SUBCATEGORIA")
    @ManyToOne
    private Subcategoria idSubcategoria;
    @JoinColumn(name = "ID_ADMINISTRADOR", referencedColumnName = "ID_UTILIZADOR")
    @ManyToOne
    private Utilizador idAdministrador;
    @JoinColumn(name = "ID_UTILIZADOR", referencedColumnName = "ID_UTILIZADOR")
    @ManyToOne(optional = false)
    private Utilizador idUtilizador;
    @JoinColumn(name = "ID_ENTIDADE", referencedColumnName = "ID_UTILIZADOR")
    @ManyToOne
    private Utilizador idEntidade;

    public Pedido() {
    }

    public Pedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Date getDtAprovacao() {
        return dtAprovacao;
    }

    public void setDtAprovacao(Date dtAprovacao) {
        this.dtAprovacao = dtAprovacao;
    }

    public String getObsAdmin() {
        return obsAdmin;
    }

    public void setObsAdmin(String obsAdmin) {
        this.obsAdmin = obsAdmin;
    }

    public BigInteger getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(BigInteger urgencia) {
        this.urgencia = urgencia;
    }

    public Date getDtInicioRes() {
        return dtInicioRes;
    }

    public void setDtInicioRes(Date dtInicioRes) {
        this.dtInicioRes = dtInicioRes;
    }

    public Date getDtFimRes() {
        return dtFimRes;
    }

    public void setDtFimRes(Date dtFimRes) {
        this.dtFimRes = dtFimRes;
    }

    public String getObsRes() {
        return obsRes;
    }

    public void setObsRes(String obsRes) {
        this.obsRes = obsRes;
    }

    @XmlTransient
    public List<PedidoFotos> getPedidoFotosList() {
        return pedidoFotosList;
    }

    public void setPedidoFotosList(List<PedidoFotos> pedidoFotosList) {
        this.pedidoFotosList = pedidoFotosList;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Subcategoria getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Subcategoria idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public Utilizador getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Utilizador idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Utilizador getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Utilizador idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Utilizador getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Utilizador idEntidade) {
        this.idEntidade = idEntidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPedido != null ? idPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.idPedido == null && other.idPedido != null) || (this.idPedido != null && !this.idPedido.equals(other.idPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proj2.DAL.Pedido[ idPedido=" + idPedido + " ]";
    }
    
}
