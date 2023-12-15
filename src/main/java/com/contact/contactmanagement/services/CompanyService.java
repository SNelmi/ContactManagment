package com.contact.contactmanagement.services;

import com.contact.contactmanagement.entities.Company;
import com.contact.contactmanagement.entities.Contact;

import java.util.List;
import java.util.Set;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company saveCompany(Company company);

    Company getCompanyByVATNumber(String vatNumber);

    Company updateCompany(Long id, Company company);

    void addContactToCompany(Long companyId, Long contactId);

    Set<Contact> getCompanyContacts(Long id);
}
