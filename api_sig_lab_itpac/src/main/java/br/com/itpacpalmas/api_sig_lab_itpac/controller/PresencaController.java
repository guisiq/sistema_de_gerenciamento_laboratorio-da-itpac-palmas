package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.services.PresencaService;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aula;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.Presenca;



@RestController
@RequestMapping("api/evidencia/presenca")
@CrossOrigin
public class PresencaController {
	 
	@Autowired
	PresencaService service;

	
	@PostMapping()
	public void add(@RequestBody Presenca presenca) {
		service.save(presenca);	
	}
	
	@GetMapping("/{id}")
	public Presenca findById(@PathVariable(value="id") int id) {
		return service.findById(id);	
	}
	
	
	
	@PutMapping()
	public void alter(@RequestBody Presenca perfil) {
		service.save(perfil);	
	}
	
	
}
