package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.services.DisciplinaService;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Disciplina;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.DisciplinaRepository;
@CrossOrigin
@RestController
@RequestMapping(value = "api/disciplina")
public class DisciplinaController {
    @Autowired
    private DisciplinaService service;
    
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("getAll/{filtro}")
public List<Disciplina> getAll(@PathVariable (value = "filtro") boolean filtro){
    List<Disciplina> retorno = disciplinaRepository.findAll();
    if (filtro) {
        retorno.removeIf(p -> !p.isAtivo()); 
    }
    return retorno;
}
    
    @GetMapping(value = "/{id}")
    public Disciplina find(@PathVariable Integer id){
        Disciplina obj = service.find(id);
        return obj;
    }
    
    @GetMapping()
    public List<Disciplina> findbyname(@RequestParam(required = false) String nome){
        List<Disciplina> obj = service.findAll();
        if (nome != null) {
            obj.removeIf(p -> !p.getNome().equals(nome));
        }
        return obj;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Disciplina> insert(@RequestBody Disciplina obj){
        obj = service.insert(obj);
        
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping()
    public ResponseEntity<Disciplina> update(@RequestBody Disciplina obj){

        //Garantir que realmente é o objeto do ID
        //obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.ok().body(obj);
    }

    @PatchMapping(value="/desativar/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disciplina> disable (@PathVariable (value = "id")Integer id){
        return service.disable(id);
        
    }
    @PatchMapping(value="/ativar/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disciplina> Ativar(@PathVariable (value = "id")Integer id){
        return service.ativar(id);
        
    }
}
