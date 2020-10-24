package br.ufrrj.labweb.vaagora.elastic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ReflectionUtils;

import br.ufrrj.labweb.vaagora.elastic.model.entities.EntityCurrentLoc;
import br.ufrrj.labweb.vaagora.elastic.model.repositories.CurrentLocRepository;

public class CurrentLocService {
    @Autowired
    CurrentLocRepository curentLocRepository;

    public Page<EntityCurrentLoc> getAll(Pageable pageable) {
        return curentLocRepository.findAll(pageable);
    }

    public Optional<EntityCurrentLoc> getById(long id) {
        try {
            return curentLocRepository.findById(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public EntityCurrentLoc add(EntityCurrentLoc location) {
        try {
            return curentLocRepository.save(location);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public EntityCurrentLoc edit(long id, EntityCurrentLoc location) throws IllegalArgumentException, DataIntegrityViolationException {
        if (location == null) {
            throw new IllegalArgumentException("received: null object as a parameter; expecting: An EntityCurrentLoc object");
        }

        Optional<EntityCurrentLoc> locationWithOldValues = curentLocRepository.findById(id);
        if (locationWithOldValues.isPresent()) {
            EntityCurrentLoc modifiedlocation = locationWithOldValues.get();
            ReflectionUtils.doWithFields(modifiedlocation.getClass(), field -> {
                field.setAccessible(true);
                if (field.get(location) != null && field.getName() != "id") {
                    field.set(modifiedlocation, field.get(location));
                }
            });
            return curentLocRepository.save(modifiedlocation);
        }

        return null;

    }

    public boolean delete(long id) {
        try {
            if (curentLocRepository.findById(id).isPresent()) {
                curentLocRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
