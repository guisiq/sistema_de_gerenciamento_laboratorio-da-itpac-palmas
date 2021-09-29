package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Manual {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(nullable = false, length = 200)
    private String documento;


//-------------Getter e Setter
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

}