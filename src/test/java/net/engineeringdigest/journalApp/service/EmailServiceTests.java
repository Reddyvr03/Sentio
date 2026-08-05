package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest(){
        emailService.sendEmail("vijaykumarynh7@gmail.com","Testing java mail sender","ela unnav ra");
    }
}
