package com.basetwitter.basetwitter.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "contracting")
public class Contracting {
  public String nif;

  @Id private int id;

  private String description;

  @ManyToMany(mappedBy = "contracting")
  private Set<ContractDetails> contractDetails;
}
