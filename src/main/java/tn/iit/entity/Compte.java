package tn.iit.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "t_compte")
public class Compte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Include
	@Column(name = "rib", unique = true, nullable = false)
	private Integer rib; 

	@Column(name = "solde", nullable = false)
	private Double solde;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	
}