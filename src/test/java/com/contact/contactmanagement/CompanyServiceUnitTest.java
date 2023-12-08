package com.contact.contactmanagement;

import com.contact.contactmanagement.entities.Company;
import com.contact.contactmanagement.entities.Contact;
import com.contact.contactmanagement.enums.ContactStatus;
import com.contact.contactmanagement.repositories.CompanyRepository;
import com.contact.contactmanagement.services.impl.CompanyServiceImpl;
import com.contact.contactmanagement.services.impl.ContactServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyServiceUnitTest {

    @InjectMocks
    CompanyServiceImpl companyService;

    @Mock
    CompanyRepository companyRepository;
    @Mock
    ContactServiceImpl contactService;

    @Test
    void testGetAllCompanies(){
        //Given
        Company firstCompany = Company.builder().address("Bruxelles").vatNumber("VT45631").build();
        Company secondCompany = Company.builder().address("Charleroi").vatNumber("VA7896").build();
        List<Company> companies = Arrays.asList(firstCompany, secondCompany);
        //when
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        //get
        List<Company> result = companyService.getAllCompanies();
        //Assert
        verify(companyRepository, times(1)).findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

    }

    @Test
    void testCreateCompany(){
        //Given
        Company company = Company.builder().address("Bruxelles").vatNumber("VT45631").build();
        //when
        Mockito.when(companyRepository.save(any(Company.class))).thenReturn(company);
        //get
        Company result = companyService.saveCompany(company);
        //Assert
        verify(companyRepository, times(1)).save(any(Company.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("VT45631", result.getVatNumber());
    }

    @Test
    void testGetCompanyByVatNumber(){
        //Given
        String vatNumber = "VAT123789";
        Company company = Company.builder().address("Bruxelles").vatNumber("VAT123789").build();
        //when
        Mockito.when(companyRepository.findByVatNumber(any(String.class))).thenReturn(Optional.ofNullable(company));
        //get
        Company result = companyService.getCompanyByVATNumber(vatNumber);
        //Assert
        verify(companyRepository, times(1)).findByVatNumber(any(String.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("VAT123789", result.getVatNumber());
    }

    @Test
    void testUpdateCompany(){
        //Given
        Long id = 1L;
        Company updatedCompany = Company.builder().address("Charleroi").vatNumber("VAT123789").build();
        //when
        Mockito.when(companyRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(updatedCompany));
        Mockito.when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);
        //update
        Company result = companyService.updateCompany(id, updatedCompany);
        //assert
        verify(companyRepository, times(1)).save(any(Company.class));
        verify(companyRepository, times(1)).findById(any(Long.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("VAT123789", result.getVatNumber());
        Assertions.assertEquals("Charleroi", result.getAddress());

    }

    @Test
    void testAddContactToCompany(){
        //Given
        Long companyId = 1L;
        Company company = Company.builder().address("Charleroi").vatNumber("VAT123789").contacts(new HashSet<>()).build();
        Long contactId = 1L;
        Contact contact = Contact.builder().firstName("John").lastName("Dohe").address("Charleroi").vatNumber("TV456").status(ContactStatus.FREELANCER).build();
        //when
        Mockito.when(companyRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(company));
        Mockito.when(companyRepository.save(any(Company.class))).thenReturn(company);
        Mockito.when(contactService.getContactById(any(Long.class))).thenReturn(contact);
        //add
        companyService.addContactToCompany(companyId, contactId);
        //Assert
        verify(companyRepository, times(1)).save(any(Company.class));
        verify(companyRepository, times(1)).findById(any(Long.class));

    }

}
