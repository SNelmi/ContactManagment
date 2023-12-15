package com.contact.contactmanagement.entities;

import com.contact.contactmanagement.enums.ContactStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "the first name is required can't be null")
    private String firstName;

    @NotBlank(message = "the last name is required can't be null")
    private String lastName;

    @NotBlank(message = "the address is required can't be null")
    private String address;

    private String vatNumber;

    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "contact_company",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private Set<Company> companies = new HashSet<>();

    public void addCompany(Company c){
        if(this.companies.isEmpty()){
            this.companies = new HashSet<>();
        }
        this.companies.add(c);
    }

}
