package org.yearup.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;
import java.util.Optional;

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
    public ResponseEntity<Optional<Profile>> getProfile(Principal principal)
    {
        //principal.getName(); //gets current name of the user

        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //make a variable to hold the user's profile ID
        int userId = currentUserId(principal);

        Optional<Profile> profileOptional = profileService.getProfile(userId);

        if(profileOptional.isPresent()){

            return new ResponseEntity<>(profileOptional, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(@RequestBody @Valid Profile profile,
                                                 Principal principal){

        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        int userId = currentUserId(principal);
        Profile updatedProfile = this.profileService.update(userId, profile);

        if(updatedProfile == null){
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


