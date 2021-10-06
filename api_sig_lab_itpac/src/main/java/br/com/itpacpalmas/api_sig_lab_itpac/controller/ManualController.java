package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.itpacpalmas.api_sig_lab_itpac.Services.ManualServiss;
import br.com.itpacpalmas.api_sig_lab_itpac.Services.FileStorageService;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Manual;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.PerfilUser;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ManualResponseVO;
//import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ManualInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PerfilUserRepository;

// https://www.youtube.com/watch?v=DtC_KfU6b1o
@RestController
@RequestMapping("api/manual")
public class ManualController {
    @Autowired
	private FileStorageService servises;
    
    @GetMapping("")
    public  List<ManualResponseVO> getInfo() {
        return servises.buscarTodosInfo();
    }
    
    @GetMapping("{id}")
    public ManualResponseVO getInfoById(@PathVariable int id) {
        return servises.buscarbyIdInfo(id );
    }
    
    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getDocById(@PathVariable int id, HttpServletRequest request) {
        return servises.downloadFile(id, request);
    }
    
    @PostMapping
	public ManualResponseVO uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("descricao") String descricao) {
        return servises.uploadFile(file,descricao);
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