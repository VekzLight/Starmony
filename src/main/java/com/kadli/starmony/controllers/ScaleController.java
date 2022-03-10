package com.kadli.starmony.controllers;

import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.service.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recognize/scale")
@CrossOrigin(origins = "http://localhost:4200")
public class ScaleController {

    @Autowired
    private ScaleService scaleService;

    @GetMapping("/getScales")
    public ResponseEntity<List<Scale>> getScales(){
        return new ResponseEntity<>(scaleService.getScales(), HttpStatus.OK);
    }

    @GetMapping("/getScale/{id}")
    public ResponseEntity<Optional<Scale>> getScale(@PathVariable Long id){
        return new ResponseEntity<>(scaleService.getScale(id), HttpStatus.OK);
    }

}
