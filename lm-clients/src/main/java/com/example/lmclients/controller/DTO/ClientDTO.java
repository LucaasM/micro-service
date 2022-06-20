package com.example.lmclients.controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ClientDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @CPF
    private String cpf;

    private String email;
    private String numberPhone;

    @NotBlank
    private Boolean ativo;
}
