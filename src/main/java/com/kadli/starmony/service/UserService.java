package com.kadli.starmony.service;

import com.kadli.starmony.dto.UserDTO;
import com.kadli.starmony.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserService extends DtoConversions<User, UserDTO>{

    // Usuarios
    boolean exist(User user);
    Optional<User> getById(Long idUser);
    User registrar(User user);
    User obtenerUsuarioPorCredenciales(User user);

    Long getMaxUIId();

    // Intervalos
    UserInterval saveInterval(User user, ConcreteInterval interval);
    List<UserInterval> getConcreteIntervalsOf(Long id);
    void eliminarIntervalo(Long id, Long id1);

    // Acordes
    UserChord saveChord(User user, ConcreteChord chord);
    List<UserChord> getConcreteChordsOf(Long idUser);
    void deleteChord(Long idUser, Long idChord);

    // Escalas
    UserScale saveScale(User user, ConcreteScale scale);
    List<UserScale> getConcreteScalesOf(Long idUser);
    void deleteScale(Long idUser, Long idScale);

    // Progressiones
    UserProgression saveProgression(User user, ConcreteProgression progression);
    List<UserProgression> getConcreteProgressionsOf(Long idUser);
    void deleteProgression(Long idUser, Long idProgression);
}
