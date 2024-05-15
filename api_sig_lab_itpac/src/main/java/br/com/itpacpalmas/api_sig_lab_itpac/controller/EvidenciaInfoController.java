package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aula;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.EvidenciaInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.services.EvidenciaInfoService;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.EvidenciaInfo;

@RestController
@RequestMapping("/api/evidencia/info")
@CrossOrigin
public class EvidenciaInfoController {

    @Autowired
    EvidenciaInfoService evidenciaInfoService;


    @GetMapping("/byPeriodo/{id}")
    public List<Aula> byPeriodo(@PathVariable(value = "id") int id) {
        return evidenciaInfoService.byPeriodo(id);
    }
    @GetMapping("/hora_aprendisagen_por_periodo/{id}")
    public Map<String,Double> horasbyPeriodo(@PathVariable(value = "id") int id) {
        Map<String,Double> retorno = new HashMap<>();
        retorno.put("horasAprendizagen", evidenciaInfoService.horasbyPeriodo(id));
        return retorno;
    }
    @GetMapping("/{id}")
    public EvidenciaInfo Info(@PathVariable(value = "id") int id) {
        return evidenciaInfoService.find(id);
    }
    @PostMapping()
    public EvidenciaInfo criar(@RequestBody EvidenciaInfo evidencia) {

        return evidenciaInfoService.creat(evidencia);
    }
    @DeleteMapping("/{id}")
    public EvidenciaInfo deletar(@PathVariable(value = "id") Integer id) {
        return evidenciaInfoService.Delete(id);
    }
    @GetMapping
    public List<EvidenciaInfo> InfoLista() {
        return evidenciaInfoService.findAll();
    }
    @PutMapping
    public EvidenciaInfo put(@RequestBody EvidenciaInfo evidencia) {
        return evidenciaInfoService.Alter(evidencia);
    }
    
}