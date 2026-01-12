package org.ball.service;

import org.ball.domain.*;
import org.ball.repository.*;
import org.springframework.stereotype.Service;
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
    private final ClubRepository clubRepository;

    public ManagerService(ManagerRepository managerRepository, TransferRepository transferRepository, PlayerRepository playerRepository, PlayerHistoryRepository playerHistoryRepository, PlayerService playerService, ClubRepository clubRepository) {
        this.managerRepository = managerRepository;
        this.transferRepository = transferRepository;
        this.playerRepository = playerRepository;
        this.playerHistoryRepository = playerHistoryRepository;
        this.playerService = playerService;
        this.clubRepository = clubRepository;
    }

    @Transactional
    public void makeTransferOffer(Long transferId, long sum) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found"));

        // Optional: Check if buyer has funds before even offering
        if (transfer.getNewClub().getBudget() < sum) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient budget to make this offer");
        }

        transfer.setTransferStatus(TransferStatus.MANAGER_OFFER);
        transfer.setOfferSum(sum);
        transferRepository.save(transfer);
    }

    public void rejectTransfer(Long transferId) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found"));
        transfer.setTransferStatus(TransferStatus.MANAGER_REJECTED);
        transferRepository.save(transfer);
    }

    @Transactional
    public void approveTransferOffer(Long transferId) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found"));

        if (transfer.getTransferStatus() != TransferStatus.MANAGER_OFFER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No active offer to accept.");
        }

        Club buyer = transfer.getNewClub();
        Club seller = transfer.getPlayer().getClub();
        Player player = transfer.getPlayer();
        long price = transfer.getSum(); // Assuming getSum() returns the offerSum

        // 1. Check Funds
        if (buyer.getBudget() < price) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buyer does not have enough funds.");
        }

        // 2. Transfer Money
        buyer.setBudget(buyer.getBudget() - (int)price);
        if (seller != null) {
            seller.setBudget(seller.getBudget() + (int)price);
            clubRepository.save(seller);
        }

        // 3. Update Status
        transfer.setTransferStatus(TransferStatus.DONE);

        // 4. Move Player (Using PlayerService or directly)
        playerService.transfer(transfer);

        // 5. Save Changes
        clubRepository.save(buyer);
        transferRepository.save(transfer);
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
