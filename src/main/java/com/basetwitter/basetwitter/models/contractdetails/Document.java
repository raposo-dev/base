package com.basetwitter.basetwitter.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "documents")
public class Document {
  public String description;
  @Id private int id;
  @ManyToMany(mappedBy = "documents")
  private Set<ContractDetails> contractDetails;
}
