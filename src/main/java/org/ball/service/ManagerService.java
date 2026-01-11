package org.ball.service;

import org.ball.domain.Coach;
import org.ball.domain.Manager;
import org.ball.domain.Transfer;
import org.ball.domain.TransferStatus;
import org.ball.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.ball.repository.PlayerHistoryRepository;
import org.ball.repository.PlayerRepository;
import org.ball.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ManagerService {

    public static final Logger log = LoggerFactory.getLogger(ManagerService.class);
    private final ManagerRepository managerRepository;
    private final TransferRepository transferRepository;
    private final PlayerRepository playerRepository;
    private final PlayerHistoryRepository playerHistoryRepository;
    private final PlayerService playerService;

    public ManagerService(ManagerRepository managerRepository, TransferRepository transferRepository, PlayerRepository playerRepository, PlayerHistoryRepository playerHistoryRepository, PlayerService playerService) {
        this.managerRepository = managerRepository;
        this.transferRepository = transferRepository;
        this.playerRepository = playerRepository;
        this.playerHistoryRepository = playerHistoryRepository;
        this.playerService = playerService;
    }

    @Transactional
    public void makeTransferOffer(Long transferId, long sum) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found"));
        transfer.setTransferStatus(TransferStatus.MANAGER_OFFER);
        transfer.setOfferSum(sum);
    }

    public void rejectTransfer(Long transferId) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found"));
        transfer.setTransferStatus(TransferStatus.MANAGER_REJECTED);
        transferRepository.save(transfer);
    }

    public void approveTransferOffer(Long transferId) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found"));
        transfer.setTransferStatus(TransferStatus.DONE);

        playerService.transfer(transfer);
    }

    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager getManagerByName(String name) {
        Manager manager = null;
        try {
            manager = managerRepository.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return manager;
    }
    public List<Transfer> getAllTransferOffers() {
        return transferRepository.findAll();
    }

    public Manager getManagerById(int id) {
        return managerRepository.findById(id).orElse(null);
    }


    public Manager getManagerByUserId(Long id) {
        if(id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is mandatory, cannot be null");
        }
        Optional<Manager> manager;
        try {
            manager = managerRepository.findByUserId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find the manager in the db ");
        }
        return manager.get();
    }
}
