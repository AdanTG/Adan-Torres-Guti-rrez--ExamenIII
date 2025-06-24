package com.upiiz.ExamenIII.Controllers;

import com.upiiz.ExamenIII.Models.PrestamosModel;
import com.upiiz.ExamenIII.Services.PrestamosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class PrestamosController {

    @Autowired
    private PrestamosService prestamosService;

    @GetMapping("/prestamo")
    public String prestamo(){
        return "prestamo";
    }

    @GetMapping("/v1/api/prestamo")
    public ResponseEntity<Map<String,Object>> getAllPrestamos() {
        List<PrestamosModel> prestamos=prestamosService.findAllPrestamos();
        return ResponseEntity.ok(Map.of(
                "estado",1,
                "mensaje","Listado de prestamos",
                "prestamos",prestamos
        ));
    }

    @PostMapping("/v1/api/prestamo")
    public ResponseEntity<Map<String,Object>> prestamoPost(@RequestBody Map<String,Object> objetoPrestamo) {
        PrestamosModel prestamo = new PrestamosModel(
                Integer.parseInt(objetoPrestamo.get("libro_id").toString()),
                objetoPrestamo.get("nombre_usuario").toString(),
                LocalDate.parse(objetoPrestamo.get("fecha_prestamo").toString()),
                LocalDate.parse(objetoPrestamo.get("fecha_devolucion").toString())
        );

        PrestamosModel prestamoGuardado=prestamosService.save(prestamo);
        if(prestamoGuardado!=null)
            return ResponseEntity.ok(Map.of(
                    "estado",1,
                    "mensaje","Prestamo guardada correctamente",
                    "prestamo", prestamoGuardado
            ));
        else
            return ResponseEntity.ok(Map.of(
                    "estado",0,
                    "mensaje","Error: No se pudo guardar el prestamo",
                    "prestamo", objetoPrestamo
            ));
    }

    @PostMapping("/v1/api/prestamo/eliminar")
    public ResponseEntity<Map<String,Object>> prestamoDelete(@RequestBody Map<String,Object> objetoPrestamo) {
        int id = Integer.parseInt(objetoPrestamo.get("id").toString());

        if(prestamosService.delete(id) > 0){
            return ResponseEntity.ok(Map.of(
                    "estado",1,
                    "mensaje","Prestamo eliminado"
            ));
        }else {
            return ResponseEntity.ok(Map.of(
                    "estado",0,
                    "mensaje","No se pudo eliminar el prestamo"
            ));
        }
    }

    @GetMapping("/v1/api/prestamo/actualizar/{id}")
    public ResponseEntity<Map<String,Object>> prestamoActualizar(@PathVariable int id){
        PrestamosModel prestamo = prestamosService.findPrestamosById(id);
        return ResponseEntity.ok(Map.of(
                "estado",1,
                "mensaje","Prestamo encontrado",
                "prestamo", prestamo
        ));
    }

    @PostMapping("/v1/api/prestamo/actualizar/{id}")
    public ResponseEntity<Map<String,Object>> prestamoActualizarDatos(@PathVariable Long id, @RequestBody Map<String,Object> objetoPrestamo) {
        PrestamosModel prestamo = new PrestamosModel(
                Integer.parseInt(objetoPrestamo.get("libro_id").toString()),
                objetoPrestamo.get("nombre_usuario").toString(),
                LocalDate.parse(objetoPrestamo.get("fecha_prestamo").toString()),
                LocalDate.parse(objetoPrestamo.get("fecha_devolucion").toString())
        );
        prestamo.setId(id);
        if(prestamosService.update(prestamo) > 0)
            return ResponseEntity.ok(Map.of(
                    "estado",1,
                    "mensaje","Prestamo actualizada correctamente",
                    "prestamo", prestamo
            ));
        else
            return ResponseEntity.ok(Map.of(
                    "estado",0,
                    "mensaje","Error: No se pudo actualizar el prestamo",
                    "prestamo", objetoPrestamo
            ));
    }
}
