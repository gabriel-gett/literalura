package com.challenge.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int fechaDeNacimiento,
        @JsonAlias("death_year") int fallecimiento

) {
}
