package com.example.lmclients.domain.service;

import com.example.lmclients.controller.DTO.ClientDTO;

import java.util.List;

public interface ClientService {

    List<ClientDTO> findAll();
}