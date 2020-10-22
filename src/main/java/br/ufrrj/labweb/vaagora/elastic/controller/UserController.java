package br.ufrrj.labweb.vaagora.elastic.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.vaagora.elastic.model.entities.EntityUser;
import br.ufrrj.labweb.vaagora.elastic.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/users")
	public @ResponseBody ResponseEntity<EntityUser> create(@RequestBody EntityUser user) {
		try {
			EntityUser savedUser = userService.add(user);

			return new ResponseEntity<EntityUser>(savedUser, HttpStatus.CREATED);

		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * This method fetches all the users in the database and returns them in a Page.
	 *
	 * @return Status 200
	 * @route /users
	 * @method GET
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public @ResponseBody ResponseEntity<Page<EntityUser>> list(Pageable pageable) {
		return new ResponseEntity<>(userService.getAll(pageable), HttpStatus.OK);
	}

	/**
	 * This method updates a given user (by ID) based on the user received as
	 * parameter. It receives the old user's ID in the URI and the new user in the
	 * request's body.
	 * 
	 * @param user    EntityUser - An EntityUser object
	 * @param user_id Long - A user's ID.
	 * @return Status 202, if successful
	 * @route /users/{user_id}
	 * @method PUT
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{user_id}", produces = "application/json")
	public @ResponseBody ResponseEntity<EntityUser> update(@PathVariable long user_id, @RequestBody EntityUser user) {
		try {
			EntityUser savedUser = userService.edit(user_id, user);

			if (savedUser != null) {
				return new ResponseEntity<EntityUser>(savedUser, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	/**
	 * This method deletes a given user by ID. It receives the parameters in the
	 * URI.
	 * 
	 * @param user_id Long - A user's ID.
	 * @return Status 204, if successful
	 *         <li>Status 404, if not successful</li>
	 *         <li>Status 500, if internal server error</li>
	 * @route /users/{user_id}
	 * @method DELETE
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/byid/{user_id}")
	public @ResponseBody ResponseEntity<Boolean> delete(@PathVariable long user_id) {
		if (userService.delete(user_id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * This method fetches a given user by ID. It receives the parameters in the
	 * URI.
	 * 
	 * @param user_id Long - A user's ID.
	 * @return Status 200, if successful
	 *         <li>Status 404, if not successful</li>
	 * @route /users/{user_id}
	 * @method GET
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users/byid/{user_id}")
	public @ResponseBody ResponseEntity<EntityUser> show(@PathVariable long user_id) {
		Optional<EntityUser> user = userService.getById(user_id);
		if (user.isPresent())
			return new ResponseEntity<EntityUser>(user.get(), HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * This method fetches a user by its username. It receives parameters in the
	 * URI.
	 * 
	 * @param username String - A user's username
	 * @return Status 200, if successful
	 *         <li>Status 404, if not successful</li>
	 * @route /users/login/{username}
	 * @method GET
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users/byname/{name}" )
	public @ResponseBody ResponseEntity<Page<EntityUser>> showByLogin(@PathVariable String name, Pageable pageable) {
		try {
			Page<EntityUser> users = userService.getByName(name, pageable);

			if (!users.isEmpty()) {
				return new ResponseEntity<>(users, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
