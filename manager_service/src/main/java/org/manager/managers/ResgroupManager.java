package org.manager.managers;

import org.manager.dao.ResgroupDao;
import org.manager.models.Notification;
import org.manager.models.Profile;
import org.manager.models.Resgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResgroupManager {
    @Autowired
    private ResgroupDao resgroupDao;

    public Resgroup createResGroup(Resgroup resGroup) {
        // Check if the Resgroup already exists
        Resgroup existingResGroup = resgroupDao.findByName(resGroup.getName());
        if (existingResGroup != null) {
            // If the Resgroup already exists, return the existing one
            return existingResGroup;
        }

        // If the Resgroup doesn't exist, create a new one and save it
        return resgroupDao.create(resGroup);
    }

    public Resgroup updateResGroup(Long id, Resgroup updatedResGroup) {
        Resgroup existingResGroup = resgroupDao.findById(id);
        if (existingResGroup == null) {
            return null;
        }

        // Check if the Resgroup has been changed
        boolean hasChanged = false;
        if (!Objects.equals(existingResGroup.getName(), updatedResGroup.getName())) {
            existingResGroup.setName(updatedResGroup.getName());
            hasChanged = true;
        }
        if (!Objects.equals(existingResGroup.getDescription(), updatedResGroup.getDescription())) {
            existingResGroup.setDescription(updatedResGroup.getDescription());
            hasChanged = true;
        }

        // If the Resgroup has been changed, update it
        if (hasChanged) {
            return resgroupDao.update(existingResGroup);
        } else {
            return existingResGroup;
        }
    }

    public void deleteResgroup(Long id) {
        // Check if the Resgroup exists
        Resgroup existingResGroup = resgroupDao.findById(id);
        if (existingResGroup == null) {
            // If the Resgroup doesn't exist, return without doing anything
            return;
        }

        // If the Resgroup exists, delete it
        resgroupDao.delete(id);
    }

    public List<Resgroup> getAllResGroups(String nameFilter, String descriptionFilter) {
        // Construct the filter criteria
        Specification<Resgroup> specification = Specification.where(null);

        // Apply the name filter if provided
        if (StringUtils.isNotBlank(nameFilter)) {
            specification = specification.and(ResGroupSpecifications.nameContains(nameFilter));
        }

        // Apply the description filter if provided
        if (StringUtils.isNotBlank(descriptionFilter)) {
            specification = specification.and(ResGroupSpecifications.descriptionContains(descriptionFilter));
        }

        // Fetch all Resgroups that match the filter criteria
        return resgroupDao.findAll(specification);
    }

    public List<Notification> getNotificationsByResGroupId(Long id) {
        Resgroup resGroup = resgroupDao.findById(id);
        if (resGroup != null) {
            return resGroup.getNotifications();
        }
        return null;
    }

    public Profile createProfile(Long id, Profile profile) {
        Resgroup resGroup = resgroupDao.findById(id);
        if (resGroup != null) {
            profile.setResGroup(resGroup);
            return resgroupDao.createProfile(profile);
        }
        return null;
    }

    public List<Profile> getAllProfilesByResGroupId(Long id) {
        Resgroup resGroup = resgroupDao.findById(id);
        if (resGroup != null) {
            return resGroup.getProfiles();
        }
        return null;
    }

    public Profile updateProfile(Long id, Long profileId, Profile profile) {
        Resgroup resGroup = resgroupDao.findById(id);
        if (resGroup != null) {
            Profile existingProfile = resgroupDao.findProfileById(profileId);
            if (existingProfile != null) {
                existingProfile.setName(profile.getName());
                existingProfile.setEmail(profile.getEmail());
                existingProfile.setPhoneNumber(profile.getPhoneNumber());
                return resgroupDao.updateProfile(existingProfile);
            }
        }
        return null;
    }

    public void deleteProfile(Long id, Long profileId) {
        // Check if the Resgroup exists
        Resgroup existingResGroup = resgroupDao.findById(id);
        if (existingResGroup == null) {
            // If the Resgroup doesn't exist, return without doing anything
            return;
        }

        // Check if the Profile exists
        Profile existingProfile = existingResGroup.getProfiles().stream()
                .filter(p -> p.getId().equals(profileId))
                .findFirst()
                .orElse(null);
        if (existingProfile == null) {
            // If the Profile doesn't exist, return without doing anything
            return;
        }

        // If the Resgroup and Profile exist, delete the Profile
        existingResGroup.getProfiles().remove(existingProfile);
        resgroupDao.save(existingResGroup);
    }
}