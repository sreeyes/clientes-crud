package com.test.clientes.controller;

import com.test.clientes.model.ClienteDto;
import com.test.clientes.services.implement.ClienteImplement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("client")
public class ClienteController extends ClienteImplement {
    @PostMapping("/create")
    public ResponseEntity<?> postCliente(
            @Validated @RequestBody ClienteDto bodyRequest
    ) throws IOException {

        Object respuesta = crearCliente(bodyRequest);

        return new ResponseEntity<Object>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> obtenerCliente(
            @PathVariable("idCliente") String idCliente
    ) throws FileNotFoundException {

        String respuesta = obtenerClienteDoc(idCliente);
        HashMap<String, Object> response = new HashMap<>();
        if (!Objects.equals(respuesta, "false")) {
            response.put("id", respuesta.split(",")[0]);
            response.put("nombre", respuesta.split(",")[1]);
            response.put("correo", respuesta.split(",")[2]);
        } else {
            response.put("mensaje", "El cliente no existe");
        }

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> actualizarCliente(
            @PathVariable("idCliente") String idCliente,
            @RequestBody ClienteDto bodyRequest
    ) throws IOException {

        Object respuesta = editarCliente(idCliente, bodyRequest);
        HashMap<String, Object> response = new HashMap<>();
        response.put("mensaje", "Cliente actualizado correctamente");

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> deleteCliente(
            @PathVariable("idCliente") String idCliente
    ) throws IOException {

        Object respuesta = eliminarCliente(idCliente);
        HashMap<String, Object> response = new HashMap<>();
        response.put("mensaje", "Cliente eliminado correctamente");

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
