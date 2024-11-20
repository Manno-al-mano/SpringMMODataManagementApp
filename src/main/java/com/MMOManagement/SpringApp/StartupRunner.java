package com.MMOManagement.SpringApp;

import com.MMOManagement.SpringApp.Service.TableCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
   @Autowired
    TableCreatorService tableCreatorService;
    @Override
    public void run(String... args) throws Exception {
        tableCreatorService.createTable();
    }
}
