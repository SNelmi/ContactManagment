package com.contact.contactmanagement;

import com.contact.contactmanagement.entities.Contact;
import com.contact.contactmanagement.enums.ContactStatus;
import com.contact.contactmanagement.exceptions.ContactException;
import com.contact.contactmanagement.repositories.ContactRepository;
import com.contact.contactmanagement.services.impl.ContactServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactServiceUnitTest {

    @InjectMocks
    public ContactServiceImpl contactService;

    @Mock
    public ContactRepository contactRepository;

    @Test
    void testGetAllContacts(){
        //Given
        Contact firstContact = Contact.builder().firstName("John").lastName("Dohe").address("Bruxelle").status(ContactStatus.EMPLOYEE).build();
        Contact secondContact = Contact.builder().firstName("Jane").lastName("Maddi").address("Charleroi").status(ContactStatus.EMPLOYEE).build();
        List<Contact> contactList = Arrays.asList(firstContact, secondContact);
        //when
        Mockito.when(contactRepository.findAll()).thenReturn(contactList);
        //get
        List<Contact> result = contactService.getAllContacts();
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("John", result.get(0).getFirstName());
        Assertions.assertEquals("Jane", result.get(1).getFirstName());
    }
    @Test
    void testCreateContact(){
        //Given
        Contact contact = Contact.builder().firstName("John").lastName("Dohe").address("Bruxelle").status(ContactStatus.EMPLOYEE).build();
        //when
        Mockito.when(contactRepository.save(any(Contact.class))).thenReturn(contact);
        //save
        Contact result = contactService.saveContact(contact);
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.getFirstName());
    }

    @Test
    void testCreateContactThrowsException(){
        //Given
        Contact contact = Contact.builder().firstName("John").lastName("Dohe").address("Bruxelles").status(ContactStatus.FREELANCER).build();
        //Assert
        Assertions.assertThrows(ContactException.class, ()->contactService.saveContact(contact));
    }

    @Test
    void testGetContactById(){
        //Given
        Long contactId = 1L;
        Contact contact = Contact.builder().firstName("John").lastName("Dohe").address("Charleroi").vatNumber("TV456").status(ContactStatus.FREELANCER).build();
        //When
        Mockito.when(contactRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(contact));
        //get
        Contact result = contactService.getContactById(contactId);
        //Assert
        verify(contactRepository, times(1)).findById(any(Long.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.getFirstName());

    }

    @Test
    void testUpdateContact(){
        //Given
        Long contactId = 1L;
        Contact updatedContact = Contact.builder().firstName("John").lastName("Dohe").address("Charleroi").vatNumber("TV456").status(ContactStatus.FREELANCER).build();
        //When
        Mockito.when(contactRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(updatedContact));
        Mockito.when(contactRepository.save(any(Contact.class))).thenReturn(updatedContact);
        //update
        Contact result = contactService.updateContact(contactId, updatedContact);
        //assert
        verify(contactRepository, times(1)).save(any(Contact.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.getFirstName());
        Assertions.assertEquals("Charleroi", result.getAddress());
    }

    @Test
    void testDeleteContact(){
        //Given
        Long contactId = 1L;
        Contact contact = Contact.builder().firstName("John").lastName("Dohe").address("Bruxelle").status(ContactStatus.EMPLOYEE).build();
        //When
        Mockito.when(contactRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(contact));
        //Delete
        contactService.deleteContact(contactId);
        //Assert
        verify(contactRepository, times(1)).findById(contactId);
        verify(contactRepository, times(1)).delete(contact);
    }
}
