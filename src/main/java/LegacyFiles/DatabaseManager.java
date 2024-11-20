package LegacyFiles;

//import com.MMOManagement.SpringApp.Constants.Messages;
//import MMOdata.Constants.PersistenceNames;
import com.MMOManagement.SpringApp.Constants.Messages;
import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import com.MMOManagement.SpringApp.Model.Game.Characters.PostacFizyczna;
import com.MMOManagement.SpringApp.Model.Game.Characters.PostacMagiczna;
import com.MMOManagement.SpringApp.Model.Users.Moderation.MistrzGry;
import com.MMOManagement.SpringApp.Model.Users.Moderation.ModeratorCzatu;
import com.MMOManagement.SpringApp.Model.Users.Playerbase.Gracz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Calendar;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private EntityManagerFactory emf;
    private EntityManager em;
    private String message;
    private ModeratorCzatu moderatorCzatu;
    private Gracz gracz;

    private DatabaseManager() {
   //     emf = Persistence.createEntityManagerFactory(PersistenceNames.UPDATE);
        em = emf.createEntityManager();

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public String getMessage() {
        return message;
    }

    public ModeratorCzatu getModeratorCzatu() {
        return moderatorCzatu;
    }

    public void setModeratorCzatu(ModeratorCzatu moderatorCzatu) {
        this.moderatorCzatu = moderatorCzatu;
    }

    public Gracz getGracz() {

        return gracz;


    }

    public void setGracz(Gracz gracz) {
        this.gracz = gracz;
    }

    public List<ModeratorCzatu> getModerators() {
        try {
            String jpql = "SELECT m FROM ModeratorCzatu m";
            TypedQuery<ModeratorCzatu> query = em.createQuery(jpql, ModeratorCzatu.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Gracz> getPlayers() {
        try {
            String jpql = "SELECT g FROM Gracz g";
            TypedQuery<Gracz> query = em.createQuery(jpql, Gracz.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //"SELECT AVG(p.level) FROM Postac p"
    public double takeAverageLevel(){
       try {
           String jpql = "SELECT AVG(p.level) FROM Postac p";
           TypedQuery<Double> query = em.createQuery(jpql, Double.class);
           return query.getSingleResult();
       }catch (Exception e) {
           e.printStackTrace();
       }
       return 0.0;
    }

    public void deleteCharacter(Postac postac) {
        em.getTransaction().begin();
        Postac charToDelete = em.find(Postac.class, postac.getId());
        for (int i = 0; i < charToDelete.getPrzedmioty().size(); i++) {
            em.remove(charToDelete.getPrzedmioty().get(i));
        }
        for (int i = 0; i < charToDelete.getAtrybuty().size(); i++) {
            em.remove(charToDelete.getAtrybuty().get(i));
        }

        charToDelete.getPrzedmioty().clear();
       charToDelete.getAtrybuty().clear();
     //  em.merge(charToDelete);

        if(postac.getAspekt().getClass()== PostacMagiczna.class){
            PostacMagiczna postacMagiczna = (PostacMagiczna) charToDelete.getAspekt();
            postacMagiczna.getZaklecia().clear();
            em.merge(postacMagiczna);
        }
       else if (postac.getAspekt().getClass()== PostacFizyczna.class){
            PostacFizyczna postacFizyczna = (PostacFizyczna) charToDelete.getAspekt();
            for(String key: postacFizyczna.getArtefakty().keySet()){
                em.remove(postacFizyczna.getArtefakty().get(key));
            }
            postacFizyczna.getArtefakty().clear();
        }
            // em.merge(charToDelete);
            em.remove(charToDelete);


        em.getTransaction().commit();
        em.clear();
       // message=Messages.DELETED;
    }
    private boolean checkforNull() {
        if (gracz == null || moderatorCzatu == null) {
        //    message = Messages.NULL;
            return true;
        }
        return false;
    }

    public void checkRelation() {
        if (checkforNull()) {
            return;
        }
        if (isModerator()) {
            return;
        }
        if (checkDate()) {
            if (checkMode()) {
                return;
            }
        }

        submit();
    }

    private int assignValue(ModeratorCzatu mode) {
        MistrzGry mg = em.find(MistrzGry.class, mode.getId());
        if (mg == null) {
            return 0;
        }
        switch (mg.getRanga()) {
            case podstawowy:
                return 1;
            case starszy:
                return 2;
            case glowny:
                return 3;
        }
        return 0;
    }


    private boolean checkMode() {
        if (gracz.getModeratorCzatu() == null)
            return false;
        ModeratorCzatu mode = gracz.getModeratorCzatu();
        if (assignValue(moderatorCzatu) >= assignValue(mode))
            return false;

        message = Messages.HIGHRANK;
        return true;
    }

    private void submit() {
        em.getTransaction().begin();
    //    message = Messages.SUCCESS;
        gracz.setDataZmianyCzatu(Calendar.getInstance().getTime());
        if (gracz.getModeratorCzatu() != null) {
            ModeratorCzatu mode = gracz.getModeratorCzatu();
            em.merge(mode);
            mode.removeGracz(gracz);
        }
        gracz.setCzyMozeUzywacCzat();
        gracz.setModeratorCzatu(moderatorCzatu);
        em.merge(gracz);
        em.merge(moderatorCzatu);
        em.getTransaction().commit();


    }

    private boolean checkDate() {
        if(gracz.getDataZmianyCzatu()==null)
            return true;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        return gracz.getDataZmianyCzatu().after(calendar.getTime());
    }

    private boolean isModerator() {
        try {
            ModeratorCzatu moderator = em.find(ModeratorCzatu.class, gracz.getId());
            if (moderator != null) {
            //    message = Messages.MODERATOR;
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }


    public void closeSessionFactory() {
        em.close();
        emf.close();
    }
}