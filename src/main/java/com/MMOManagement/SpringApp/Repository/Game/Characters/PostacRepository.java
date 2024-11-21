package com.MMOManagement.SpringApp.Repository.Game.Characters;

import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostacRepository extends JpaRepository<Postac,Long> {
    @Query("SELECT AVG(p.lvl) FROM Postac p")
    Double findAverageLevel();
}

