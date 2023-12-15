package com.contact.contactmanagement.repositories;

import com.contact.contactmanagement.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Set<Contact> findContactsByCompanies_Id(Long id);


}
