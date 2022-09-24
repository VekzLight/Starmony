package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Progression;
import com.kadli.starmony.utilities.Symbols;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
public class ProgressionRepositoryCustomImp implements ProgressionRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Progression> findByAttribute(String attribute, String value) {
        List<Progression> progression = entityManager.createQuery("" +
                        "from Progression p" +
                        " where p." + attribute + " = :value", Progression.class)
                .setParameter("value", value)
                .getResultList();
        if(progression.isEmpty()) return Optional.empty();
        return Optional.of(progression.get(0));
    }

    @Override
    public List<Progression> getAllWithLenth(int size) {
        List<Progression> progressions = entityManager.createQuery("from Progression", Progression.class).getResultList();
        List<Progression> progressionsSelected = new ArrayList<>();

        for(Progression progression: progressions){
            Pattern pattern = Pattern.compile("[+-]?([0-9]+\\.?[0-9]*|\\.[0-9]+)");
            List<String> code = Arrays.stream(progression.getCode()
                    .replaceAll("[^0-9]", " ")
                    .replaceAll("\\s{2,}", " ")
                    .trim()
                    .split(" "))
                    .filter(e -> pattern.matcher( e ).matches())
                    .collect(Collectors.toList());

            int max = Integer.MIN_VALUE;
            for(String it: code){
                int current = Integer.parseInt( it.substring(0,1));
                if ( current >= max ) max = current;
            }

            if( max <= size )
                progressionsSelected.add(progression);

        }

        return progressionsSelected;
    }

    @Override
    public Long getMaxId() {
        return entityManager.createQuery("SELECT MAX(p.id) FROM Progression p", Long.class).getResultList().get(0);
    }
}
