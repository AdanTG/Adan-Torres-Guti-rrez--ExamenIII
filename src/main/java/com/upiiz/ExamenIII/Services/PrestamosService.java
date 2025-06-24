package com.upiiz.ExamenIII.Services;

import com.upiiz.ExamenIII.Models.PrestamosModel;
import com.upiiz.ExamenIII.Repositories.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamosService implements PrestamosRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<PrestamosModel> findAllPrestamos(){
        return jdbcTemplate.query("SELECT * FROM prestamos",
                new BeanPropertyRowMapper<>(PrestamosModel.class));
    }

    @Override
    public PrestamosModel findPrestamosById(int id) {
        return jdbcTemplate.query("SELECT * FROM prestamos WHERE id=?",
                new BeanPropertyRowMapper<>(PrestamosModel.class),id)
                .stream().findFirst().orElse(new PrestamosModel());
    }

    @Override
    public PrestamosModel save(PrestamosModel prestamo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO prestamos(libro_id,nombre_usuario,fecha_prestamo,fecha_devolucion) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1,prestamo.getLibro_id());
            ps.setString(2,prestamo.getNombre_usuario());
            ps.setDate(3, Date.valueOf((LocalDate) prestamo.getFecha_prestamo()));
            ps.setDate(4, Date.valueOf((LocalDate) prestamo.getFecha_devolucion()));
            return ps;
        },keyHolder);

        Number generatedId = keyHolder.getKey();
        if(generatedId != null){
            prestamo.setId(generatedId.longValue());
        }else{
            prestamo.setId(0L);
        }
        return prestamo;
    }

    @Override
    public int update(PrestamosModel prestamo) {
        int registrosAfectados=jdbcTemplate.update(
                "UPDATE prestamos SET libro_id=?,nombre_usuario=?,fecha_prestamo=?,fecha_devolucion=? WHERE id=?",
                prestamo.getLibro_id(),prestamo.getNombre_usuario(),prestamo.getFecha_prestamo(),prestamo.getFecha_devolucion(),prestamo.getId());
        return registrosAfectados;
    }

    @Override
    public int delete(int id) {
        int registrosAfectados=jdbcTemplate.update("DELETE FROM prestamos WHERE id=?",id);
                return registrosAfectados;
    }
}
