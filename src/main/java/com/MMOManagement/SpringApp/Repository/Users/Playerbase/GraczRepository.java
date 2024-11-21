package com.MMOManagement.SpringApp.Repository.Users.Playerbase;

import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraczRepository  extends JpaRepository<Gracz,Long> {
}
