package com.basetwitter.basetwitter.models.contractdetails;

import com.basetwitter.basetwitter.util.ContractMoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Data
public class ContractDetails {
  @Id public int id;

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
  public boolean contractTypeCS;
  public String objectBriefDescription;
  public boolean income;
  public String centralizedProcedure;
  public String executionPlace;
  public String nonWrittenContractJustificationTypes;

  @JsonDeserialize(using = ContractMoneyDeserializer.class)
  public BigDecimal initialContractualPrice;

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
}
