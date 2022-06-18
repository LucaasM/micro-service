package com.example.lmclients.controller;

import com.example.lmclients.controller.DTO.ClientDTO;
import com.example.lmclients.domain.service.implementacao.ClientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientServiceImpl service;

    public ClientController (ClientServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
