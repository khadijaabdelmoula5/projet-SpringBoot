package tn.iit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.iit.dao.ClientRepository;
import tn.iit.entity.Client;
import tn.iit.exception.ClientNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public List<Client> findAll() {
		return clientRepository.findAll(); 
	}

	public void save(Client client) {
		clientRepository.save(client); 
	}

	public Client findById(Integer id) {
		return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("id =" + id + " not found"));
	}

	public void deleteById(Integer id) {
		clientRepository.deleteById(id); 
	}

	public boolean existsById(Integer id) {
		return clientRepository.existsById(id);
	}

	public List<Client> findByNomStartsWithIgnoreCase(String query) {
		return clientRepository.findByNomStartsWithIgnoreCase(query);
	}

}
