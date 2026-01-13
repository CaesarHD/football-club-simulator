package org.ball.controller;

import org.ball.domain.Manager;
import org.ball.domain.UserInfo;
import org.ball.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PutMapping("/transfers/offer/{transferId}/{offerSum}")
    public void makeOffer(@PathVariable("transferId") Long transferId,
                          @PathVariable("offerSum") Long offerSum) {
        managerService.makeTransferOffer(transferId, offerSum);
    }

    @PutMapping("/transfers/reject/{transferId}")
    public void rejectTransfer(@PathVariable("transferId") Long transferId) {
        managerService.rejectTransfer(transferId);
    }

    // Accept Offer (Seller)
    @PutMapping("/transfers/accept/{transferId}")
    public void acceptTransfer(@PathVariable("transferId") Long transferId) {
        managerService.approveTransferOffer(transferId);
    }


    @PostMapping("/manager_profile")
    public Manager getManagerInfo(@RequestBody UserInfo userInfo) {
        Long userId = userInfo.getId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }

        Manager manager;
        try {
            manager = managerService.getManagerByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found");
        }

        return manager;
    }
}
