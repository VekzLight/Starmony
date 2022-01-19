package com.kadli.starmony.dao;

import com.kadli.starmony.models.Scale;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ScaleDAOImp implements ScaleDAO{

    @PersistenceContext
    private EntityManager entityManager;

    public Scale getScale(int id){
        Scale scale = new Scale();
        List<Scale> result = entityManager.createQuery("from Scale where id_scale='"+id+"'").getResultList();
        if(!result.isEmpty()){
            scale = result.get(0);
        }
        else{
            scale.setName("-");
            scale.setSymbol("-");
            scale.setCode("-");
        }
        return scale;
    }

    public List<Scale> matchScales(Scale scale) {
        List<Scale> scales = new ArrayList<>();
        scales.addAll(matchScalesByCode(scale.getCode()));
        scales.addAll(matchScalesByName(scale.getName()));
        scales.addAll(matchScalesBySymbol(scale.getSymbol()));

        return scales;
    }

    public List<Scale> matchScalesByCode(String code) {
        return entityManager.createQuery("from Scale scale where scale.code='" + code + "'").getResultList();
    }

    public List<Scale> matchScalesByName(String name) {
        return entityManager.createQuery("from Scale scale where scale.code='" + name + "'").getResultList();
    }

    public List<Scale> matchScalesBySymbol(String symbol) {
        return entityManager.createQuery("from Scale scale where scale.code='" + symbol + "'").getResultList();
    }
}
