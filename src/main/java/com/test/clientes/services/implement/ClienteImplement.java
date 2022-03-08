package com.test.clientes.services.implement;

import com.test.clientes.model.ClienteDto;
import com.test.clientes.services.ClienteService;

import java.io.*;
import java.util.HashMap;
import java.util.stream.Stream;

public class ClienteImplement implements ClienteService {
    @Override
    public Object crearCliente(ClienteDto bodyRequest) throws IOException {
        HashMap<String, Object> respuesta = new HashMap<>();

        File file = new File("src/main/java/com/test/clientes/documents");
        if (file.exists()) {
            File fileCreated = new File("src/main/java/com/test/clientes/documents/clientes.txt");

            FileReader fr = new FileReader(fileCreated);
            BufferedReader br = new BufferedReader(fr);
            int idCliente = (int) (br.lines().count() + 1);


            FileOutputStream f = new FileOutputStream(fileCreated, true);
            String lineAgregar = idCliente + ", " + bodyRequest.getNombre() + ", " + bodyRequest.getCorreo() + "\n";

            byte[] byteArr = lineAgregar.getBytes();
            f.write(byteArr);
            f.close();
        } else {
            new File("src/main/java/com/test/clientes/documents/" + "clientes.txt");
        }

        respuesta.put("mensaje", "Cliente creado correctamente");

        return respuesta;
    }

    @Override
    public String obtenerClienteDoc(String data) throws FileNotFoundException {
        File file = new File("src/main/java/com/test/clientes/documents/" + "clientes.txt");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        try {
            Stream<String> cliente = br.lines().filter(ln -> ln.contains(data));
            return cliente.toList().get(0);
        } catch (ArrayIndexOutOfBoundsException a) {
            return "false";
        }
    }

    @Override
    public Object editarCliente(String id, ClienteDto data) throws IOException {
        String lineAEditar = id + ", " + data.getNombre() + ", " + data.getCorreo();

        File inputFile = new File("src/main/java/com/test/clientes/documents/" + "clientes.txt");
        File tempFile = new File("src/main/java/com/test/clientes/documents/" + "clientesTemp.txt");
        File temporalFile = inputFile;

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToEdit = lineAEditar;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.split(",")[0];
            if(trimmedLine.equals(lineToEdit.split(",")[0])) continue;
                writer.write(currentLine + "\n");
        }
        writer.write(lineAEditar + "\n");

        writer.close();
        reader.close();

        boolean isDelete = inputFile.delete();
        boolean isChange = tempFile.renameTo(temporalFile);
        if (isChange && isDelete) {
            return "Cliente eliminado correctamente";
        } else {
            return null;
        }
    }

    @Override
    public Object eliminarCliente(String id) throws IOException {
        String lineToDelete = id;

        File inputFile = new File("src/main/java/com/test/clientes/documents/" + "clientes.txt");
        File tempFile = new File("src/main/java/com/test/clientes/documents/" + "clientesTemp.txt");
        File temporalFile = inputFile;

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.split(",")[0];
            if(trimmedLine.equals(lineToDelete)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

        boolean isDelete = inputFile.delete();
        boolean isChange = tempFile.renameTo(temporalFile);
        if (isChange && isDelete) {
            return "Cliente eliminado correctamente";
        } else {
            return null;
        }
    }
}
