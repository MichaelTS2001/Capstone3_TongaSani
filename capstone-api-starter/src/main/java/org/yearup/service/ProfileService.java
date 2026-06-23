package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfile(int userId){
        return profileRepository.findById(userId);
    }

    public Profile update(int id, Profile profile){
        Optional<Profile> updateProfile = profileRepository.findByUserId(id);

        if(updateProfile.isEmpty()){
            return null;
        }

        Profile profileToUpdate = updateProfile.get();

        profileToUpdate.setFirstName(profile.getFirstName());
        profileToUpdate.setLastName(profile.getLastName());
        profileToUpdate.setPhone(profile.getPhone());
        profileToUpdate.setEmail(profile.getEmail());
        profileToUpdate.setAddress(profile.getAddress());
        profileToUpdate.setCity(profile.getCity());
        profileToUpdate.setState(profile.getState());
        profileToUpdate.setZip(profile.getZip());

        profileRepository.save(profileToUpdate);

        return profileToUpdate;

    }
}
