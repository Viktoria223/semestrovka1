package ru.itis.servletsapp.services.impl;

import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.exceptions.NotFoundException;
//import ru.itis.servletsapp.sex;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional= usersRepository.findById(id);
        return userOptional.orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean isInMatch(Long userId, Long matchId) {
        List<Long> matches = usersRepository.findMatchesId(userId);

        return matches.contains(matchId);
    }

    @Override
    public void setDislike(Long userId, Long matchId) {
        setInteracted(userId, matchId);
    }

    @Override
    public User getPair(Long id) {
        Optional<User> pair = usersRepository.getPair(id);
        return pair.orElse(null);
    }

    @Override
    public void deleteToken(Long id) {
        usersRepository.deleteToken(id);
    }

    @Override
    public void setMatch(Long userId, Long matchId) {
        setInteracted(userId, matchId);
        usersRepository.setMatch(userId, matchId);
    }

    private void setInteracted(Long userId, Long matchId){
        usersRepository.setInteractedWith(userId, matchId);
    }
}