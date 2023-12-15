package com.contact.contactmanagement;

import com.contact.contactmanagement.entities.Company;
import com.contact.contactmanagement.entities.Contact;
import com.contact.contactmanagement.enums.ContactStatus;
import com.contact.contactmanagement.repositories.CompanyRepository;
import com.contact.contactmanagement.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ContactManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner run(@Autowired CompanyRepository companyRepository, @Autowired ContactRepository contactRepository){
		return args -> {
			Contact employee = Contact.builder().firstName("John").lastName("Dab").address("Bruxelles").status(ContactStatus.EMPLOYEE).build();
			Contact freelancer = Contact.builder().firstName("John2").lastName("Dab2").address("Charleroi").vatNumber("VT21345").status(ContactStatus.FREELANCER).build();
			contactRepository.saveAll(List.of(employee, freelancer));

			Company c = Company.builder().vatNumber("VTA54621").address("Bruxelles").build();
			companyRepository.save(c);
		};
	}
}
