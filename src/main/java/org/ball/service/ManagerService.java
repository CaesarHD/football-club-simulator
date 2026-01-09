package org.ball.service;

import org.ball.domain.Manager;
import org.ball.repository.ManagerRepository;

import java.util.List;

public class ManagerService {

    private final ManagerRepository managerRepository;


    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }


}
