package com.base.base.models.contractdetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "documents")
public class Document {
	public String description;
	@Id
	private int id;
	@ManyToMany(mappedBy = "documents")
	private Set<ContractDetails> contractDetails;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<ContractDetails> getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(Set<ContractDetails> contractDetails) {
		this.contractDetails = contractDetails;
	}
}
