package com.example.lmclients.domain.service;

import com.example.lmclients.controller.DTO.ClientDTO;
import com.example.lmclients.domain.entity.Client;
import com.example.lmclients.domain.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final ModelMapper modelMapper;

    public ClientService (ClientRepository clientRepository, ModelMapper modelMapper) {
        this.repository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClientDTO> findAll() {
        List<Client> client = repository.findAll();

        return client.stream().map(client1 -> modelMapper.map(client1, ClientDTO.class))
                .collect(Collectors.toList());
    }
}
