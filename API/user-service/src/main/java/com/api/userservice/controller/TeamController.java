package com.api.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.userservice.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeam() {
        return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
    }
}
