package org.rayshan.workflow.service;

import org.rayshan.workflow.entity.User;
import org.rayshan.workflow.exception.AppException;
import org.rayshan.workflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws AppException {
        Optional<User> exitingUser = userRepository.findById(id);
        if(exitingUser.isPresent()) {
            return exitingUser.get();
        } else {
            throw new AppException("User not found with id: " + id, 404);
        }
    }

    public User getUserByEmail(String email) throws AppException {
        Optional<User> exitingUser = userRepository.findByEmail(email);
        if(exitingUser.isPresent()) {
            return exitingUser.get();
        } else {
            throw new AppException("User not found with email: " + email, 404);
        }
    }

    public User createUser(User user) throws AppException {
        Optional<User> exitingUser = userRepository.findByEmail(user.getEmail());
        if(exitingUser.isPresent()){
            throw new AppException("User already exists with email: " + user.getEmail(), 409);
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) throws AppException {
        Optional<User> exitingUser = userRepository.findById(id);
        if(exitingUser.isPresent()) {
            User dbUser = exitingUser.get();
            dbUser.setName(userDetails.getName());
            dbUser.setEmail(userDetails.getEmail());
            return userRepository.save(dbUser);
        } else {
            throw new AppException("User not found with id: " + id, 404);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
