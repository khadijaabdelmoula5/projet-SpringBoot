package tn.iit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	
    List<Client> findByNomStartsWithIgnoreCase(String nom);  

}
