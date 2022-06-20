package com.example.lmclients.domain.service.implementacao;

import com.example.lmclients.controller.DTO.ClientDTO;
import com.example.lmclients.domain.entity.Client;
import com.example.lmclients.domain.repository.ClientRepository;
import com.example.lmclients.domain.service.ClientService;
import com.example.lmclients.exception.ClientInexistenteException;
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

    public ClientDTO findById(Long id) throws Exception {
        Client client = repository.findById(id).orElseThrow(
                () -> new ClientInexistenteException(String.format("Não existe nenhum cliente com o ID %s", id)));
        
        return modelMapper.map(client, ClientDTO.class);
    }

    public ClientDTO save(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);

        return modelMapper.map(repository.save(client), ClientDTO.class);
    }

    public void delete(Long id) throws Exception {
        Client client = repository.findById(id).orElseThrow(
                () -> new ClientInexistenteException(String.format("Não existe nenhum cliente com o ID %s", id)));

        repository.delete(client);
    }

    public ClientDTO update(Long id, ClientDTO clientDTO) throws Exception {
        Client clientPersistido = repository.findById(id).orElseThrow(
                () -> new ClientInexistenteException(String.format("Não existe nenhum cliente com o ID %s", id)));

        Client clientAtualizar = modelMapper.map(clientDTO, Client.class);

        clientAtualizar.setId(clientPersistido.getId());

        return modelMapper.map(repository.save(clientAtualizar), ClientDTO.class);

    }
}
