package com.system.system.serviceimpl;

import com.system.system.jpa.SadmSystemOM;
import com.system.system.repository.SadmSystemOMRepository;
import com.system.system.service.SadmSystemOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SadmSystemOMServiceImpl implements SadmSystemOMService {

    @Autowired
    private SadmSystemOMRepository systemRepository;

    @Override
    public SadmSystemOM saveSystem(SadmSystemOM system) {
        return systemRepository.save(system);
    }

    @Override
    public SadmSystemOM getSystemById(Long id) {
        return systemRepository.findById(id).orElse(null);
    }
    @Override
    public List<SadmSystemOM> getAllSystem() {
        return systemRepository.findAll();
    }
    @Override
    public void deleteSystem(Long id) {
        SadmSystemOM system = getSystemById(id);
        if (system != null) {
            systemRepository.delete(system);
        }

    }

}