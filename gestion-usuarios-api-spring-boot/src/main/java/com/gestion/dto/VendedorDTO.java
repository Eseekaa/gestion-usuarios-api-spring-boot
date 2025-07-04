package com.gestion.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendedorDTO extends RepresentationModel<VendedorDTO> { 
    private Integer idVendedor;
    private Integer idUsuario;
    private String nombreCompleto;
    private String rut;
    private String areaVentas;
}