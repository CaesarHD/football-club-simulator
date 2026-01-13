package org.ball.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "transfers")
public class Transfer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "new_club_id")
    private Club newClub;

    @Column(name = "transfer_sum")
    private long sum;

    @Column(name = "transfer_status")
    @Enumerated(EnumType.STRING)
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

    public void setOfferSum(long sum) {
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


    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", player=" + player.getName() +
                ", newClub=" + newClub.getName() +
                ", sum=" + sum +
                ", transferStatus=" + transferStatus +
                '}';
    }
}
