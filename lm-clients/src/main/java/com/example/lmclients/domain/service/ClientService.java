package com.example.lmclients.domain.service;

import com.example.lmclients.controller.DTO.ClientDTO;
import com.example.lmclients.domain.entity.Client;

import java.util.List;

public interface ClientService {

    List<ClientDTO> findAll();
    ClientDTO findById(Long id) throws Exception;
    ClientDTO save(ClientDTO clientDTO);
    void delete(Long id) throws Exception;
    ClientDTO update(Long id, ClientDTO clientDTO) throws Exception;
}