package com.upiiz.ExamenIII.Repositories;

import com.upiiz.ExamenIII.Models.PrestamosModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamosRepository {
    public List<PrestamosModel> findAllPrestamos();
    public PrestamosModel findPrestamosById(int id);
    public PrestamosModel save(PrestamosModel model);
    public int update(PrestamosModel model);
    public int delete(int id);
}
