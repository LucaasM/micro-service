package com.example.lmclients.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String numberPhone;
    private Boolean ativo;
}
