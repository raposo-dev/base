package com.base.base.models.contractdetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "contestants")
public class Contestant {

	@Id
	private int id;

	private String nif;

	private String description;

	@ManyToMany(mappedBy = "contestants")
	private Set<ContractDetails> contractDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ContractDetails> getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(Set<ContractDetails> contractDetails) {
		this.contractDetails = contractDetails;
	}
}
