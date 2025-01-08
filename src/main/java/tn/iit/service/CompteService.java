package tn.iit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.iit.dao.CompteRepository;
import tn.iit.entity.Compte;

@Service
public class CompteService {

	@Autowired
	private CompteRepository compteRepository;

	public List<Compte> findAll() {
		return compteRepository.findAll(); 
	}

	public void save(Compte compte) {
		compteRepository.save(compte); 
	}

	public Compte findByRib(Integer rib) {
		return compteRepository.findById(rib).orElse(null); 
	}

	public boolean existsByRib(Integer rib) {
		return compteRepository.findByRib(rib) != null; 
	}

	public void deleteByRib(Integer rib) {
		compteRepository.deleteById(rib); 
	}
		public List<Compte> findByClientNameStartsWithIgnoreCase(String query) {
			return compteRepository.findByClientNomStartsWithIgnoreCase(query);   
																				
		}
	


}