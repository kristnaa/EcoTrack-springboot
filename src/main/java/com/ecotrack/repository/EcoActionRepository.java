package com.ecotrack.repository;

import com.ecotrack.model.EcoAction;
import com.ecotrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EcoActionRepository extends JpaRepository<EcoAction, Long> {
    List<EcoAction> findByUser(User user);
    
//    @Query("SELECT e.user.id, COUNT(e) as actionCount FROM EcoAction e GROUP BY e.user.id ORDER BY actionCount DESC")
    
    
    @Query("SELECT u.id, u.name, COUNT(a.id) as totalActions " +
    	       "FROM User u " +
    	       "JOIN EcoAction a ON u.id = a.user.id " +
    	       "GROUP BY u.id, u.name " +
    	       "ORDER BY totalActions DESC")
    List<Object[]> findTopContributors();
}