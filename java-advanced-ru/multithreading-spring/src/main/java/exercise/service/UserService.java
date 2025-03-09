package exercise.service;

import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    // BEGIN
    public Mono<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> updateUserById(Long userId, User data) {
        return userRepository.findById(userId).flatMap(u -> {
            data.setId(u.getId());
            return userRepository.save(data);
        });
    }

    public Mono<Void> deleteUserById(Long userId) {
        return userRepository.deleteById(userId);
    }
    // END
}
