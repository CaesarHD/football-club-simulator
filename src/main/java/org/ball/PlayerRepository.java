package org.ball;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
