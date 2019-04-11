package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.general.model.TokenUser;
import com.general.model.User;

public interface TokenUserDao extends JpaRepository<TokenUser, Long> {

	List<TokenUser> findAllByuser(User user);
	TokenUser findByidTokenUser (int idTokenUser);

}
