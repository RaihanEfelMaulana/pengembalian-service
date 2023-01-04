/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raihanefelmaulana.pengembalianservice.Controller;

import com.raihanefelmaulana.pengembalianservice.Entity.Pengembalian;
import com.raihanefelmaulana.pengembalianservice.Service.PengembalianService;
import com.raihanefelmaulana.pengembalianservice.Vo.ResponseTemplateVo;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/pengembalian")
public class PengembalianController {
    @Autowired
    private PengembalianService pengembalianService;
    
    @PostMapping("/")
    public Pengembalian savePengembalian(@RequestBody Pengembalian pengembalian) throws ParseException{
        return pengembalianService.savePengembalian(pengembalian);
    }
    
    @GetMapping("/{id}")
    public Pengembalian findPegembalianById(@PathVariable("id") Long pegembalianId){
        return pengembalianService.findPengembalianById(pegembalianId);
    }
    
    @GetMapping("/")
    public List<Pengembalian> getAllPengembalian(){
        return pengembalianService.getAllPengembalian();
    }
    
    @PutMapping("/")
    public Pengembalian updatePengembalian(@RequestBody Pengembalian pengembalian){
        return pengembalianService.updatePengembalian(pengembalian);
    } 
     
    @DeleteMapping("/{id}")
    public void deletePengembalian(@PathVariable("id") Long pengembalianId){
        pengembalianService.deletePengembalian(pengembalianId);
    }
}
