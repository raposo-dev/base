package com.base.base.models;

import com.base.base.util.ContractMoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Contracts {
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
}