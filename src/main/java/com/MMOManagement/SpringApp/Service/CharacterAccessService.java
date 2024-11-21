package com.MMOManagement.SpringApp.Service;

import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import com.MMOManagement.SpringApp.Model.Users.Moderation.MistrzGry;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;

import java.util.List;
import java.util.Optional;

public interface CharacterAccessService {
    List<ModeratorCzatu> getModerators();
    Optional<ModeratorCzatu> getModerator(long id);
    Optional<MistrzGry> getMistrzGry(long id);
    List<Gracz> getPlayers();
    double takeAverageLevel();
    void deleteCharacter(Postac postac);
    void setRelation(Gracz gracz, ModeratorCzatu moderatorCzatu);
}
