package com.MMOManagement.SpringApp.Repository.Users;

import com.MMOManagement.SpringApp.Model.Users.Osoba;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsobaRepository  extends JpaRepository<Osoba, Integer> {
}
