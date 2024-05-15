package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.RecuperarSenha;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.RecuperarSenhaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.services.EmailService;

@RestController
@RequestMapping(value="api/forgotpass")
public class EsqueceuSenhaController {
	
@Autowired
UsuarioRepository usuarioRepository;
@Autowired
RecuperarSenhaRepository recuperarSenhaRepository;

	@GetMapping
	public ResponseEntity<?> enviarEmail(@RequestParam(name = "email") String email) {
		 
		Usuario usuario = usuarioRepository.findByEmail(email);
		Random r = new Random();
		DecimalFormat format = new DecimalFormat("Afya-000000");
		String codigo = format.format(r.nextInt(1000000));
		RecuperarSenha entity = new RecuperarSenha();
		entity.setCodigo(codigo);
		entity.setUsuario(usuario);
		entity.setUtilizado(false);
		entity.setDataLimite(LocalDateTime.now().plusDays(1));
		recuperarSenhaRepository.save(entity);
		EmailService mail = new EmailService(usuario.getPessoa().getEmail(), "Esqueceu a Senha",codigo+" é seu código de recuperação de senha.");
		mail.enviar();
		return ResponseEntity.ok().body("Email enviado!");
		
	}
	
	@GetMapping(value="/alterpass")
	public ResponseEntity<?> alterarSenha(@RequestParam String codigo,@RequestParam String senha) {
	
		RecuperarSenha obj = recuperarSenhaRepository.findByCodigo(codigo);
		if(obj == null || obj.getDataLimite().isBefore(LocalDateTime.now()) || obj.getUtilizado() == true) {
			
			return null;
		}
		else {
			Usuario usu = obj.getUsuario();
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
			usu.setPassword(bCryptPasswordEncoder.encode(senha));
			obj.setUtilizado(true);
			usuarioRepository.save(usu);
			recuperarSenhaRepository.save(obj);
			
				
			return ResponseEntity.ok().body("Senha alterada!");
		}
		
		
		
	}



}
