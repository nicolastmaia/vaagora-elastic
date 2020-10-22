package br.ufrrj.labweb.vaagora.elastic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import br.ufrrj.labweb.vaagora.elastic.model.entities.EntityUser;
import br.ufrrj.labweb.vaagora.elastic.model.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public Page<EntityUser> getAll(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	public Optional<EntityUser> getById(long id) {
		try {
			return userRepository.findById(id);			
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public Page<EntityUser> getByName(String name, Pageable pageable){
		return userRepository.findByName(name, pageable);
	}
	
	public EntityUser add(EntityUser user) {
		try {
			return userRepository.save(user);			
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	public EntityUser edit(long id, EntityUser user) throws IllegalArgumentException, DataIntegrityViolationException {
		if (user == null) {
			throw new IllegalArgumentException("received: null object as a parameter; expecting: An EntityUser object");
		}

		Optional<EntityUser> userWithOldValues = userRepository.findById(id);
		if (userWithOldValues.isPresent()) {
			EntityUser modifiedUser = userWithOldValues.get();
			ReflectionUtils.doWithFields(modifiedUser.getClass(), field -> {
				field.setAccessible(true);
				if (field.get(user) != null && field.getName() != "id") {
					field.set(modifiedUser, field.get(user));
				}
			});
			return userRepository.save(modifiedUser);
		}

		return null;

	}
	
	public boolean delete(long id) {
		try {
			if (userRepository.findById(id).isPresent()) {
				userRepository.deleteById(id);
				return true;
			}
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
