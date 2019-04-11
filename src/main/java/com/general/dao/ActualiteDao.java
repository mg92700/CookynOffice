    
package com.general.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.general.model.Actualite;
import com.general.model.Etape;
import com.general.model.Favoris;
import com.general.model.Ingredient;
import com.general.model.User;


public interface ActualiteDao extends JpaRepository<Actualite, Integer> {

List<Actualite> findAllByuser(User user);
	
    @Query("SELECT a FROM Actualite a WHERE a.user=?1 and a.date >= ?2 and a.date <= ?3")
    List<Actualite> findAllByuserAndPeriod(User user,Date date1,Date date2);
    
    @Query("SELECT a FROM Actualite a WHERE a.user in(:lstIdFriend)")
    List<Actualite> findAllByFriend(@Param(value = "lstIdFriend")List<User> lstIdFriend);
    
    @Query("SELECT a FROM Actualite a WHERE a.user=:user and a.idWhat=:idFriend and a.typeActualite='Follow'")
    Actualite findFollowByUser(@Param(value = "user")User user,@Param(value = "idFriend")Integer idFriend);
    
    @Query("SELECT a FROM Actualite a WHERE a.user=:user and a.idWhat=:idRecette and a.typeActualite='Create'")
    Actualite findCreateByUser(@Param(value = "user")User user,@Param(value = "idRecette")Integer idRecette);
    
    @Query("SELECT a FROM Actualite a WHERE a.user=:user and a.idWhat=:idRecette and a.typeActualite='Favoris'")
    Actualite findFavorisByUser(@Param(value = "user")User user,@Param(value = "idRecette")Integer idRecette);
}