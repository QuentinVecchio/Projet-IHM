package com.insa_lyon.restin.Modeles;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class DataSingleton {
    private static DataSingleton instance = null;

    private ArrayList<Restaurant> restaurants = null;

    private DataSingleton() {
        this.initData();
    }

    public static DataSingleton getInstance() {
        if(DataSingleton.instance == null) {
            DataSingleton.instance = new DataSingleton();
        }
        return DataSingleton.instance;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public Restaurant getRestaurant(int position) {
        return this.restaurants.get(position);
    }

    public int getRestaurantPosition(Restaurant restaurant) {
        for(int i = 0; i < this.restaurants.size(); i++) {
            if(restaurants.get(i).equals(restaurant)){
                return i;
            }
        }
        return -1;
    }

    private void initData() {
        try {
            this.restaurants = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);

            //Restaurant RU Jussieu
            ArrayList<Avis> avisRUJussieu = new ArrayList<>();
            avisRUJussieu.add(new Avis("Plein de choix de plat", 4, dateFormat.parse("01/01/2017 12:50")));
            avisRUJussieu.add(new Avis("Très bon rapport qualité prix, je recommande !", 5, dateFormat.parse("01/01/2017 13:13")));
            avisRUJussieu.add(new Avis("Un peu trop de monde vers 12h..", 3, dateFormat.parse("06/01/2017 13:58")));
            avisRUJussieu.add(new Avis("Franchement. Un repas de midi avec entrée, plat et dessert a 4,50 !! D'accord ce n'est pas de la cuisine gastronomique mais de la cuisine familiale améliorée. Le chef se démène pour varier les goûts, les présentations et les saveurs des quatre coins du monde. Seul bémol il n'y a pas de yaourts non sucrés.", 5, dateFormat.parse("06/01/2017 13:58")));
            avisRUJussieu.add(new Avis("Nickel", 4, dateFormat.parse("08/01/2017 13:00")));
            avisRUJussieu.add(new Avis("Pour 3euros 20 le repas avec une entrée, un plat, un dessert parmi des pizzas, viandes, légumes habituellement de qualité, il n'y à pas grand chose à dire, le rapport qualité-prix rend le restaurant universitaire excellent. En plus le personnel est vraiment gentil ce qui change des cuisinières scolaires tirant la tête durant tout le service !", 4, dateFormat.parse("08/01/2017 13:50")));
            avisRUJussieu.add(new Avis("Jamais de pizza apres 12h...", 1, dateFormat.parse("10/01/2017 12:50")));



            //Menu RU Jussieu
            ArrayList<String> entreeJussieu = new ArrayList<>();
            ArrayList<String> platJussieu = new ArrayList<>();
            ArrayList<String> dessertJussieu = new ArrayList<>();
            entreeJussieu.add("Salade Lyonnaise");
            entreeJussieu.add("Macédoine");
            platJussieu.add("Hamburger du chef");
            platJussieu.add("Bavette du chef");
            platJussieu.add("Pizza");
            dessertJussieu.add("Crème brulée");
            dessertJussieu.add("Clémentine");

            Menu menuMidiJussieu = new Menu(entreeJussieu, platJussieu, dessertJussieu);

            TreeMap<Double, Integer> affluenceMidiJussieu = new TreeMap<>();

            affluenceMidiJussieu.put(11.5, 4);
            affluenceMidiJussieu.put(12.0, 15);
            affluenceMidiJussieu.put(12.5, 25);
            affluenceMidiJussieu.put(12.8, 22);
            affluenceMidiJussieu.put(12.9, 16);
            affluenceMidiJussieu.put(13.0, 11);
            affluenceMidiJussieu.put(13.5, 8);
            affluenceMidiJussieu.put(14.0, 2);

            Restaurant ruJussieu = new Restaurant("RU Jussieu", 45.780691, 4.876519, avisRUJussieu,
                    null, menuMidiJussieu, null, null, affluenceMidiJussieu, null, 3.75);
            this.restaurants.add(ruJussieu);

            //Restaurant l'olivier
            ArrayList<Avis> avisOlivier = new ArrayList<>();
            avisOlivier.add(new Avis("Toujours des pizzas, super !", 5, dateFormat.parse("01/01/2017 12:50")));
            avisOlivier.add(new Avis("Plus cher que le RU Jussieu pour une moins bonne quelité, dommage...", 2, dateFormat.parse("02/01/2017 13:50")));
            avisOlivier.add(new Avis("Pas très bon..", 2, dateFormat.parse("02/01/2017 14:03")));
            avisOlivier.add(new Avis("Peu de choix..", 3, dateFormat.parse("03/01/2017 12:12")));
            avisOlivier.add(new Avis("Aujourd'hui j'ai eu une tartine savoyarde, super bon. Dommage qu'ils n'en proposent pas plus souvent.", 4, dateFormat.parse("04/01/2017 12:34")));
            avisOlivier.add(new Avis("Très abordable niveau prix.", 4, dateFormat.parse("05/01/2017 14:01")));

            ArrayList<String> entreeOlivier = new ArrayList<>();
            ArrayList<String> platOlivier = new ArrayList<>();
            ArrayList<String> dessertOlivier = new ArrayList<>();
            ArrayList<String> produitOlivier = new ArrayList<>();
            ArrayList<String> boissonOlivier = new ArrayList<>();

            entreeOlivier.add("Salade");
            entreeOlivier.add("Charcuterie");
            platOlivier.add("Cordon bleu - Riz");
            platOlivier.add("Saucisse lentille");
            dessertOlivier.add("Yaourt");
            dessertOlivier.add("Banane");

            Menu menuMidiOlivier = new Menu(entreeOlivier, platOlivier, dessertOlivier);

            produitOlivier.add("Croissant");
            produitOlivier.add("Pain au chocolat");
            boissonOlivier.add("Café");
            boissonOlivier.add("Jus d'orange");

            MenuMatin menuMatinOlivier = new MenuMatin(produitOlivier, boissonOlivier);

            TreeMap<Double, Integer> affluenceMidiOlivier = new TreeMap<>();
            TreeMap<Double, Integer> affluenceMatinOlivier = new TreeMap<>();

            affluenceMidiOlivier.put(11.5, 4);
            affluenceMidiOlivier.put(12.0, 10);
            affluenceMidiOlivier.put(12.5, 23);
            affluenceMidiOlivier.put(12.8, 28);
            affluenceMidiOlivier.put(12.9, 16);
            affluenceMidiOlivier.put(13.0, 18);
            affluenceMidiOlivier.put(13.5, 12);
            affluenceMidiOlivier.put(14.0, 6);

            affluenceMatinOlivier.put(7.0, 2);
            affluenceMatinOlivier.put(7.25, 4);
            affluenceMatinOlivier.put(7.5, 6);
            affluenceMatinOlivier.put(7.75, 2);
            affluenceMatinOlivier.put(8.0, 1);


            Restaurant olivier = new Restaurant("L'olivier", 45.784221, 4.874811, avisOlivier,
                    menuMatinOlivier, menuMidiOlivier, null, affluenceMatinOlivier,
                    affluenceMidiOlivier, null, 4.25);
            this.restaurants.add(olivier);

            //Restaurant le Prevert
            ArrayList<Avis> avisPrevert = new ArrayList<>();
            avisPrevert.add(new Avis("Possibilité de prendre à emporté, c'est pratique.", 3, dateFormat.parse("04/01/2017 19:50")));
            avisPrevert.add(new Avis("Du choix et assez bon pour un restaurant universitaire. Je recommande.", 4, dateFormat.parse("06/01/2017 13:08")));
            avisPrevert.add(new Avis("Aujourd'hui j'ai pris du poisson.. J'avais l'impression d'avoir tout sauf du poisson.. Beurk..", 1, dateFormat.parse("07/01/2017 12:03")));
            avisPrevert.add(new Avis("Un peu radin sur les quantités...", 1, dateFormat.parse("08/01/2017 11:57")));
            avisPrevert.add(new Avis("Trop de monde à partir de midi... J'ai fait la queue pendant 30 minutes aujourd'hui..", 1, dateFormat.parse("08/01/2017 13:44")));
            avisPrevert.add(new Avis("Bof..", 2, dateFormat.parse("09/01/2017 12:32")));
            avisPrevert.add(new Avis("Je suis satisfait de ce resto U, je trouve que c'est vraiment bien.", 4, dateFormat.parse("010/01/2017 12:56")));

            ArrayList<String> entreePrevert = new ArrayList<>();
            ArrayList<String> platPrevert = new ArrayList<>();
            ArrayList<String> dessertPrevert = new ArrayList<>();
            ArrayList<String> produitPrevert = new ArrayList<>();
            ArrayList<String> boissonPrevert = new ArrayList<>();

            entreePrevert.add("Saucisson");
            entreePrevert.add("Carottes râpées");
            platPrevert.add("Steak frites");
            platPrevert.add("Quiche lorraine");
            dessertPrevert.add("Liégeois");
            dessertPrevert.add("Orange");

            Menu menuSoirPrevert = new Menu(entreePrevert, platPrevert, dessertPrevert);

            produitPrevert.add("Croissant");
            produitPrevert.add("Pain au chocolat");
            boissonPrevert.add("Café");
            boissonPrevert.add("Jus d'orange");

            MenuMatin menuMatinPrevert = new MenuMatin(produitPrevert, boissonPrevert);

            TreeMap<Double,Integer> affluenceSoirPrevert = new TreeMap<>();
            TreeMap<Double,Integer> affluenceMatinPrevert = new TreeMap<>();

            affluenceSoirPrevert.put(18.5, 2);
            affluenceSoirPrevert.put(18.75, 4);
            affluenceSoirPrevert.put(19.0, 9);
            affluenceSoirPrevert.put(19.25, 12);
            affluenceSoirPrevert.put(19.50, 14);
            affluenceSoirPrevert.put(19.75, 10);
            affluenceSoirPrevert.put(20.0, 7);
            affluenceSoirPrevert.put(20.25, 2);

            affluenceMatinPrevert.put(7.0, 2);
            affluenceMatinPrevert.put(7.25, 3);
            affluenceMatinPrevert.put(7.5, 5);
            affluenceMatinPrevert.put(7.75, 4);
            affluenceMatinPrevert.put(8.0, 3);
            affluenceMatinPrevert.put(8.25, 3);

            Restaurant prevert = new Restaurant("Le Prevert", 45.781173, 4.873638, avisPrevert,
                    menuMatinPrevert, null, menuSoirPrevert, affluenceMatinPrevert, null, affluenceSoirPrevert, 3.5);
            this.restaurants.add(prevert);

            //Restaurant le grillon
            ArrayList<Avis> avisGrillon = new ArrayList<>();
            avisGrillon.add(new Avis("Très déçu...", 1, dateFormat.parse("02/01/2017 13:50")));
            avisGrillon.add(new Avis("Repas satisfaisant.", 3, dateFormat.parse("03/01/2017 12:54")));
            avisGrillon.add(new Avis("Super", 5, dateFormat.parse("06/01/2017 12:50")));
            avisGrillon.add(new Avis("Pas trop de monde avant midi, ça fait de ne pas attendre !", 4, dateFormat.parse("07/01/2017 12:53")));
            avisGrillon.add(new Avis("Ca fait une semaine que je viens ici, je ne suis pas déçu, c'est bon.", 5, dateFormat.parse("07/01/2017 14:50")));
            avisGrillon.add(new Avis("Ils sont généreux sur les quantités.", 4, dateFormat.parse("09/01/2017 14:10")));
            avisGrillon.add(new Avis("Le choix, possibilité de faire des repas équilibré et pas cher. Je reviendrai!", 4, dateFormat.parse("10/01/2017 15:23")));

            ArrayList<String> entreeGrillon = new ArrayList<>();
            ArrayList<String> platGrillon = new ArrayList<>();
            ArrayList<String> dessertGrillon = new ArrayList<>();

            entreeGrillon.add("Lentilles");
            entreeGrillon.add("Céleri");
            platGrillon.add("Choucroute");
            platGrillon.add("Flammekueche");
            dessertGrillon.add("Flan");
            dessertGrillon.add("Brownie");

            Menu menuMidiGrillon = new Menu(entreeGrillon, platGrillon, dessertGrillon);

            ArrayList<String> entreeSoirGrillon = new ArrayList<>();
            ArrayList<String> platSoirGrillon = new ArrayList<>();
            ArrayList<String> dessertSoirGrillon = new ArrayList<>();

            entreeSoirGrillon.add("Friand");
            entreeSoirGrillon.add("Gazpacho à la citrouille");
            platSoirGrillon.add("Blanquette de veau - riz");
            platSoirGrillon.add("Cassoulet");
            dessertSoirGrillon.add("Compote de pomme");
            dessertSoirGrillon.add("Tarte au citron");

            Menu menuSoirGrillon = new Menu(entreeSoirGrillon, platSoirGrillon, dessertSoirGrillon);


            ArrayList<String> produitGrillon = new ArrayList<>();
            ArrayList<String> boissonGrillon = new ArrayList<>();

            produitGrillon.add("Croissant");
            produitGrillon.add("Brioche");
            boissonGrillon.add("Chocolat chaud");
            boissonGrillon.add("Thé");

            MenuMatin menuMatinGrillon = new MenuMatin(produitGrillon, boissonGrillon);

            TreeMap<Double,Integer> affluenceSoirGrillon = new TreeMap<>();
            TreeMap<Double,Integer> affluenceMidiGrillon = new TreeMap<>();
            TreeMap<Double,Integer> affluenceMatinGrillon = new TreeMap<>();

            affluenceSoirGrillon.put(18.5, 2);
            affluenceSoirGrillon.put(18.75, 4);
            affluenceSoirGrillon.put(19.0, 9);
            affluenceSoirGrillon.put(19.25, 12);
            affluenceSoirGrillon.put(19.50, 14);
            affluenceSoirGrillon.put(19.75, 10);
            affluenceSoirGrillon.put(20.0, 7);
            affluenceSoirGrillon.put(20.25, 2);

            affluenceMatinGrillon.put(7.0, 2);
            affluenceMatinGrillon.put(7.25, 3);
            affluenceMatinGrillon.put(7.5, 5);
            affluenceMatinGrillon.put(7.75, 4);
            affluenceMatinGrillon.put(8.0, 3);
            affluenceMatinGrillon.put(8.25, 3);

            affluenceMidiGrillon.put(11.5, 6);
            affluenceMidiGrillon.put(12.0, 8);
            affluenceMidiGrillon.put(12.5, 16);
            affluenceMidiGrillon.put(12.8, 10);
            affluenceMidiGrillon.put(12.9, 14);
            affluenceMidiGrillon.put(13.0, 12);
            affluenceMidiGrillon.put(13.5, 10);
            affluenceMidiGrillon.put(14.0, 5);
            affluenceMidiGrillon.put(14.25, 3);

            Restaurant grillon = new Restaurant("Le Grillon", 45.783927, 4.875049, avisGrillon,
                    menuMatinGrillon, menuMidiGrillon, menuSoirGrillon, affluenceMatinGrillon,
                    affluenceMidiGrillon, affluenceSoirGrillon, 3.70);
            this.restaurants.add(grillon);

            //Restaurant la Roulotte dorée
            ArrayList<Avis> avisRoulotteDoree = new ArrayList<>();
            avisRoulotteDoree.add(new Avis("Excellent rapport qualité/prix.", 5, dateFormat.parse("02/01/2017 11:50")));
            avisRoulotteDoree.add(new Avis("Vendeur très sympathique. Et les sandwichs sont très bon.", 5, dateFormat.parse("04/01/2017 13:46")));
            avisRoulotteDoree.add(new Avis("Carte de fidélité, au bout de dix menus achetés, un menu offert, cool!", 5, dateFormat.parse("05/01/2017 12:12")));
            avisRoulotteDoree.add(new Avis("Très bon !", 4, dateFormat.parse("05/01/2017 12:23")));
            avisRoulotteDoree.add(new Avis("Excellent !", 4, dateFormat.parse("05/01/2017 12:14")));
            avisRoulotteDoree.add(new Avis("Je recommande.", 5, dateFormat.parse("07/01/2017 14:54")));
            avisRoulotteDoree.add(new Avis("A proximité du campus de la doua, pratique ! En plus c'est bon et peu cher !", 5, dateFormat.parse("09/01/2017 14:37")));
            avisRoulotteDoree.add(new Avis("On est servi très rapidement, je recommande.", 4, dateFormat.parse("10/01/2017 13:18")));
            avisRoulotteDoree.add(new Avis("Tres bon sandwich, le menu étudiant vaut le coup : boisson + sandwich pour seulement 5€.", 5, dateFormat.parse("10/01/2017 12:28")));



            ArrayList<String> entreeRoulotte = new ArrayList<>();
            ArrayList<String> platRoulotte = new ArrayList<>();
            ArrayList<String> dessertRoulotte = new ArrayList<>();

            entreeRoulotte.add("Salade d'endive");
            entreeRoulotte.add("Foie gras");
            platRoulotte.add("Saumon fumé à la marseillaise");
            platRoulotte.add("Bouillabaisse");
            platRoulotte.add("Moules marinées");
            dessertRoulotte.add("Calisson");
            dessertRoulotte.add("Brioche");

            Menu menuSoirRoulotte = new Menu(entreeRoulotte, platRoulotte, dessertRoulotte);

            TreeMap<Double,Integer> affluenceSoirRoulotte = new TreeMap<>();

            affluenceSoirRoulotte.put(19.0, 6);
            affluenceSoirRoulotte.put(19.50, 8);
            affluenceSoirRoulotte.put(20.0, 12);
            affluenceSoirRoulotte.put(20.25, 16);
            affluenceSoirRoulotte.put(20.50, 15);
            affluenceSoirRoulotte.put(20.75, 13);
            affluenceSoirRoulotte.put(21.0, 9);
            affluenceSoirRoulotte.put(21.5, 7);
            affluenceSoirRoulotte.put(21.75, 6);

            Restaurant roulotteDoree = new Restaurant("La Roulotte Dorée", 45.782565, 4.876553,
                    avisRoulotteDoree, null, null, menuSoirRoulotte, null, null, affluenceSoirRoulotte, 5.0);
            this.restaurants.add(roulotteDoree);

            //Restaurant Ali Baba Kebab
            ArrayList<Avis> avisNinkasi = new ArrayList<>();
            avisNinkasi.add(new Avis("J'ai commandé deux fois au Ninkasi La Doua avec l'appli Deliveroo. La première fois ma commande avait d'abord été acceptée puis finalement annulée par le resto au bout de 20 min sans explication. J'ai décidé de leur donner une 2eme chance il y a moins d'une semaine... Résultat les frites sont arrivées froides (mais totalement, à se demander si elles avaient même chauffées) alors que le livreur a mis 5 min pour venir. La viande du burger froide également et pas cuite. Horrible et immangeable je n'avais jamais vu ca ! A oublier.", 1, dateFormat.parse("01/01/2017 12:49")));
            avisNinkasi.add(new Avis("La nourriture est très bonne et copieuse, avec en plus la possibilité de créer son propre Burger! Bonne ambiance avec des groupes qui se produisent ! Parfait entre amis. Attention à la forte affluence.", 1, dateFormat.parse("01/01/2017 13:50")));
            avisNinkasi.add(new Avis("Dans le quartier étudiant voici un endroit super sympa où l on goûte des bières brassées sur place qui accompagnent des plats simples, frais et sympas. Les serveurs sont très commerciaux, efficaces et ont le sens du service aux clients. Dans ce quartier c est une adresse à tester sans hésiter.", 4, dateFormat.parse("11/01/2017 11:50")));
            avisNinkasi.add(new Avis("Hier je suis venue manger avec ma famille. Les milkshakes sont délicieux , adresse à recommander.", 5, dateFormat.parse("04/01/2017 11:54")));
            avisNinkasi.add(new Avis("Nous avons voulu y manger le vendredi midi mais après 45 minutes d'attente et 3 relances au serveur tjs pas de prise de commande. Service déplorable. Nous avons quitté la table dans la plus grande ignorance.", 1, dateFormat.parse("07/01/2017 12:35")));
            avisNinkasi.add(new Avis("Repas, service et rapport qualité prix superbes. De plus, point positif pour les végétariens grâce aux repas veggie dans chaque catégories.", 4, dateFormat.parse("06/01/2017 14:50")));
            avisNinkasi.add(new Avis("Très large choix de bières locales. Une bière à thème chaque mois. Salades complètes et hamburgers maison à composer vous même. Service et accueil ok.", 4, dateFormat.parse("09/01/2017 15:40")));
            avisNinkasi.add(new Avis("Un déjeuner qui a été rapide et correct avec des plats locaux. Le burger est pas mal. Il mériterait un pain un peu plus frais. Les frites sont très bonnes. La bière est super.", 4, dateFormat.parse("10/01/2017 11:59")));
            avisNinkasi.add(new Avis("Frite maison ! Excellentes !", 5, dateFormat.parse("04/01/2017 13:24")));


            ArrayList<String> platKebab = new ArrayList<>();
            ArrayList<String> dessertKebab = new ArrayList<>();

            platKebab.add("Chiche kebab");
            platKebab.add("Chiche taouk");
            platKebab.add("Frites");
            dessertKebab.add("Cornes de gazelle");

            Menu menuMidiKebab = new Menu(null, platKebab, dessertKebab);

            ArrayList<String> platSoirKebab = new ArrayList<>();
            ArrayList<String> dessertSoirKebab = new ArrayList<>();

            platSoirKebab.add("Chiche kebab");
            platSoirKebab.add("Americano");
            platSoirKebab.add("Cordon bleu - frites");
            dessertSoirKebab.add("Loukoum");

            Menu menuSoirKebab = new Menu(null, platSoirKebab, dessertSoirKebab);

            TreeMap<Double,Integer> affluenceSoirNinka = new TreeMap<>();
            TreeMap<Double,Integer> affluenceMidiNinka = new TreeMap<>();

            affluenceSoirNinka.put(18.5, 7);
            affluenceSoirNinka.put(18.75, 9);
            affluenceSoirNinka.put(19.0, 12);
            affluenceSoirNinka.put(19.25, 15);
            affluenceSoirNinka.put(19.50, 18);
            affluenceSoirNinka.put(19.75, 14);
            affluenceSoirNinka.put(20.0, 20);
            affluenceSoirNinka.put(20.25, 22);
            affluenceSoirNinka.put(20.50, 18);
            affluenceSoirNinka.put(21.0, 15);
            affluenceSoirNinka.put(21.5, 13);
            affluenceSoirNinka.put(22.0, 9);

            affluenceMidiNinka.put(11.5, 6);
            affluenceMidiNinka.put(12.0, 8);
            affluenceMidiNinka.put(12.5, 16);
            affluenceMidiNinka.put(12.8, 10);
            affluenceMidiNinka.put(12.9, 14);
            affluenceMidiNinka.put(13.0, 12);
            affluenceMidiNinka.put(13.5, 10);

            Restaurant ninkasi = new Restaurant("Ninkasi", 45.778878, 4.872942, avisNinkasi, null,
                    menuMidiKebab, menuSoirKebab, null, affluenceMidiNinka, affluenceSoirNinka, 7.0);
            this.restaurants.add(ninkasi);

            for(Restaurant r : restaurants) {
                r.sortListAvis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
