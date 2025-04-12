package br.com.directpurchase.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "USUARIO_SESSION")
@SequenceGenerator(name = "USUARIOSESSION_SEQ", sequenceName = "USUARIOSESSION_SEQ", allocationSize = 1)
public class UsuarioSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USUARIOSESSION_SEQ")
    @Column(name = "USUARIOSESSION_ID")
    private Integer usuarioSessionId;

    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "USER_AGENT")
    private String userAgent;

    @Column(name = "PLATFORM")
    private String platform;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "TOKEN_USER")
    @Lob
    private String tokenUser;

    @Column(name = "TOKEN_ACCESS")
    @Lob
    private String tokenAccess;

    @Basic(optional = false)
    @Column(name = "DATA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataModif;

    @Basic(optional = false)
    @Column(name = "DATA_SESSION")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataSession;

    @Column(name = "IND_SESSION")
    private Boolean indSession;

}
