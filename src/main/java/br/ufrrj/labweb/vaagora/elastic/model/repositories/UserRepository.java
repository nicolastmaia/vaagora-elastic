package br.ufrrj.labweb.vaagora.elastic.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import br.ufrrj.labweb.vaagora.elastic.model.entities.EntityUser;

public interface UserRepository extends PagingAndSortingRepository<EntityUser, Long> {

	/**
	 * Find a user instance by its name.
	 * 
	 * @param name String - A user's name
	 * @return EntityUser - An EntityUser object
	 */
	@Query("select u from EntityUser u where u.name like :name%")
	public Page<EntityUser> findByName(@Param("name") String name, @PageableDefault Pageable pageable);

}
