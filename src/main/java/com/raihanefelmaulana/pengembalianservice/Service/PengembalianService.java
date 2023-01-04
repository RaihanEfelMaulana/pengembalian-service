/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raihanefelmaulana.pengembalianservice.Service;

import com.raihanefelmaulana.pengembalianservice.Entity.Pengembalian;
import com.raihanefelmaulana.pengembalianservice.Repository.PengembalianRepository;
import com.raihanefelmaulana.pengembalianservice.Vo.Peminjaman;
import com.raihanefelmaulana.pengembalianservice.Vo.ResponseTemplateVOPinjam;
import com.raihanefelmaulana.pengembalianservice.Vo.ResponseTemplateVo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author hp
 */
@Service
public class PengembalianService {
    @Autowired
    private SimpleDateFormat simpleDateFormat;
    
    @Autowired
    private PengembalianRepository pengembalianRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Pengembalian savePengembalian(Pengembalian pengembalian) throws ParseException{
        ResponseTemplateVOPinjam peminjaman = restTemplate.getForObject("http://localhost:8119/peminjaman/"
                +pengembalian.getPeminjamanId(), ResponseTemplateVOPinjam.class);
        String tglSekarang = simpleDateFormat.format(new Date());
        long terlambat = kurangTanggal(tglSekarang, peminjaman.getPeminjaman().getTglKembali());
        double denda = terlambat * 500;
        pengembalian.setTerlambat((int)terlambat);
        pengembalian.setTglDiKembalikan(tglSekarang);
        pengembalian.setDenda(denda);
        
        return pengembalianRepository.save(pengembalian);
    }
    
    public ResponseTemplateVo getPengembalian(Long pengembalianId){
        ResponseTemplateVo vo = new ResponseTemplateVo();
        Pengembalian pengembalian = pengembalianRepository.findByPengembalianId(pengembalianId);
        ResponseTemplateVOPinjam peminjaman = restTemplate.getForObject("http://localhost:8119/peminjaman/"
                +pengembalian.getPeminjamanId(), ResponseTemplateVOPinjam.class);
        vo.setPengembalian(pengembalian);
        vo.setPeminjaman(peminjaman.getPeminjaman());
        return vo;
    }

    private long kurangTanggal(String tglAwal, String tglAkhir) throws ParseException {
        Date tgl1 = simpleDateFormat.parse(tglAwal);
        Date tgl2 = simpleDateFormat.parse(tglAkhir);
        long selisih = tgl1.getTime() - tgl2.getTime();
        long selisihHari = selisih / (24 * 60 * 60 * 1000);
        return selisihHari;
    }
    
    public Pengembalian findPengembalianById(Long pengembalianId){
        return pengembalianRepository.findByPengembalianId(pengembalianId);
    } 
     
    public List<Pengembalian> getAllPengembalian(){
        return pengembalianRepository.findAll();
    }
    
    public void deletePengembalian(Long pengembalianId) {
        pengembalianRepository.deleteById(pengembalianId);
    }
    
    public Pengembalian updatePengembalian(Pengembalian pengembalian){
        return pengembalianRepository.save(pengembalian);
    }
}
