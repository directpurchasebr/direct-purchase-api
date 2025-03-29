package br.com.directpurchase.auth.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = -6945051264947575517L;

	private String usuario;
	private String senha;

}
