package br.com.itpacpalmas.api_sig_lab_itpac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // @NatiQuery("SELECT u FROM Usuario u WHERE u.userName =:userName")
    Usuario findByUserName(String userName);
    
	@Query(value="SELECT * FROM usuario where pessoa = (SELECT t.idpessoa FROM public.pessoa t WHERE email = :email)" ,nativeQuery = true)
	Usuario findByEmail(String email);
}
