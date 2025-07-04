package com.gestion.controllers;

import com.gestion.dto.CrearUsuarioRequest;
import com.gestion.dto.UsuarioDTO;
import com.gestion.models.Usuario;
import com.gestion.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> crearUsuario(@RequestBody CrearUsuarioRequest request) {
        if (request.getNombreUsuario() == null || request.getEmail() == null || request.getContrasena() == null) {
            return ResponseEntity.badRequest().build();
        }
        Usuario usuario = usuarioService.crearUsuario(request);
        UsuarioDTO dto = usuarioService.buscarUsuarioPorId(usuario.getIdUsuario());
        EntityModel<UsuarioDTO> resource = EntityModel.of(dto);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerHATEOAS(dto.getIdUsuario())).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).eliminar(dto.getIdUsuario())).withRel("eliminar"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<UsuarioDTO>>> listarUsuarios() {
        List<UsuarioDTO> dtos = usuarioService.listarUsuarios();
        List<EntityModel<UsuarioDTO>> resources = dtos.stream()
                .map(dto -> EntityModel.of(dto)
                        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerHATEOAS(dto.getIdUsuario())).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/hateoas/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerHATEOAS(@PathVariable Integer id) {
        try {
            UsuarioDTO dto = usuarioService.buscarUsuarioPorId(id);
            EntityModel<UsuarioDTO> resource = EntityModel.of(dto);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerHATEOAS(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).eliminar(id)).withRel("eliminar"));
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hateoas")
    public ResponseEntity<List<EntityModel<UsuarioDTO>>> obtenerTodosHATEOAS() {
        List<UsuarioDTO> lista = usuarioService.listarUsuarios();
        List<EntityModel<UsuarioDTO>> resources = lista.stream()
                .map(dto -> EntityModel.of(dto)
                        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerHATEOAS(dto.getIdUsuario())).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO updatedDto = usuarioService.actualizarUsuario(id, dto);
            EntityModel<UsuarioDTO> resource = EntityModel.of(updatedDto);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerHATEOAS(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).eliminar(id)).withRel("eliminar"));
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}