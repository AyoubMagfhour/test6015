package com.system.system.service;

import com.system.system.jpa.SadmSystemOM;

import java.util.List;

public interface SadmSystemOMService {

    SadmSystemOM saveSystem(SadmSystemOM system);

    SadmSystemOM getSystemById(Long id);

    List<SadmSystemOM> getAllSystem();

    void deleteSystem(Long id);

}

