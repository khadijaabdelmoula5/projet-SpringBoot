package tn.iit.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import tn.iit.entity.Compte;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.service.ClientService;
import tn.iit.service.CompteService;

@Controller
@RequestMapping("/comptes")
public class CompteController {

	@Autowired
	private CompteService compteService;

	@Autowired
	private ClientService clientService; 

	
	@GetMapping
	public String showComptesPage(@RequestParam(value = "searchRib", required = false) Integer searchRib, Model model) {
		if (searchRib != null) {
			
			try {
				Compte compte = compteService.findByRib(searchRib);
				model.addAttribute("comptes", compte != null ? List.of(compte) : List.of());
				model.addAttribute("searchRib", searchRib); 
			} catch (CompteNotFoundException e) {
				model.addAttribute("errorMessage", "Compte non trouvé avec l'ID : " + searchRib);
				model.addAttribute("comptes", compteService.findAll()); 
																		
			}
		} else {
			
			model.addAttribute("comptes", compteService.findAll());
		}
		model.addAttribute("compte", new Compte()); 
		return "comptes"; 
	}
	
	@GetMapping("/edit/{rib}")
	public String editCompte(@PathVariable Integer rib, Model model) {
		Compte compte = compteService.findByRib(rib);
		if (compte == null) {
			model.addAttribute("error", "Compte non trouvé");
			return "redirect:/comptes"; 
		}
		model.addAttribute("compte", compte);
		model.addAttribute("clients", clientService.findAll()); 
		return "edit-compte";
	}

	@PostMapping("/save")
	public String saveCompte(@ModelAttribute Compte compte, RedirectAttributes redirectAttributes) {
		if (compteService.existsByRib(compte.getRib())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Le RIB existe déjà.");
			return "redirect:/comptes";
		}

		compteService.save(compte);
		return "redirect:/comptes";
	}

	@PostMapping("/saveeditcompte")
	public String saveEditCompte(@ModelAttribute Compte compte, Model model) {
		if (compte.getRib() == null) {
			return "edit-compte";
		}
		compteService.save(compte);
		return "redirect:/comptes";
	}

	@GetMapping("/delete/{rib}")
	public String deleteCompte(@PathVariable Integer rib) {
		compteService.deleteByRib(rib);
		return "redirect:/comptes";
	}

	@ResponseBody
	@PostMapping("/delete-ajax")
	public void deleteAjax(@RequestParam Integer rib) {
		compteService.deleteByRib(rib);
	}

	@GetMapping("/autocomplete")
	@ResponseBody
	public List<Client> autoComplete(@RequestParam String query) {
		List<Client> clients = clientService.findByNomStartsWithIgnoreCase(query);
		return clients.stream().map(client -> new Client(client.getId(), client.getNom(), client.getPrenom(), null)).collect(Collectors.toList());
	}
}