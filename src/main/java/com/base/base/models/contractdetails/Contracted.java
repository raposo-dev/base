package com.base.base.models.contractdetails;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contracted")
public class Contracted {
    public String nif;

    public Set<ContractDetails> getContractDetails() {
        return contractDetails;
    }

    public void setContractDetails(Set<ContractDetails> contractDetails) {
        this.contractDetails = contractDetails;
    }

    public String description;
    @Id
    private int id;

    @ManyToMany(mappedBy = "contracted")
    private Set<ContractDetails> contractDetails;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
