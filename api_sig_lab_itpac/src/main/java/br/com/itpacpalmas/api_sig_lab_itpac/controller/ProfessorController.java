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

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Professor;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.ResourceNotFoundException;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ProfessorRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/api/professores")
@CrossOrigin
public class ProfessorController {

    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("getAll/{filtro}")
    public List<Professor> getAll(@PathVariable(value = "filtro") boolean filtro) {
        List<Professor> retorno = professorRepository.findAll();
        if (filtro) {
            retorno.removeIf(p -> !p.isAtivo());
        }
        return retorno;
    }

@PostMapping()
public Professor add(@RequestBody Professor professor){
	 Professor professorRetorno = professorRepository.save(professor);
	 Usuario usu = new Usuario();
	 usu.setPessoa(professorRetorno.getPessoa());
	 usu.setUserName(professor.getPessoa().getCpf());
	 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
	 usu.setPassword(bCryptPasswordEncoder.encode("afya"+professor.getPessoa().getCpf()));
	 usu.setAccountNonExpired(true);
	 usu.setAccountNonLocked(true);
	 usu.setCredentialsNonExpired(true);
	 usuarioRepository.save(usu);
    return professorRetorno;
}
@PutMapping()
public Professor alter(@RequestBody Professor professor){
    return professorRepository.save(professor);
}

 

    @PatchMapping(value = "/desativar/{id}")
    public ResponseEntity<Professor> disable(@PathVariable(value = "id") Integer id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professor com o id descrito nao foi encontrado "));
        professor.setAtivo(false);
        professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @PatchMapping(value = "/ativar/{id}")
    public ResponseEntity<Professor> ativar(@PathVariable(value = "id") Integer id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professor com o id descrito nao foi encontrado "));
        professor.setAtivo(true);
        professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

}
