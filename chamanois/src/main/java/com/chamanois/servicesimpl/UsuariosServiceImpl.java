package com.chamanois.servicesimpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chamanois.dto.UsuariosDTO;
import com.chamanois.model.Usuarios;
import com.chamanois.model.Role;
import com.chamanois.repositories.RoleRepository;
import com.chamanois.repositories.UsuariosRepository;
import com.chamanois.services.UsuariosService;

@Service
public class UsuariosServiceImpl implements UsuariosService, UserDetailsService {

	@Autowired
	private UsuariosRepository usuariosRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<UsuariosDTO> findAllUsuarios() {
		List<Usuarios> usuariosList = usuariosRepository.findAll();
		return usuariosList.stream().map(this::mapToUsuariosDTO).collect(Collectors.toList());
	}

	private UsuariosDTO mapToUsuariosDTO(Usuarios usuarios) {
		UsuariosDTO usuariosDTO = new UsuariosDTO();
		usuariosDTO.setIdUsuario(usuarios.getIdUsuario());
		usuariosDTO.setNomeUsuario(usuarios.getNomeUsuario());
		usuariosDTO.setCpfUsuario(usuarios.getCpfUsuario());
		usuariosDTO.setEnderecoUsuario(usuarios.getEnderecoUsuario());
		usuariosDTO.setTelefoneUsuario(usuarios.getTelefoneUsuario());
		usuariosDTO.setEmailUsuario(usuarios.getEmailUsuario());
		usuariosDTO.setSenhaUsuario(usuarios.getSenhaUsuario());
		usuariosDTO.setProdutos(usuarios.getProdutos());
		usuariosDTO.setRoles(usuarios.getRoles());
		return usuariosDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuarios findUserById(Long idUsuario) {
		return usuariosRepository.findById(idUsuario).orElse(null);
	}

	@Override
	public void saveUsuario(UsuariosDTO usuarioDTO) {
		Usuarios usuarios = new Usuarios();
		usuarios.setNomeUsuario(usuarioDTO.getNomeUsuario());
		usuarios.setCpfUsuario(usuarioDTO.getCpfUsuario());
		usuarios.setEnderecoUsuario(usuarioDTO.getEnderecoUsuario());
		usuarios.setTelefoneUsuario(usuarioDTO.getTelefoneUsuario());
		usuarios.setEmailUsuario(usuarioDTO.getEmailUsuario());
		usuarios.setSenhaUsuario(passwordEncoder.encode(usuarioDTO.getSenhaUsuario()));

		Role role = roleRepository.findByAuthority("ROLE_COMUM");

		if (role == null) {
			throw new IllegalStateException("'ROLE_COMUM' não encontrada.");
		}

		usuarios.setRoles((List<Role>) Arrays.asList(role));
		usuariosRepository.save(usuarios);
	}

	@Override
	public Usuarios findUserByEmail(String emailUsuario) {
		return usuariosRepository.findByEmailUsuario(emailUsuario);
	}

	@Override
	public UserDetails loadUserByUsername(String emailUsuario) throws UsernameNotFoundException {
		Usuarios usuarios = usuariosRepository.findByEmailUsuario(emailUsuario);

		if (usuarios != null) {
			return new org.springframework.security.core.userdetails.User(usuarios.getEmailUsuario(),
					usuarios.getSenhaUsuario(), mapRolesToAuthorities(usuarios.getRoles()));
		} else {
			throw new UsernameNotFoundException("Email ou senha inválidos.");
		}
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		Collection<? extends GrantedAuthority> mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
		return mapRoles;
	}

	@Override
	public Usuarios updateUsuario(Long idUsuario, Usuarios usuarioAtualizado) {
		Usuarios usuarioExistente = usuariosRepository.findById(idUsuario).orElse(null);
		if (usuarioExistente != null) {
			usuarioExistente.setEnderecoUsuario(usuarioAtualizado.getEnderecoUsuario());
			usuarioExistente.setTelefoneUsuario(usuarioAtualizado.getTelefoneUsuario());
			return usuariosRepository.save(usuarioExistente);
		} else {
			throw new RuntimeException("Usuario com o ID" + idUsuario + "não foi encontrada.");
		}
	}

	@Override
	public void deleteUsuario(Long idUsuario) {
		usuariosRepository.deleteById(idUsuario);

	}
}
