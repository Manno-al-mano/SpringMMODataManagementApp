package com.MMOManagement.SpringApp.Model.Game.Characters;

import com.MMOManagement.SpringApp.Model.Game.Items.Artefakt;
import jakarta.persistence.*;

import java.util.Map;
import java.util.TreeMap;

@Entity
public class PostacFizyczna extends Aspekt{
    @Column(nullable = false)
    private int kondycja;
    public PostacFizyczna(int kondycja){
    this.kondycja=kondycja;
    artefakty= new TreeMap<>();
    }

    @OneToMany(mappedBy = "postacFizyczna", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey(name = "nazwa")
    private Map<String, Artefakt> artefakty ;
    private PostacFizyczna(){}
    public void setKondycja(int kondycja) {
        this.kondycja = kondycja;
    }
    public void addArtefakt(Artefakt artefakt){
        artefakty.put(artefakt.getNazwa(),artefakt);
        if (!postac.getPrzedmioty().contains(artefakt))
            postac.addPrzedmiot(artefakt,1);
        if (artefakt.getPostacFizyczna()==null){
            artefakt.setPostacFizyczna(this);
            return;
        }
        if(!artefakt.getPostacFizyczna().equals(this))
            artefakt.setPostacFizyczna(this);
    }

    public Map<String, Artefakt> getArtefakty() {
        return artefakty;
    }

    public String toString(){
        return "Postać Fizyczna";
    }
}
