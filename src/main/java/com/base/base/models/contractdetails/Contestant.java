package com.base.base.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
  @Table(name = "contestants")
public class Contestant {

  @Id private int id;

  private String nif;

  private String description;

  @ManyToMany(mappedBy = "contestants")
  private Set<ContractDetails> contractDetails;
}
