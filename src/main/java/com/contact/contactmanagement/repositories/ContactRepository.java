package com.contact.contactmanagement.repositories;

import com.contact.contactmanagement.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
