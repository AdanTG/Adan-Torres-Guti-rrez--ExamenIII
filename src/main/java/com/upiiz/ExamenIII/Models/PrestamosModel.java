package com.upiiz.ExamenIII.Models;

import java.time.LocalDate;

public class PrestamosModel {
    private Long id;
    private int libro_id;
    private String nombre_usuario;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;


    public PrestamosModel() {

    }

    public PrestamosModel(int libro_id, String nombre_usuario, LocalDate fecha_prestamo, LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
        this.fecha_prestamo = fecha_prestamo;
        this.nombre_usuario = nombre_usuario;
        this.libro_id = libro_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public int getLibro_id() {
        return libro_id;
    }

    public void setLibro_id(int libro_id) {
        this.libro_id = libro_id;
    }
}
