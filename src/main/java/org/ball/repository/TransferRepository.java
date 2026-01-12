package org.ball.repository;

import org.ball.domain.Club;
import org.ball.domain.Transfer;
import org.ball.domain.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> getAllTransfersByNewClub(Club newClub);

    List<Transfer> findByPlayerId(Long playerId);

    List<Transfer> findAllByNewClub(Club club);

    List<Transfer> findByPlayer_Club(Club club);

    List<Transfer> findByPlayer_ClubAndTransferStatusNot(Club club, TransferStatus status);

}
