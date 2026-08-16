package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Service.UserDetailsImpl;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static net.engineeringdigest.journalApp.controller.UserControllerV2.passwordEncoder;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    public UserService userService;
    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    public UserDetailsImpl userDetailsImpl;
    @Autowired
    public JwtUtil jwtUtil;
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
    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.saveNewUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsImpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return  new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to authenticate and get JWT token");
            throw new RuntimeException(e);

        }
    }
}
