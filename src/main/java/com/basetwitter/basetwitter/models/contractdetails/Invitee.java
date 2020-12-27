package com.basetwitter.basetwitter.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "invitees")
public class Invitee {
  public String nif;
  public String description;

  @Id private int id;

  @ManyToMany(mappedBy = "invitees")
  private Set<ContractDetails> contractDetails;
}
