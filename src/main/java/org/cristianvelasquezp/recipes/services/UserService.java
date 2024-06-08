package org.cristianvelasquezp.recipes.services;

import org.cristianvelasquezp.recipes.entities.User;
import org.cristianvelasquezp.recipes.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String username) {
        return userRepository.findByEmail(username).get(0);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
