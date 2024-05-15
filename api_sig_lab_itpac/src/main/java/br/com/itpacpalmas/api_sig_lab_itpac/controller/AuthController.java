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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.security.AccountCredentialsVO;
import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/login")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UsuarioRepository repository;

	@PostMapping(value = "/cadastrar")
	public Usuario cadastro(@RequestBody Usuario usuario) {
		usuario.setAccountNonExpired(true);
		usuario.setAccountNonLocked(true);
		usuario.setCredentialsNonExpired(true);
		usuario.setEnabled(true);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

		return repository.save(usuario);
		 
	}

	@GetMapping(value = "/listar")
	public List<Usuario> listar() {
		return repository.findAll();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signin", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		try {
			String username = data.getUsername();
			String pasword = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));

			Usuario user = repository.findByUserName(username);

			String token = "";

			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}

			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			model.put("roles", user.getRoles());
			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
