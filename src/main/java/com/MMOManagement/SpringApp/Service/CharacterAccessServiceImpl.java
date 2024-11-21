package com.MMOManagement.SpringApp.Service;

import com.MMOManagement.SpringApp.Model.Game.Characters.AtrybutyPostaci;
import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import com.MMOManagement.SpringApp.Model.Game.Characters.PostacFizyczna;
import com.MMOManagement.SpringApp.Model.Game.Characters.PostacMagiczna;
import com.MMOManagement.SpringApp.Model.Users.Moderation.MistrzGry;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;
import com.MMOManagement.SpringApp.Repository.Game.Characters.AtrybutyPostaciRepository;
import com.MMOManagement.SpringApp.Repository.Game.Characters.PostacFizycznaRepository;
import com.MMOManagement.SpringApp.Repository.Game.Characters.PostacMagicznaRepository;
import com.MMOManagement.SpringApp.Repository.Game.Characters.PostacRepository;
import com.MMOManagement.SpringApp.Repository.Game.Items.ArtefaktRepository;
import com.MMOManagement.SpringApp.Repository.Game.Items.PrzedmiotwEkwipunkuRepository;
import com.MMOManagement.SpringApp.Repository.Users.Moderation.MistrzGryRepository;
import com.MMOManagement.SpringApp.Repository.Users.Moderation.ModeratorCzatuRepository;
import com.MMOManagement.SpringApp.Repository.Users.Playerbase.GraczRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterAccessServiceImpl implements CharacterAccessService{
    @Autowired
    private PrzedmiotwEkwipunkuRepository przedmiotwEkwipunkuRepository;
    @Autowired
    private MistrzGryRepository mistrzGryRepository;
    @Autowired
    private PostacRepository postacRepository;
    @Autowired
   private GraczRepository graczRepository;
    @Autowired
    private ModeratorCzatuRepository moderatorCzatuRepository;
@Autowired
private AtrybutyPostaciRepository atrybutyPostaciRepository;
//@Autowired
//private PostacFizycznaRepository postacFizycznaRepository;
@Autowired
private PostacMagicznaRepository postacMagicznaRepository;
@Autowired
private ArtefaktRepository artefaktRepository;



    @Override
    public List<ModeratorCzatu> getModerators() {
        return moderatorCzatuRepository.findAll();
    }

    @Override
    public Optional <ModeratorCzatu> getModerator(long id) {
        return moderatorCzatuRepository.findById(id);
    }

    @Override
    public Optional<MistrzGry> getMistrzGry(long id) {
        return mistrzGryRepository.findById(id);
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

    @Transactional
    @Override
    public void deleteCharacter(Postac postac) {

            for (int i = 0; i < postac.getPrzedmioty().size(); i++) {
                przedmiotwEkwipunkuRepository.delete(postac.getPrzedmioty().get(i));
            }
            for (int i = 0; i < postac.getAtrybuty().size(); i++) {
                atrybutyPostaciRepository.delete(postac.getAtrybuty().get(i));
            }

            postac.getPrzedmioty().clear();
            postac.getAtrybuty().clear();

            if(postac.getAspekt().getClass()== PostacMagiczna.class){
                PostacMagiczna postacMagiczna = (PostacMagiczna) postac.getAspekt();
                postacMagiczna.getZaklecia().clear();
                postacMagicznaRepository.save(postacMagiczna);
            }
            else if (postac.getAspekt().getClass()== PostacFizyczna.class){
                PostacFizyczna postacFizyczna = (PostacFizyczna) postac.getAspekt();
                for(String key: postacFizyczna.getArtefakty().keySet()){
                    artefaktRepository.delete(postacFizyczna.getArtefakty().get(key));
                }
                postacFizyczna.getArtefakty().clear();
            }

    postacRepository.delete(postac);
    }
    @Transactional
    @Override
    public void setRelation(Gracz gracz, ModeratorCzatu moderatorCzatu) {
    gracz.setCzyMozeUzywacCzat();
    gracz.setModeratorCzatu(moderatorCzatu);
    gracz.setDataZmianyCzatu(new Date());
    graczRepository.save(gracz);
    }
}
