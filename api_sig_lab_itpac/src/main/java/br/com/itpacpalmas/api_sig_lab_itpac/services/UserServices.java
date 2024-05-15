package br.com.itpacpalmas.api_sig_lab_itpac.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.*;

@Service
public class UserServices implements UserDetailsService {

	@Autowired
	UsuarioRepository repository;

	public UserServices(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = repository.findByUserName(username);
		
		if (user != null) {
			
			String[] roles = new String[user.getRoles().size()];
			for (int index = 0; index < user.getRoles().size(); index++) {
				roles[index] = user.getRoles().get(index);
			}
	
			return User.builder().username(user.getUserName()).password(user.getPassword()).roles(roles).build();
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}

	}


}