package com.base.base.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "contracted")
public class Contracted {
  public String nif;
  public String description;
  @Id private int id;

  @ManyToMany(mappedBy = "contracted")
  private Set<ContractDetails> contractDetails;
}
