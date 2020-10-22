package br.ufrrj.labweb.vaagora.elastic.model.repositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.ufrrj.labweb.vaagora.elastic.model.entities.EntityCurrentLoc;

public interface CurrentLocRepository extends PagingAndSortingRepository<EntityCurrentLoc, Long> {

}
