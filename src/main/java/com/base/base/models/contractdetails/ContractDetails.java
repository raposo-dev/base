package com.base.base.models.contractdetails;

import com.base.base.util.ContractMoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"groupMembers"})
public class ContractDetails {
	@Id
	public int id;

	public String description;
	public String frameworkAgreementProcedureId;

	public boolean ambientCriteria;
	public int announcementId;
	public String directAwardFundamentationType;
	public String contractingProcedureUrl;
	public String observations;

	@JsonFormat(pattern = "dd-MM-yyyy")
	public Date publicationDate;

	public String endOfContractType;

	@JsonDeserialize(using = ContractMoneyDeserializer.class)
	public BigDecimal totalEffectivePrice;

	public String contractFundamentationType;
	public String frameworkAgreementProcedureDescription;
	public String causesDeadlineChange;
	public String causesPriceChange;

	@JsonFormat(pattern = "dd-MM-yyyy")
	public Date closeDate;

	public boolean increments;
	public String contractingProcedureType;
	public String contractTypes;
	public String executionDeadline;
	public String cpvs;
	public String cpvsType;

	public String cpvsDesignation;
	public boolean contractTypeCS;
	public String objectBriefDescription;
	public boolean income;
	public String centralizedProcedure;
	public String executionPlace;
	public String nonWrittenContractJustificationTypes;

	public String cpvsValue;
	@JsonDeserialize(using = ContractMoneyDeserializer.class)
	public BigDecimal initialContractualPrice;

	public Boolean aquisitionStateMemberUE;

	public String infoAquisitionStateMemberUE;
	public String contractStatus;

	@JsonFormat(pattern = "dd-MM-yyyy")
	public Date signingDate;

	public boolean cocontratantes;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable
	public List<Contestant> contestants;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable
	public List<Invitee> invitees;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable
	public List<Document> documents;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable
	public List<Contracting> contracting;
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable
	public List<Contracted> contracted;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrameworkAgreementProcedureId() {
		return frameworkAgreementProcedureId;
	}

	public void setFrameworkAgreementProcedureId(String frameworkAgreementProcedureId) {
		this.frameworkAgreementProcedureId = frameworkAgreementProcedureId;
	}

	public boolean isAmbientCriteria() {
		return ambientCriteria;
	}

	public void setAmbientCriteria(boolean ambientCriteria) {
		this.ambientCriteria = ambientCriteria;
	}

	public int getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(int announcementId) {
		this.announcementId = announcementId;
	}

	public String getDirectAwardFundamentationType() {
		return directAwardFundamentationType;
	}

	public void setDirectAwardFundamentationType(String directAwardFundamentationType) {
		this.directAwardFundamentationType = directAwardFundamentationType;
	}

	public String getContractingProcedureUrl() {
		return contractingProcedureUrl;
	}

	public void setContractingProcedureUrl(String contractingProcedureUrl) {
		this.contractingProcedureUrl = contractingProcedureUrl;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getEndOfContractType() {
		return endOfContractType;
	}

	public void setEndOfContractType(String endOfContractType) {
		this.endOfContractType = endOfContractType;
	}

	public BigDecimal getTotalEffectivePrice() {
		return totalEffectivePrice;
	}

	public void setTotalEffectivePrice(BigDecimal totalEffectivePrice) {
		this.totalEffectivePrice = totalEffectivePrice;
	}

	public String getContractFundamentationType() {
		return contractFundamentationType;
	}

	public void setContractFundamentationType(String contractFundamentationType) {
		this.contractFundamentationType = contractFundamentationType;
	}

	public String getFrameworkAgreementProcedureDescription() {
		return frameworkAgreementProcedureDescription;
	}

	public void setFrameworkAgreementProcedureDescription(String frameworkAgreementProcedureDescription) {
		this.frameworkAgreementProcedureDescription = frameworkAgreementProcedureDescription;
	}

	public String getCausesDeadlineChange() {
		return causesDeadlineChange;
	}

	public void setCausesDeadlineChange(String causesDeadlineChange) {
		this.causesDeadlineChange = causesDeadlineChange;
	}

	public String getCausesPriceChange() {
		return causesPriceChange;
	}

	public void setCausesPriceChange(String causesPriceChange) {
		this.causesPriceChange = causesPriceChange;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public boolean isIncrements() {
		return increments;
	}

	public void setIncrements(boolean increments) {
		this.increments = increments;
	}

	public String getContractingProcedureType() {
		return contractingProcedureType;
	}

	public void setContractingProcedureType(String contractingProcedureType) {
		this.contractingProcedureType = contractingProcedureType;
	}

	public String getContractTypes() {
		return contractTypes;
	}

	public void setContractTypes(String contractTypes) {
		this.contractTypes = contractTypes;
	}

	public String getExecutionDeadline() {
		return executionDeadline;
	}

	public void setExecutionDeadline(String executionDeadline) {
		this.executionDeadline = executionDeadline;
	}

	public String getCpvs() {
		return cpvs;
	}

	public void setCpvs(String cpvs) {
		this.cpvs = cpvs;
	}

	public boolean isContractTypeCS() {
		return contractTypeCS;
	}

	public void setContractTypeCS(boolean contractTypeCS) {
		this.contractTypeCS = contractTypeCS;
	}

	public String getObjectBriefDescription() {
		return objectBriefDescription;
	}

	public void setObjectBriefDescription(String objectBriefDescription) {
		this.objectBriefDescription = objectBriefDescription;
	}

	public boolean isIncome() {
		return income;
	}

	public void setIncome(boolean income) {
		this.income = income;
	}

	public String getCentralizedProcedure() {
		return centralizedProcedure;
	}

	public void setCentralizedProcedure(String centralizedProcedure) {
		this.centralizedProcedure = centralizedProcedure;
	}

	public String getExecutionPlace() {
		return executionPlace;
	}

	public void setExecutionPlace(String executionPlace) {
		this.executionPlace = executionPlace;
	}

	public String getNonWrittenContractJustificationTypes() {
		return nonWrittenContractJustificationTypes;
	}

	public void setNonWrittenContractJustificationTypes(String nonWrittenContractJustificationTypes) {
		this.nonWrittenContractJustificationTypes = nonWrittenContractJustificationTypes;
	}

	public BigDecimal getInitialContractualPrice() {
		return initialContractualPrice;
	}

	public void setInitialContractualPrice(BigDecimal initialContractualPrice) {
		this.initialContractualPrice = initialContractualPrice;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public boolean isCocontratantes() {
		return cocontratantes;
	}

	public void setCocontratantes(boolean cocontratantes) {
		this.cocontratantes = cocontratantes;
	}

	public List<Contestant> getContestants() {
		return contestants;
	}

	public void setContestants(List<Contestant> contestants) {
		this.contestants = contestants;
	}

	public List<Invitee> getInvitees() {
		return invitees;
	}

	public void setInvitees(List<Invitee> invitees) {
		this.invitees = invitees;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<Contracting> getContracting() {
		return contracting;
	}

	public void setContracting(List<Contracting> contracting) {
		this.contracting = contracting;
	}

	public List<Contracted> getContracted() {
		return contracted;
	}

	public void setContracted(List<Contracted> contracted) {
		this.contracted = contracted;
	}

	public String getCpvsType() {
		return cpvsType;
	}

	public void setCpvsType(String cpvsType) {
		this.cpvsType = cpvsType;
	}

	public String getCpvsDesignation() {
		return cpvsDesignation;
	}

	public void setCpvsDesignation(String cpvsDesignation) {
		this.cpvsDesignation = cpvsDesignation;
	}

	public String getCpvsValue() {
		return cpvsValue;
	}

	public void setCpvsValue(String cpvsValue) {
		this.cpvsValue = cpvsValue;
	}

	public Boolean getAquisitionStateMemberUE() {
		return aquisitionStateMemberUE;
	}

	public void setAquisitionStateMemberUE(Boolean aquisitionStateMemberUE) {
		this.aquisitionStateMemberUE = aquisitionStateMemberUE;
	}

	public String getInfoAquisitionStateMemberUE() {
		return infoAquisitionStateMemberUE;
	}

	public void setInfoAquisitionStateMemberUE(String infoAquisitionStateMemberUE) {
		this.infoAquisitionStateMemberUE = infoAquisitionStateMemberUE;
	}
}
