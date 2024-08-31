package main.java.py.edu.ucom.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import main.java.py.edu.ucom.entities.apiresponse.Presupuestos;

@ApplicationScoped
public class ApiResponseRepositoryPresupuesto {

    private static final String FILE_PATH = "src/main/resources/data/presupuestos.json";
    private List<Presupuesto> presupuestosList;
    private ObjectMapper objectMapper;

    public ApiResponseRepositoryPresupuesto() {
        objectMapper = new ObjectMapper();
        presupuestosList = cargarDatos();
        presupuestosSave = guardarDatos();

    }

    private List<Presupuesto> cargarDatos() {
        try {
            System.out.println("CARGA DE DATOS" + FILE_PATH);
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Presupuesto>>() {
                });
            } else {

                System.out.println("UPSSS NO HAY DATOS");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), presupuestosList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Presupuestos obtenerById(Integer id) {
        return presupuestosList.stream()
                .filter(Presupuesto -> Presupuesto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Presupuestos> listar() {

        return new ArrayList<>(presupuestosList);
    }

    public Presupuestos agregar(Presupuestos param) {
        Integer newId = presupuestosList.isEmpty() ? 1
                : presupuestosList.stream()
                        .mapToInt(Presupuesto::getId)
                        .max()
                        .getAsInt() + 1;

        param.setId(newId);
        presupuestosList.add(param);
        guardarDatos();
        return param;
    }

    public Presupuestos modificar(Presupuestos param) {
        Optional<Presupuestos> existingPresupuesto = presupuestosList.stream()
                .filter(Presupuesto -> Presupuesto.getId().equals(param.getId()))
                .findFirst();

        if (existingPresupuesto.isPresent()) {
            presupuestosList = presupuestosList.stream()
                    .map(Presupuesto -> Presupuesto.getId().equals(param.getId()) ? param : Presupuesto)
                    .collect(Collectors.toList());
            guardarDatos();
            return param;
        } else {
            return null;
        }
    }

    public void eliminar(Integer id) {
        presupuestosList = presupuestosList.stream()
                .filter(Presupuesto -> !Presupuesto.getId().equals(id))
                .collect(Collectors.toList());
        guardarDatos();
    }
}
