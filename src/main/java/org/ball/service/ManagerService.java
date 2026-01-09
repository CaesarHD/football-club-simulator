package org.ball.service;

import org.ball.domain.*;
import org.ball.repository.ManagerRepository;
import org.ball.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ManagerService {

    public static final Logger log = LoggerFactory.getLogger(ManagerService.class);
    private final ManagerRepository managerRepository;
    private final TransferRepository transferOfferRepository;

    public ManagerService(ManagerRepository managerRepository, TransferRepository transferOfferRepository) {
        this.managerRepository = managerRepository;
        this.transferOfferRepository = transferOfferRepository;
    }

    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public List<Transfer> getAllTransferOffers() {
        return transferOfferRepository.findAll();
    }

    public Manager getManagerById(int id) {
        return managerRepository.findById(id).orElse(null);
    }

    public Manager getManagerByName(String name) {
        return managerRepository.findByName(name);
    }

    public void sendTransferOffer(Club sender, Club receiver, long sum) {
        if(receiver ==  null || sender == null || sum <= 0) {
            log.error("Invalid transfer offer");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transfer offer");
        }

        Transfer newTransfer = new Transfer(sender, receiver, sum);
        transferOfferRepository.save(newTransfer);
    }

    public List<Transfer> getAllTransferOffersForAClub(Manager manager) {
        if(manager == null) {
            log.error("Invalid manager arg");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager is null");
        }


        if(manager.getClub() == null || manager.getClub().getId() == null) {
            log.error("Invalid transfer offer, manager's club is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "club or club id is null");
        }

        return transferOfferRepository.findTransferOfferByReceiver(manager.getClub().getId());
    }

    public void acceptTransferOffer(Transfer transfer) {
        if(transfer == null) {
            log.error("Invalid transfer offer");
        }
        transfer.setStatusTransferOffer(TransferStatus.ACCEPTED);

        transferOfferRepository.delete(transfer);
    }

    public void declineTransferOffer(Transfer transfer) {
        if(transfer == null) {
            log.error("Invalid transfer offer");
        }
        transfer.setStatusTransferOffer(TransferStatus.REJECTED);
        transferOfferRepository.save(transfer);
    }
}
