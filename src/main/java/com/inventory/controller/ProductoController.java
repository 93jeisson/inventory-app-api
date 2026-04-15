package com.inventory.controller;

import com.inventory.model.Producto;
import com.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 👀 TODOS PUEDEN VER
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    // 👀 TODOS PUEDEN VER
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    // 👑 SOLO ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")

    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    // 👑 SOLO ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}