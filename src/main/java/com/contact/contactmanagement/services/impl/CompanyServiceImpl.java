package com.contact.contactmanagement.services.impl;

import com.contact.contactmanagement.entities.Company;
import com.contact.contactmanagement.entities.Contact;
import com.contact.contactmanagement.exceptions.CompanyException;
import com.contact.contactmanagement.repositories.CompanyRepository;
import com.contact.contactmanagement.services.CompanyService;
import com.contact.contactmanagement.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final ContactService contactService;
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyByVATNumber(String vatNumber) {
        Optional<Company> company = companyRepository.findByVatNumber(vatNumber);
        if(company.isPresent()){
            return  company.get();
        }else throw new CompanyException(HttpStatus.NOT_FOUND, "Unable to find company with VAT Number: " + vatNumber);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if(existingCompany.isPresent()){
            Company updateCompany = existingCompany.get();
            updateCompany.setAddress(company.getAddress());
            updateCompany.setVatNumber(company.getVatNumber());
            companyRepository.save(updateCompany);
            return updateCompany;
        }else throw new CompanyException(HttpStatus.NOT_FOUND, "Unable to find company with Id: " + id);
    }

    @Override
    public void addContactToCompany(Long companyId, Long contactId) {
        Optional<Company> existingCompany = companyRepository.findById(companyId);
        Contact existingContact = contactService.getContactById(contactId);
        if(existingCompany.isPresent()){
            Company updateCompany = existingCompany.get();
            updateCompany.addContact(existingContact);
            companyRepository.save(updateCompany);
        }else throw new CompanyException(HttpStatus.BAD_REQUEST, "Cannot add contact to company, unable to find company with id: " + companyId);

    }

    @Override
    public Set<Contact> getCompanyContacts(Long id) {
        return companyRepository.findCompanyContacts(id);
    }
}
