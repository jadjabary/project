package io.transferfile.sftp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.transferfile.sftp.controller.UserData;

@Repository("userRepository")
@Transactional
public interface UserRepository extends CrudRepository<UserData, String> {
	
		
	public UserData findByUsername(String username);
}
