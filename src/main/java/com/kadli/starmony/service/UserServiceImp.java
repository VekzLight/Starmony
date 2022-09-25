package com.kadli.starmony.service;

import com.kadli.starmony.dto.UserDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("UserService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserIntervalRepository userIntervalRepository;

    @Autowired
    private UserChordRepository userChordRepository;

    @Autowired
    private UserScaleRepository userScaleRepository;

    @Autowired
    private UserProgressionRepository userProgressionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO entityToDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public User dtotoEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public boolean exist(User user) {
        if(userRepository.existUsername(user.getUsername())) return true;
        if(userRepository.existEmail(user.getEmail())) return true;
        return false;
    }

    @Override
    public User registrar(User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);

        userRepository.save(user);
        User userNew = userRepository.findByUsername(user.getUsername());
        return userNew;
    }

    @Override
    public User obtenerUsuarioPorCredenciales(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        if( userDB == null ) return null;

        String passwordHash = userDB.getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        boolean verify = argon2.verify(passwordHash, user.getPassword());

        if(!verify) return null;

        return userDB;
    }

    @Override
    public Optional<User> getById(Long idUser) {
        return userRepository.findById(idUser);
    }



    @Override
    public List<UserInterval> getConcreteIntervalsOf(Long id) {
        return userIntervalRepository.getAllIntervalsOfUserId(id);
    }

    @Override
    public void eliminarIntervalo(Long idUser, Long idInterval) {
        Long id = userIntervalRepository.getConcreteIdByIntervalId(idUser,idInterval);
        userIntervalRepository.deleteById(id);
    }

    @Override
    public Long getMaxUIId(){
        Long id = userIntervalRepository.getLast();
        return id == null ? 0: id;
    }

    @Override
    public UserInterval saveInterval(User user, ConcreteInterval interval) {
        UserInterval userInterval = new UserInterval();

        userInterval.setId_user_concrete_interval(getMaxUIId() + 1);
        userInterval.setUserOfConcreteIntervals(user);
        userInterval.setConcreteIntervalsOfUser(interval);

        System.out.println(userInterval.getUserOfConcreteIntervals().getId());

        UserInterval response = userIntervalRepository.save(userInterval);

        return response;
    }

    @Override
    public UserChord saveChord(User user, ConcreteChord chord) {
        UserChord userChord = new UserChord( null, user, chord );
        UserChord response = userChordRepository.save(userChord);
        return response;
    }

    @Override
    public List<UserChord> getConcreteChordsOf(Long idUser) {
        return userChordRepository.getAllChordsOfUserId(idUser);
    }

    @Override
    public void deleteChord(Long idUser, Long idChord) {
        Long id = userChordRepository.getConcreteIdByChordId(idUser,idChord);
        userChordRepository.deleteById(id);
    }

    @Override
    public UserScale saveScale(User user, ConcreteScale scale) {
        UserScale userScale = new UserScale(null, user, scale);
        UserScale response = userScaleRepository.save(userScale);
        return response;
    }

    @Override
    public List<UserScale> getConcreteScalesOf(Long idUser) {
        return userScaleRepository.getAllScalesOfUserId(idUser);
    }

    @Override
    public void deleteScale(Long idUser, Long idScale) {
        Long id = userScaleRepository.getConcreteIdByScaleId(idUser,idScale);
        userScaleRepository.deleteById(id);
    }

    @Override
    public UserProgression saveProgression(User user, ConcreteProgression progression) {
        UserProgression userProgression = new UserProgression(null, user, progression);
        UserProgression response = userProgressionRepository.save(userProgression);
        return response;
    }

    @Override
    public List<UserProgression> getConcreteProgressionsOf(Long idUser) {
        return userProgressionRepository.getAllProgressionsOfUserId(idUser);
    }

    @Override
    public void deleteProgression(Long idUser, Long idProgression) {
        Long id = userProgressionRepository.getConcreteIdByProgressionId(idUser,idProgression);
        userProgressionRepository.deleteById(id);
    }


}
