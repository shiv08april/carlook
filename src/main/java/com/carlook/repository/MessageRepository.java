package com.carlook.repository;

import com.carlook.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author
 *
 */
public interface MessageRepository extends JpaRepository<Message, Integer>{

}
