package com.chamanois.services;

import java.util.List;

import com.chamanois.dto.UsuariosDTO;
import com.chamanois.model.Usuarios;

public interface UsuariosService {
	List<UsuariosDTO> findAllUsuarios();

	void saveUsuario(UsuariosDTO usuarioDTO);
	
	Usuarios findUserById(Long idUsuario);

	Usuarios findUserByEmail(String emailUsuario);

	Usuarios updateUsuario(Long idUsuario, Usuarios usuarioAtualizado);

	void deleteUsuario(Long idUsuario);

}
