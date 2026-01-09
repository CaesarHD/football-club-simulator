package org.ball.repository;

import org.ball.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer,Long> {

    List<Transfer> findBySenderAndReceiverAndSumGreaterThanEqualAndSumLessThanEqual();

    List<Transfer> findTransferOfferByReceiver(Long clubId);

    Transfer save(Transfer transfer);

    Transfer findById(long id);

}
