package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.Profile;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController
{

    private ProfileService profileService;
    private UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal)
    {
        principal.getName(); //gets current name of the user

        if(principal.getName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private int currentUserId(Principal principal)
    {
        return userService.getByUserName(principal.getName()).getId();
    }
}


