package com.api.userservice.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.userservice.DTO.EditUserBean;
import com.api.userservice.model.User;
import com.api.userservice.security.jwt.JwtService;
import com.api.userservice.service.NotificationService;
import com.api.userservice.service.RefreshTokenService;
import com.api.userservice.service.UserService;
import com.api.userservice.service.UserTokenService;
import com.api.userservice.utils.AppError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class AuthUserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private NotificationService notificationService;

    private String pattern = "dd/MM/yyyy";

    private DateFormat df = new SimpleDateFormat(pattern);

    @RequestMapping(path = "/check-password", method = RequestMethod.GET)
    public ResponseEntity<?> checkPassword(@RequestParam String password, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        Map<String, Boolean> map = new HashMap<>();
        map.put("isCorrectPassword", userService.isCorrectPassword(user, password));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(path = "/change-password", method = RequestMethod.GET)
    public ResponseEntity<?> changePassword(@RequestParam String newPassword,
                                            @RequestParam(required = false) String oldPassword,
                                            @RequestParam String refreshToken,
                                            HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        if (oldPassword != null) {
            if (!userService.isCorrectPassword(user, oldPassword)) {
                return new ResponseEntity<>(
                        new AppError(HttpStatus.NOT_FOUND.value(),
                                "Wrong old password!"), HttpStatus.NOT_FOUND);
            }
        }
        userTokenService.deleteAllAccessTokensByUser(user);
        refreshTokenService.deleteAllRefreshTokensByUser(user);
        userTokenService.addAccessToken(user, resolveToken(request));
        refreshTokenService.addRefreshToken(user, refreshToken);
        userService.changePassword(newPassword, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        return new ResponseEntity<>(userService.getInfoForProfile(user), HttpStatus.OK);
    }

    @RequestMapping(path = "/get-post-by-id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getPostByUserId(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(userService.findPostByUserId(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/colleagues", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getColleagues(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        if (user.getTeam() == null) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.CONFLICT.value(),
                            "You are not working"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.getColleagues(user.getId(), user.getTeam().getId()), HttpStatus.OK);
    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user;
        try {
            user = userService.getUserById(id);
            if (!user.isEnabled()) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(
                        new AppError(HttpStatus.NOT_FOUND.value(),
                                "User with id " + id + " does not exist."), httpHeaders, HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException exception) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "User with id " + id + " does not exist."), httpHeaders, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getInfoForProfile(user), HttpStatus.OK);
    }

    @RequestMapping(path = "/add-worker", method = RequestMethod.GET)
    public ResponseEntity<?> addWorker(@RequestParam(value = "email") String email,
                                       @RequestParam("newPost") Long post,
                                       @RequestParam("newCommand") Long command, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        if ((user == null) || (user.getPost() == null) || (user.getPost().getId() != 1)) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Not enough rights to execute"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        try {
            userService.updateDate(email, date);
            userService.updatePost(email, post);
            userService.updateTeam(email, command);
            User worker = userService.findUserByEmail(email);
            String content = "???? ???????? ?????????????????? ?? ?????????????? \"" + worker.getTeam() + "\" ???? ?????????????????? \"" + worker.getPost() + "\".";
            notificationService.addNewNotification(content, worker);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/edit-profile", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces = "application/json")
    public ResponseEntity<?> editProfile(@RequestPart String editUserBeanString,
                                         @RequestPart(required = false) MultipartFile file,
                                         HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        EditUserBean editUserBean;
        try {
            editUserBean = mapper.readValue(editUserBeanString, EditUserBean.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to convert JSON object"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int photoVersion = 0;
        try {
            if (file != null) {
                String oldLink = user.getLinkPhoto();
                if (oldLink != null) {
                    Scanner sc = new Scanner(oldLink);
                    sc.useDelimiter("\\.");
                    if (sc.hasNextInt()) {
                        sc.nextInt();
                        photoVersion = sc.nextInt();
                    }
                    photoVersion++;
                }
                String path = new File("").getAbsolutePath();
                File newFile = new File(path + user.getId() + "." + photoVersion + ".jpg");
                file.transferTo(newFile);
                File oldPhoto = new File(path + user.getId() + "." + --photoVersion + ".jpg");
                oldPhoto.delete();
            }
        } catch (IOException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to convert photo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userService.update(user, editUserBean);
        if (file != null) {
            userService.setLinkPhoto(user.getId() + "." + ++photoVersion + ".jpg", user.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(@RequestParam String refreshToken,
                                    HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        userTokenService.deleteAccessToken(user, resolveToken(request));
        refreshTokenService.deleteRefreshToken(user, refreshToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-account", method = RequestMethod.GET)
    public ResponseEntity<?> deleteAccount(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        String path = new File("").getAbsolutePath();
        File file = new File(path + user.getLinkPhoto());
        file.delete();
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/check-token", method = RequestMethod.GET)
    public ResponseEntity<?> checkToken(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/get-user-id", method = RequestMethod.GET)
    public ResponseEntity<?> getUserId(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        return new ResponseEntity<>(((User) responseEntity.getBody()).getId(), HttpStatus.OK);
    }


    private ResponseEntity<?> getUserByRequest(HttpServletRequest request) {
        String email;
        String token = resolveToken(request);
        try {
            email = jwtService.extractUserInfo(token);
        } catch (URISyntaxException | IOException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Extracting user info by token is failed."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user;
        try {
            user = userService.findUserByEmail(email);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "User with email " + email + " does not exist."), HttpStatus.NOT_FOUND);
        }
        if (!userTokenService.isCorrectAccessToken(user, token)) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "This access token does not exist."), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
