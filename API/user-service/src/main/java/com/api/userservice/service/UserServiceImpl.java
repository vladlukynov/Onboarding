package com.api.userservice.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.userservice.DTO.EditUserBean;
import com.api.userservice.DTO.RegisterBean;
import com.api.userservice.DTO.UserPageWithoutEmail;
import com.api.userservice.DTO.UserProfile;
import com.api.userservice.model.User;
import com.api.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterBean register) {
        User newUser = new User();
        newUser.setName(register.getName());
        newUser.setEmail(register.getEmail());
        newUser.setPassword(passwordEncoder.encode(register.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public boolean isCorrectPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void updateDate(String email, String date){
        userRepository.updateDateStartWorkByEmailEquals(email, date);
    }

    @Override
    public void updateTeam(String email, Long team){
        userRepository.updateTeamIdByEmailEquals(team, email);
    }

    @Override
    public void updatePost(String email, Long post){
        userRepository.updatePostIdByEmailEquals(post, email);
    }

    @Override
    public void changePassword(String password, User user) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public boolean isEmailExist(String email) {
        List<User> users = userRepository.findUsersByEmail(email);
        for (User user : users) {
            if (user.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user, EditUserBean editUserBean) {
        user.setName(editUserBean.getName());
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        List<User> users = userRepository.findUsersByEmail(email);
        for (User u : users) {
            if (u.isEnabled()) {
                return u;
            }
        }
        throw new EntityNotFoundException("User not found.");
    }

    @Override
    public void setLinkPhoto(String linkPhoto, Long userId) {
        userRepository.updateLinkPhotoByUserId(linkPhoto, userId);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new EntityNotFoundException("User not found.");
    }

    @Override
    public void deleteAllUsersExceptVerified(String email) {
        userRepository.deleteAllUsersExceptVerified(email);
    }

    @Override
    public UserProfile getInfoForProfile(User user) {
        UserProfile userProfile = new UserProfile(user);
        userProfile.setImage("/user/image?id=" + user.getId());
        return userProfile;
    }

    @Override
    public UserPageWithoutEmail getInfoForUserPageWithoutEmail(User user) {
        UserPageWithoutEmail userPageWithoutEmail = new UserPageWithoutEmail(user);
        userPageWithoutEmail.setImage("/user/image?id=" + user.getId());
        return userPageWithoutEmail;
    }
}
