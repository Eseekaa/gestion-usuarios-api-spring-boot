package com.gestion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CrearUsuarioRequest extends RepresentationModel<CrearUsuarioRequest> {
    private String nombreUsuario;
    private String email;
    @JsonProperty("contrasena")
    private String contrasena;
    private String rol;
    private String estado;
    private String nombreCompleto;
    private String rut;
    private String direccion;
    private String telefono;
    private String areaVentas;
}
