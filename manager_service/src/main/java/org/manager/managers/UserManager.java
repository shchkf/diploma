package org.manager.managers;

import org.manager.dao.ResgroupDao;
import org.manager.dao.UserDao;
import org.manager.models.Resgroup;
import org.manager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManager {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private ResgroupDao resGroupRepository;

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User blockUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setBlocked(true);
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User moveUserToResGroup(Long id, Long rgId) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Optional<Resgroup> optionalResGroup = resGroupRepository.findById(rgId);
            if (optionalResGroup.isPresent()) {
                User existingUser = optionalUser.get();
                Resgroup resGroup = optionalResGroup.get();
                existingUser.setResGroup(resGroup);
                return userRepository.save(existingUser);
            }
        }
        return null;
    }
}