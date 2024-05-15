package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aluno;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AlunoRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;

@RestController
@RequestMapping("api/aluno")
@CrossOrigin
public class AlunoController {

	@Autowired
	AlunoRepository alunoRepository;

	@Autowired
	UsuarioRepository usuarioRepository;


	@GetMapping("getAll/{filtro}")
	public List<Aluno> getAll(@PathVariable (value = "filtro") boolean filtro){
		List<Aluno> retorno = alunoRepository.findAll();
		if (filtro) {
			retorno.removeIf(p -> !p.isAtivo()); 
		}
		return retorno;
	}
	
	
	@PostMapping
	public Aluno add(@RequestBody Aluno aluno) {
		 aluno.setAtivo(true);
		 Aluno alunoRetorno = alunoRepository.save(aluno);
		 Usuario usu = new Usuario();
		 usu.setPessoa(alunoRetorno.getPessoa());
		 usu.setUserName(aluno.getPessoa().getCpf());
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		 usu.setPassword(bCryptPasswordEncoder.encode("afya"+aluno.getPessoa().getCpf()));
		 usu.setAccountNonExpired(true);
		 usu.setAccountNonLocked(true);
		 usu.setCredentialsNonExpired(true);
		 usuarioRepository.save(usu);
		 
	  return alunoRetorno;	
	}
	
	@GetMapping(value="/usuario")
	public List<Usuario> getUsu() {
		
		return usuarioRepository.findAll();
		
	}
	
	@GetMapping(value="/{id}")
	public Optional<Aluno> findById(@PathVariable(value="id") int id) {
	  return alunoRepository.findById(id);	
	}
	
	@GetMapping
	public List<Aluno> findAll() {
	  return alunoRepository.findAll();	
	}
	
	@PutMapping
	public Aluno alter(@RequestBody Aluno aluno) {
	  return alunoRepository.save(aluno);	
	}
	
	@PatchMapping(value="/desativar/{id}")
	public ResponseEntity<Aluno> disable(@PathVariable( value =  "id") Integer id) {
		try{
            Aluno aluno = alunoRepository.findById(id).get();
			aluno.setAtivo(false);
			alunoRepository.save(aluno);
			return ResponseEntity.status(HttpStatus.OK).body(aluno);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	@PatchMapping(value="/Ativar/{id}")
	public ResponseEntity<Aluno> ativar(@PathVariable( value =  "id") Integer id) {
		try{
            Aluno aluno = alunoRepository.findById(id).get();
			aluno.setAtivo(true);
			alunoRepository.save(aluno);
			return ResponseEntity.status(HttpStatus.OK).body(aluno);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
		

		
	
}
