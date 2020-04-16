package com.example.todoapptask.todoapp.service;

import com.example.todoapptask.todoapp.exception.AccountAlreadyRegisteredException;
import com.example.todoapptask.todoapp.exception.WrongPasswordException;
import com.example.todoapptask.todoapp.model.User;
import com.example.todoapptask.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@DependsOn("passwordEncoderConfig")
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return optionalUser.get();
    }

    @Override
    public User getUser(String username) throws AccountNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new AccountNotFoundException("User not found.");
        }
        return optionalUser.get();
    }

    @Override
    public User login(String username, String password) throws AccountNotFoundException, WrongPasswordException {
        User user = getUser(username);
        String encodedPassword = user.getPassword();
        boolean successLogin = bCryptPasswordEncoder.matches(password, encodedPassword);
        if (!successLogin) {
            throw new WrongPasswordException("Invalid password");
        }
        user.generateToken(password);
        return user;
    }

    @Override
    public User register(User user) throws AccountAlreadyRegisteredException {
        String username = user.getUsername();
        Optional<User> storedUserOptional = userRepository.findByUsername(username);
        if (storedUserOptional.isPresent()) {
            throw new AccountAlreadyRegisteredException("User already registered!");
        }

        String rawPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setPassword(encodedPassword);

        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(User user) throws AccountNotFoundException {
        String username = user.getUsername();
        Optional<User> storedUserOptional = userRepository.findByUsername(username);
        if (!storedUserOptional.isPresent()) {
            throw new AccountNotFoundException("User not found!");
        }
        User storedUser = storedUserOptional.get();
        String storedUsername = storedUser.getUsername();

        String rawPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setUsername(storedUsername);
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return storedUser;
    }
}
