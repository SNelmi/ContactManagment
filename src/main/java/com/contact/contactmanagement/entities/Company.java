package com.contact.contactmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vatNumber;

    private String address;

    @JsonIgnore
    @ManyToMany(mappedBy = "companies")
    private Set<Contact> contacts = new HashSet<>();
}
