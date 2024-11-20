package com.MMOManagement.SpringApp.Service;

import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;
import com.MMOManagement.SpringApp.Repository.Game.Characters.PostacRepository;
import com.MMOManagement.SpringApp.Repository.Users.Moderation.ModeratorCzatuRepository;
import com.MMOManagement.SpringApp.Repository.Users.Playerbase.GraczRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterAccessServiceImpl implements CharacterAccessService{

    @Autowired
    private PostacRepository postacRepository;
    @Autowired
   private GraczRepository graczRepository;
    @Autowired
    private ModeratorCzatuRepository moderatorCzatuRepository;




    @Override
    public List<ModeratorCzatu> getModerators() {
        return moderatorCzatuRepository.findAll();
    }

    @Override
    public List<Gracz> getPlayers() {
        return graczRepository.findAll();
    }

    @Override
    public double takeAverageLevel() {
        Double avg = postacRepository.findAverageLevel();
        return avg!=null?avg:0;
    }

    @Override
    public void deleteCharacter(Postac postac) {

    }

    @Override
    public void checkRelation(Gracz gracz, ModeratorCzatu moderatorCzatu) {

    }
}
