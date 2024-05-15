package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.itpacpalmas.api_sig_lab_itpac.services.ArquivoEvidenciaService;
import br.com.itpacpalmas.api_sig_lab_itpac.services.ManualService;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ArquivoResponseVO;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ArquivoResponseVO;


// https://www.youtube.com/watch?v=DtC_KfU6b1o
@RestController
@RequestMapping("api/evidencia/arquivo")
@CrossOrigin
public class ArquivoEvidenciaController {
    @Autowired
	private ArquivoEvidenciaService servises;

    
    
    @GetMapping()
    public  ResponseEntity<?> getInfo(
        @RequestParam(required = false )Integer idEvidencia,
        @RequestParam(required = false )Integer idArquivo) {   
        
        if (idEvidencia != null) {
            return ResponseEntity.ok().body(servises.getByEvidencia(idEvidencia));
        } else if(idArquivo != null) {
            return ResponseEntity.ok().body(servises.buscarbyIdInfo(idArquivo));    
        }else
            return ResponseEntity.ok().body(servises.buscarTodosInfo());
    }
    
    
    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getDocById(@PathVariable int id, HttpServletRequest request) {
        return servises.downloadFile(id, request);
    }
    
    @PostMapping("/{idEvidencia}")
	public ArquivoResponseVO uploadFile(@RequestParam("descricao") String descricao,@RequestParam("file") MultipartFile file,@PathVariable int idEvidencia) {
        return servises.uploadFile(file,idEvidencia,descricao);
    }
    
    @PatchMapping("/{id}/{descricao}")
    public void update(@PathVariable int id,@PathVariable String descricao) {
        servises.update(id, descricao);
    }

    @DeleteMapping("/{id}")
    public void delet(@PathVariable int id) {
        
       servises.delete(id); 
    }

}
