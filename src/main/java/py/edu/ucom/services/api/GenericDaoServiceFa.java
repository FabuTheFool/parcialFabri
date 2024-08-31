package main.java.py.edu.ucom.services.api;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import main.java.py.edu.ucom.entities.apiresponse.Presupuestos;
import main.java.py.edu.ucom.repository.ApiResponseRepository;
import py.edu.ucom.config.GenericDAO;

@ApplicationScoped
public class GenericDaoServiceFa implements GenericDAO<Presupuestos, Integer> {

    public ApiResponseRepositoryPresupuesto repository;

    public GenericDaoServiceFa(ApiResponseRepositoryPresupuesto repository) {
        this.repository = repository;
    }

    @Override
    public List<Presupuestos> listar() {
        return this.repository.listar();
    }

    @Override
    public Presupuestos obtener(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Presupuestos modificar(Presupuestos param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Presupuestos agregar(Presupuestos param) {
        return this.repository.agregar(param);
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Presupuestos mayorPresupuesto() {
        List<Presupuestos> lista = this.repository.listar();
        Integer max = 0;
        Integer id = 0;
        for (Presupuestos elem : lista) {
            if (elem.getMonto() > max) {
                max = elem.getMonto();
                id = elem.getId();
            }
        }
        Presupuestos data = this.repository.obtenerById(id);
        return data;

    }

}
