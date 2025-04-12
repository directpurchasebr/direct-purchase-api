package br.com.directpurchase.auth.payload;

import java.time.LocalDateTime;

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
public class UsuarioSessionPayload {

    private Integer usuarioSessionId;
    private String deviceId;
    private String userAgent;
    private String platform;
    private String language;
    private String tokenUser;
    private String tokenAccess;
    private LocalDateTime dataModif;
    private LocalDateTime dataSession;
    private Boolean indSession;
}
