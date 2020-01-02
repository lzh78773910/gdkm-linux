package com.gdkm.service.impl;

import com.gdkm.Repository.HerfRepository;
import com.gdkm.model.Herf;
import com.gdkm.service.HerfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HerfServiceImpl implements HerfService {

    @Autowired
    private HerfRepository herfRepository;

    @Override
    public List<Herf> getHerfList() {
        return herfRepository.findAll();
    }
}
