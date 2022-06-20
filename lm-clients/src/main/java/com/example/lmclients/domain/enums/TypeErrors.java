package com.example.lmclients.domain.enums;

import lombok.Builder;
import lombok.Getter;

@Getter
public enum TypeErrors {
    CLIENT_INEXISTENT("/client-inexistent", "Cliente inexistente");

    private String uri;
    private String title;

    TypeErrors (String path, String title) {
        this.title = title;
        this.uri = "https://localhost:8081" + path;
    }


}
