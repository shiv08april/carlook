package com.carlook.repository;

import java.util.Optional;

import com.carlook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>
{

	Optional<User> findByEmail(String email);

}
