package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.security.AccountCredentialsVO;
import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UsuarioRepository repository;

	@PostMapping(value = "/cadastrar")
	public Usuario cadastro(@RequestBody Usuario usuario) {
		usuario.setId(null);
		usuario.setAccountNonExpired(true);
		usuario.setAccountNonLocked(true);
		usuario.setCredentialsNonExpired(true);
		usuario.setEnabled(true);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

		return repository.save(usuario);

	}

	@PutMapping
	public Usuario editar(@RequestBody Usuario usuario) {
		Usuario usu = repository.save(usuario);
		return usu;
	}

	@GetMapping(value = "/listar")
	public List<Usuario> listar() {
		return repository.findAll();
	}

}
