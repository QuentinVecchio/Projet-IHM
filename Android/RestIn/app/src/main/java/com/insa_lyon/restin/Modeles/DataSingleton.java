package com.insa_lyon.restin.Modeles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class DataSingleton {
    private static DataSingleton instance = null;

    private DataSingleton() {

    }

    public static DataSingleton getInstance() {
        if(instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    public List<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        //Restaurant RU Jussieu
        ArrayList<Avis> avisRUJussieu = new ArrayList<Avis>();

        //Menu RU Jussieu
        ArrayList<String> entreeJussieu = new ArrayList<String>();
        ArrayList<String> platJussieu = new ArrayList<String>();
        ArrayList<String> dessertJussieu = new ArrayList<String>();
        entreeJussieu.add("Salade Lyonnaise");
        entreeJussieu.add("Macédoine");
        platJussieu.add("Hamburger du chef");
        platJussieu.add("Bavette du chef");
        platJussieu.add("Pizza");
        dessertJussieu.add("Crème brulée");
        dessertJussieu.add("Clémentine");

        Menu menuMidiJussieu = new Menu(entreeJussieu, platJussieu, dessertJussieu);

        Restaurant ruJussieu = new Restaurant("RU Jussieu", 45.780691, 4.876519, avisRUJussieu, null, menuMidiJussieu, null);
        restaurants.add(ruJussieu);

        //Restaurant l'olivier
        ArrayList<Avis> avisOlivier = new ArrayList<Avis>();

        ArrayList<String> entreeOlivier = new ArrayList<String>();
        ArrayList<String> platOlivier = new ArrayList<String>();
        ArrayList<String> dessertOlivier = new ArrayList<String>();
        ArrayList<String> produitOlivier = new ArrayList<String>();
        ArrayList<String> boissonOlivier = new ArrayList<String>();

        entreeOlivier.add("Salade");
        entreeOlivier.add("Charcuterie");
        platOlivier.add("Cordon bleu - Riz");
        platOlivier.add("Saucisse lentille");
        dessertOlivier.add("Yaourt");
        dessertOlivier.add("Banane");

        Menu menuMidiOlivier = new Menu(entreeOlivier,platOlivier,dessertOlivier);

        produitOlivier.add("Croissant");
        produitOlivier.add("Pain au chocolat");
        boissonOlivier.add("Café");
        boissonOlivier.add("Jus d'orange");

        MenuMatin menuMatinOlivier = new MenuMatin(produitOlivier,boissonOlivier);


        Restaurant olivier = new Restaurant("L'olivier", 45.784221, 4.874811, avisOlivier, menuMatinOlivier, menuMidiOlivier, null);
        restaurants.add(olivier);

        //Restaurant le Prevert
        ArrayList<Avis> avisPrevert = new ArrayList<Avis>();

        ArrayList<String> entreePrevert = new ArrayList<String>();
        ArrayList<String> platPrevert = new ArrayList<String>();
        ArrayList<String> dessertPrevert = new ArrayList<String>();
        ArrayList<String> produitPrevert = new ArrayList<String>();
        ArrayList<String> boissonPrevert = new ArrayList<String>();

        entreePrevert.add("Saucisson");
        entreePrevert.add("Carottes râpées");
        platPrevert.add("Steak frites");
        platPrevert.add("Quiche lorraine");
        dessertPrevert.add("Liégeois");
        dessertPrevert.add("Orange");

        Menu menuSoirPrevert = new Menu(entreePrevert,platPrevert,dessertPrevert);

        produitPrevert.add("Croissant");
        produitPrevert.add("Pain au chocolat");
        boissonPrevert.add("Café");
        boissonPrevert.add("Jus d'orange");

        MenuMatin menuMatinPrevert = new MenuMatin(produitPrevert,boissonPrevert);

        Restaurant prevert = new Restaurant("Le Prevert", 45.781173, 4.873638, avisPrevert, menuMatinPrevert, null, menuSoirPrevert);
        restaurants.add(prevert);

        //Restaurant le grillon
        ArrayList<Avis> avisGrillon = new ArrayList<Avis>();

        ArrayList<String> entreeGrillon = new ArrayList<String>();
        ArrayList<String> platGrillon = new ArrayList<String>();
        ArrayList<String> dessertGrillon = new ArrayList<String>();

        entreeGrillon.add("Lentilles");
        entreeGrillon.add("Céleri");
        platGrillon.add("Choucroute");
        platGrillon.add("Flammekueche");
        dessertGrillon.add("Flan");
        dessertGrillon.add("Brownie");

        Menu menuMidiGrillon = new Menu(entreeGrillon,platGrillon,dessertGrillon);

        ArrayList<String> entreeSoirGrillon = new ArrayList<String>();
        ArrayList<String> platSoirGrillon = new ArrayList<String>();
        ArrayList<String> dessertSoirGrillon = new ArrayList<String>();

        entreeSoirGrillon.add("Friand");
        platSoirGrillon.add("Cassoulet");
        dessertSoirGrillon.add("Compote de pomme");

        Menu menuSoirGrillon = new Menu(entreeSoirGrillon,platSoirGrillon,dessertSoirGrillon);


        ArrayList<String> produitGrillon = new ArrayList<String>();
        ArrayList<String> boissonGrillon = new ArrayList<String>();

        produitGrillon.add("Croissant");
        produitGrillon.add("Brioche");
        boissonGrillon.add("Chocolat chaud");
        boissonGrillon.add("Thé");

        MenuMatin menuMatinGrillon = new MenuMatin(produitGrillon,boissonGrillon);

        Restaurant grillon = new Restaurant("Le Grillon", 45.783927, 4.875049, avisGrillon, menuMatinGrillon, menuMidiGrillon, menuSoirGrillon);
        restaurants.add(grillon);

        //Restaurant la Roulotte dorée
        ArrayList<Avis> avisRoulotteDoree = new ArrayList<Avis>();

        ArrayList<String> entreeRoulotte = new ArrayList<String>();
        ArrayList<String> platRoulotte = new ArrayList<String>();
        ArrayList<String> dessertRoulotte = new ArrayList<String>();

        entreeRoulotte.add("Salade d'endive");
        entreeRoulotte.add("Foie gras");
        platRoulotte.add("Saumon fumé à la marseillaise");
        platRoulotte.add("Bouillabaisse");
        platRoulotte.add("Moules marinées");
        dessertRoulotte.add("Calisson");
        dessertRoulotte.add("Brioche");

        Menu menuSoirRoulotte = new Menu(entreeRoulotte,platRoulotte,dessertRoulotte);

        Restaurant roulotteDoree = new Restaurant("La Roulotte Dorée", 45.782565, 4.876553, avisRoulotteDoree, null, null, menuSoirRoulotte);
        restaurants.add(roulotteDoree);

        //Restaurant Ali Baba Kebab
        ArrayList<Avis> avisNinkasi = new ArrayList<Avis>();


        ArrayList<String> platKebab = new ArrayList<String>();
        ArrayList<String> dessertKebab = new ArrayList<String>();

        platKebab.add("Chiche kebab");
        platKebab.add("Chiche taouk");
        platKebab.add("Frites");
        dessertKebab.add("Cornes de gazelle");

        Menu menuMidiKebab = new Menu(null,platKebab,dessertKebab);

        ArrayList<String> platSoirKebab = new ArrayList<String>();
        ArrayList<String> dessertSoirKebab = new ArrayList<String>();

        platSoirKebab.add("Chiche kebab");
        platSoirKebab.add("Americano");
        platSoirKebab.add("Cordon bleu - frites");
        dessertSoirKebab.add("Loukoum");

        Menu menuSoirKebab = new Menu(null,platSoirKebab,dessertSoirKebab);

        Restaurant ninkasi = new Restaurant("Ninkasi", 45.778878, 4.872942, avisNinkasi, null, menuMidiKebab, menuSoirKebab);
        restaurants.add(ninkasi);

        return restaurants;
    }
}
