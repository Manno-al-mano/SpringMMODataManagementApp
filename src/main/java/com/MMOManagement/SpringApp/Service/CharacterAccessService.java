package com.MMOManagement.SpringApp.Service;

import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;

import java.util.List;

public interface CharacterAccessService {
    List<ModeratorCzatu> getModerators();
    List<Gracz> getPlayers();
    double takeAverageLevel();
    void deleteCharacter(Postac postac);
    void checkRelation(Gracz gracz, ModeratorCzatu moderatorCzatu);
}
