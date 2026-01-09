package org.ball.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "transfer_offers")
public class Transfer {
    @Id
    private Long id;

    @ManyToOne

    private Player player;

    @ManyToOne
    private Club newClub;

    private long sum;

    TransferStatus transferStatus;

    public Transfer() {}

    public Transfer(Club newClub, Player player) {
        this.newClub = newClub;
        this.player = player;
        this.sum = 0;
        this.transferStatus = TransferStatus.PLAYER_INTERESTED;
    }

    public Long getId() {
        return id;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public TransferStatus getStatusTransferOffer() {
        return transferStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNewClub(Club newClub) {
        this.newClub = newClub;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTransferStatus(TransferStatus transferStatusOffer) {
        this.transferStatus = transferStatusOffer;
    }

    public Club getNewClub() {
        return newClub;
    }

    public Player getPlayer() {
        return player;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }
}
