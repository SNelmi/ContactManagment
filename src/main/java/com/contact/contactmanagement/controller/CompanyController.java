package com.contact.contactmanagement.controller;

import com.contact.contactmanagement.entities.Company;
import com.contact.contactmanagement.services.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping()
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK) ;
    }

    //@ResponseStatus()
    @PostMapping()
    public ResponseEntity<Company> createCompany(@RequestBody @Validated Company company) {
        Company savedCompany = companyService.saveCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{vatNumber}")
    public ResponseEntity<Company> getCompanyByVATNumber(@PathVariable String vatNumber) {
        Company company = companyService.getCompanyByVATNumber(vatNumber);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody @Validated Company company){
        Company updatedCompany = companyService.updateCompany(id, company);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @PatchMapping("/{companyId}/contacts/{contactId}")
    public ResponseEntity<Void> addContactToCompany(@PathVariable Long companyId, @PathVariable Long contactId){
        companyService.addContactToCompany(companyId, contactId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
