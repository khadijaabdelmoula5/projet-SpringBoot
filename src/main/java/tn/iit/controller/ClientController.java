package tn.iit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tn.iit.entity.Client;
import tn.iit.exception.ClientNotFoundException;
import tn.iit.service.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping
	public String showClientsPage(@RequestParam(value = "searchId", required = false) Integer searchId, Model model) {
		if (searchId != null) {
			
			try {
				Client client = clientService.findById(searchId);
				model.addAttribute("clients", client != null ? List.of(client) : List.of());
				model.addAttribute("searchId", searchId); 
			} catch (ClientNotFoundException e) {
				model.addAttribute("errorMessage", "Client non trouvé avec l'ID : " + searchId);
				model.addAttribute("clients", clientService.findAll()); 
																		
			}
		} else {
			
			model.addAttribute("clients", clientService.findAll());
		}
		model.addAttribute("client", new Client()); 
		return "clients"; 
	}

	@PostMapping("/save")
	public String saveClient(@ModelAttribute Client client) {
		clientService.save(client);
		return "redirect:/clients";
	}

	@PostMapping("/addclient")
	public String addClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
		if (clientService.existsById(client.getId())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Un client avec cet ID (CIN) existe déjà.");
		} else {
			clientService.save(client);
		}
		return "redirect:/clients";
	}

	@GetMapping("/edit/{id}")
	public String editClient(@PathVariable Integer id, Model model) {
		Client client = clientService.findById(id);
		if (client == null) {
			return "redirect:/clients"; 
		}
		model.addAttribute("client", client);
		return "edit-client"; 
	}

	@ResponseBody
	@PostMapping("/delete-ajax")
	public void deleteAjax(@RequestParam Integer id) {
		clientService.deleteById(id);
	}

	@GetMapping("/delete/{id}")
	public String deleteClient(@PathVariable Integer id) {
		clientService.deleteById(id);
		return "redirect:/clients"; 
	}
}
