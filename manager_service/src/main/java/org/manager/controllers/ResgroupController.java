package org.manager.controllers;

import org.manager.managers.ResgroupManager;
import org.manager.models.Notification;
import org.manager.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resgroups")
public class ResgroupController {

    @Autowired
    private ResgroupManager resgroupService;


    @PostMapping
    public ResponseEntity<Resgroup> createResGroup(@RequestBody Resgroup resGroup) {
        Resgroup createdResGroup = resgroupService.createResGroup(resGroup);
        return new ResponseEntity<>(createdResGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resgroup> updateResGroup(@PathVariable Long id, @RequestBody Resgroup resGroup) {
        Resgroup updatedResGroup = resgroupService.updateResGroup(id, resGroup);
        return new ResponseEntity<>(updatedResGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResGroup(@PathVariable Long id) {
        resgroupService.deleteResgroup(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Resgroup>> getAllResGroups() {
        List<Resgroup> resGroups = resgroupService.getAllResGroups();
        return new ResponseEntity<>(resGroups, HttpStatus.OK);
    }

    @GetMapping("/{id}/notifications")
    public ResponseEntity<List<Notification>> getNotificationsByResGroupId(@PathVariable Long id) {
        List<Notification> notifications = resgroupService.getNotificationsByResGroupId(id);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/{id}/profiles")
    public ResponseEntity<Profile> createProfile(@PathVariable Long id, @RequestBody Profile profile) {
        Profile createdProfile = resgroupService.createProfile(id, profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/profiles/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id, @PathVariable Long profileId) {
        resgroupService.deleteProfile(id, profileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/profiles/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @PathVariable Long profileId, @RequestBody Profile profile) {
        Profile updatedProfile = resgroupService.updateProfile(id, profileId, profile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getAllProfilesByResGroupId(@PathVariable Long id) {
        List<Profile> profiles = resgroupService.getAllProfilesByResGroupId(id);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
}
