package com.base.base.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "contracting")
public class Contracting {

    public String nif;

    @Id
    private int id;

    private String description;

    @ManyToMany(mappedBy = "contracting")
    private Set<ContractDetails> contractDetails;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

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

    public Set<ContractDetails> getContractDetails() {
        return contractDetails;
    }

    public void setContractDetails(Set<ContractDetails> contractDetails) {
        this.contractDetails = contractDetails;
    }
}
