package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static net.engineeringdigest.journalApp.controller.UserControllerV2.passwordEncoder;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    public UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public boolean saveNewUser(@RequestBody User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userService.saveEntry(user);
            return true;
        } catch (Exception e) {
            log.trace("The User is already saved");
            log.debug("The User is already saved");
            log.info("The User is already saved");

            log.warn("The User is already saved");
            log.error("Error Occurred {}:",user.getUserName(),e);
            return false;
        }
    }
}
