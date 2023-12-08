package com.contact.contactmanagement.services.impl;

import com.contact.contactmanagement.entities.Contact;
import com.contact.contactmanagement.enums.ContactStatus;
import com.contact.contactmanagement.exceptions.ContactException;
import com.contact.contactmanagement.repositories.ContactRepository;
import com.contact.contactmanagement.services.ContactService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    public static final String UNABLE_TO_FIND_CONTACT_WITH_ID = "Unable to find contact with id ";

    private final ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact saveContact(Contact contact) {
        if(contact.getStatus().equals(ContactStatus.FREELANCER) && StringUtils.isBlank(contact.getVatNumber())){
            throw new ContactException(HttpStatus.UNPROCESSABLE_ENTITY, "Vat number is required  ");
        }
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if(contact.isPresent()){
            return  contact.get();
        }else throw new ContactException(HttpStatus.NOT_FOUND, UNABLE_TO_FIND_CONTACT_WITH_ID + id);
    }

    @Override
    public Contact updateContact(Long id, Contact contact) {
        Optional<Contact> existingContact = contactRepository.findById(id);
        if(existingContact.isPresent()){
            Contact updateContact = existingContact.get();
            updateContact.setFirstName(contact.getFirstName());
            updateContact.setLastName(contact.getLastName());
            updateContact.setAddress(contact.getAddress());
            updateContact.setStatus(contact.getStatus());
            contactRepository.save(updateContact);
            return updateContact;
        } else throw new ContactException(HttpStatus.NOT_FOUND, UNABLE_TO_FIND_CONTACT_WITH_ID + id);
    }

    @Override
    public void deleteContact(Long id) {
        Contact existingContact = contactRepository.findById(id).orElseThrow(()->new ContactException(HttpStatus.NOT_FOUND, UNABLE_TO_FIND_CONTACT_WITH_ID + id));
        contactRepository.delete(existingContact);
    }
}
