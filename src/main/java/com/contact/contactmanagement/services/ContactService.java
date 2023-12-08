package com.contact.contactmanagement.services;

import com.contact.contactmanagement.entities.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContacts();

    Contact saveContact(Contact contact);

    Contact getContactById(Long id);

    Contact updateContact(Long id, Contact contact);

    void deleteContact(Long id);
}
