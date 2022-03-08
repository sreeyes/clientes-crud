package com.test.clientes.services;

import com.test.clientes.model.ClienteDto;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ClienteService {
    Object crearCliente(ClienteDto data) throws IOException;
    Object obtenerClienteDoc(String data) throws FileNotFoundException;
    Object editarCliente(String id, ClienteDto data) throws IOException;
    Object eliminarCliente(String id) throws IOException;
}
