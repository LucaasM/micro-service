package com.example.lmclients.domain.service.implementacao;

import com.example.lmclients.controller.DTO.ClientDTO;
import com.example.lmclients.domain.entity.Client;
import com.example.lmclients.domain.repository.ClientRepository;
import com.example.lmclients.domain.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.repository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClientDTO> findAll() {
        List<Client> client = repository.findAll();

        return client.stream().map(client1 -> modelMapper.map(client1, ClientDTO.class))
                .collect(Collectors.toList());
    }
}
