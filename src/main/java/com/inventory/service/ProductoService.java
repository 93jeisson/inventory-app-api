package com.inventory.service;

import com.inventory.model.Producto;
import com.inventory.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        Producto existente = productoRepository.findById(id).orElse(null);

        if (existente != null) {
            existente.setNombre(producto.getNombre());
            existente.setDescripcion(producto.getDescripcion());
            existente.setPrecio(producto.getPrecio());
            existente.setCantidad(producto.getCantidad());

            return productoRepository.save(existente);
        }

        return null;
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}