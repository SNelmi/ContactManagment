package com.contact.contactmanagement.repositories;

import com.contact.contactmanagement.entities.Company;
import com.contact.contactmanagement.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByVatNumber(String vatNumber);

    @Query("select c.contacts from Company c where c.id = :companyId ")
    Set<Contact> findCompanyContacts(Long companyId);



}
