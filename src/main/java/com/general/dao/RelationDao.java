package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.general.model.Relation;
import com.general.model.User;

public interface RelationDao extends JpaRepository<Relation, Long> {

	List<Relation> findAllByFriend(User u);

	List<Relation> findAllByUser(User u);
	
	List<Relation> findAllByFriendAndUser(User friend, User user);
	
	@Query("SELECT r FROM Relation r WHERE r.user = :user and r.friend=:friend")
	List<Relation> findAllByIdUserAndIdFriend(@Param(value = "user") User user, @Param(value = "friend")User friend);
	
	
	@Query("SELECT r FROM Relation r WHERE  r.user=:user")
	List<Relation> findAllById(@Param(value = "user")  User user);
}
