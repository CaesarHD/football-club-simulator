package org.ball.controller;

import org.ball.service.ManagerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PutMapping("/transfers/approve{transferId}{offerSum}")
    public void approveTransfer(@PathVariable("transferId") Long transferId,
                                @PathVariable("offerSum") Long offerSum) {
        managerService.makeTransferOffer(transferId, offerSum);
    }

    @PutMapping("/transfers/reject{transferId}")
    public void rejectTransfer(@PathVariable("transferId") Long transferId) {
        managerService.rejectTransfer(transferId);
    }
}
