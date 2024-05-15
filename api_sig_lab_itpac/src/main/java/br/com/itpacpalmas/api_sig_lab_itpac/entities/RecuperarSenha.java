package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecuperarSenha {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String codigo;
	private LocalDateTime dataLimite;
	private Boolean utilizado;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(LocalDateTime dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Boolean getUtilizado() {
		return utilizado;
	}

	public void setUtilizado(Boolean utilizado) {
		this.utilizado = utilizado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}