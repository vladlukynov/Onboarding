package com.api.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.userservice.DTO.TeamDTO;
import com.api.userservice.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<TeamDTO> getAll() {
        return teamRepository.getAllTeam();
    }
}
