package com.base.base.models;

import com.base.base.util.ContractMoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Contract {
	public String contractingProcedureType;
	@JsonFormat(pattern = "dd-MM-yyyy")
	public Date publicationDate;
	public String contracting;
	public String contracted;
	public String objectBriefDescription;
	@JsonDeserialize(using = ContractMoneyDeserializer.class)
	public BigDecimal initialContractualPrice;
	@JsonFormat(pattern = "dd-MM-yyyy")
	public Date signingDate;
	@Id
	public Integer id;

	public String getContractingProcedureType() {
		return contractingProcedureType;
	}

	public void setContractingProcedureType(String contractingProcedureType) {
		this.contractingProcedureType = contractingProcedureType;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getContracting() {
		return contracting;
	}

	public void setContracting(String contracting) {
		this.contracting = contracting;
	}

	public String getContracted() {
		return contracted;
	}

	public void setContracted(String contracted) {
		this.contracted = contracted;
	}

	public String getObjectBriefDescription() {
		return objectBriefDescription;
	}

	public void setObjectBriefDescription(String objectBriefDescription) {
		this.objectBriefDescription = objectBriefDescription;
	}

	public BigDecimal getInitialContractualPrice() {
		return initialContractualPrice;
	}

	public void setInitialContractualPrice(BigDecimal initialContractualPrice) {
		this.initialContractualPrice = initialContractualPrice;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}