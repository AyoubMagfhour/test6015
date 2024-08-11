package com.cq.clinique.serviceimpl;

import com.cq.clinique.jpa.SadmCliniqueOM;
import com.cq.clinique.repository.SadmCliniqueOMRepository;
import com.cq.clinique.service.SadmCliniqueOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SadmCliniqueOMServiceImpl implements SadmCliniqueOMService {

    @Autowired
    private SadmCliniqueOMRepository sadmCliniqueOMRepository;

    @Override
    public SadmCliniqueOM saveclinique(SadmCliniqueOM sadmClinique) {
        return sadmCliniqueOMRepository.save(sadmClinique);
    }

    @Override
    public SadmCliniqueOM findbyId(Long id) {
        return sadmCliniqueOMRepository.findById(id).orElse(null);
    }

    @Override
    public List<SadmCliniqueOM> findAll() {
        return sadmCliniqueOMRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        SadmCliniqueOM clinique = findbyId(id);
        if(clinique != null) {
            sadmCliniqueOMRepository.delete(clinique);
        }
    }
}
