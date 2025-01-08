package tn.iit.dao;  

import java.util.List;  
import org.springframework.data.jpa.repository.JpaRepository;  
import tn.iit.entity.Compte;  

public interface CompteRepository extends JpaRepository<Compte, Integer> {  
    List<Compte> findByClientId(Integer id);  
    Compte findByRib(Integer rib);  
	List<Compte> findByClientNomStartsWithIgnoreCase(String nomClient); 



}