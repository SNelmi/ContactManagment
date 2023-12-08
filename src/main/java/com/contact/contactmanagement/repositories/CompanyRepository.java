package com.contact.contactmanagement.repositories;

import com.contact.contactmanagement.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByVatNumber(String vatNumber);
}
