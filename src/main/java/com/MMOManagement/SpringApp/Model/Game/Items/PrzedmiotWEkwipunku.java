package com.MMOManagement.SpringApp.Model.Game.Items;


import com.MMOManagement.SpringApp.Model.Game.Characters.Postac;
import jakarta.persistence.*;

@Entity
public class PrzedmiotWEkwipunku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postac_id")
    private Postac postac;

    @ManyToOne
    @JoinColumn(name = "przedmiot_id")
    private Przedmiot przedmiot;


    @Column(nullable = false)
    private int ilosc;
    private PrzedmiotWEkwipunku(){}
    public PrzedmiotWEkwipunku(Postac _postac, Przedmiot _przedmiot, int wrt){
        postac= _postac;
        przedmiot= _przedmiot;
        ilosc = wrt;
    }
}
