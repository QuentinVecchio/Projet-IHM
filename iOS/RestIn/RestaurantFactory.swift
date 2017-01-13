//
//  RestaurantFactory.swift
//  RestIn
//
//  Created by Quentin Vecchio on 03/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import Foundation

class RestaurantFactory {
    private var restaurants = [Restaurant]()
    
    private static var restaurantFactory: RestaurantFactory = {
        let restaurantFactory = RestaurantFactory()
        //Restaurant RU Jussieu
        let avisRUJussieu : [Avis] = [Avis(avis: "Le meilleur RU de Lyon",note: 4.5),
                                      Avis(avis: "Grand choix de nourriture, mais beaucoup d'attente",note: 4),
                                      Avis(avis: "Le traditionnel burger du chef ❤️",note: 5),
                                      Avis(avis: "Je recommende, j'y manges tous les midis avec mes amis",note: 4),
                                      Avis(avis: "Bien mais à éviter aux heures de pointes",note: 4)]
        var menusRUJussieu = [0 : ("Déjeuner",[0 : ("Entrée",["Salade choux","Salade d'haricot"]),
                                                1 : ("Plats",["Hamburger du chef","Escalope milanaise avec légumes"]),
                                                2 : ("Desserts",["Salade de fruit","Éclair au chocolat"])])]
        let affluencesRUJussieu = [[0: ("11h00",0),1: ("11h30",10), 2 : ("12h00",45), 3:("12h30",30),4 : ("13h00",10), 5:("13h30",5), 6:("14h00",0)]]
        let ruJussieu = Restaurant(name: "RU Jussieu", lat: 45.780691, lon: 4.876519, avis: avisRUJussieu, menus: menusRUJussieu, affluences: affluencesRUJussieu)
        restaurantFactory.restaurants.append(ruJussieu)
        
        //Restaurant l'olivier
        let avisOlivier : [Avis] = [Avis(avis: "Parfait pour manger des pizzas",note: 4.5),
                                    Avis(avis: "La nourriture est pas mauvaise mais le resto coute trop cher",note: 2.5),
                                    Avis(avis: "Enormement d'attente",note: 2.5),
                                    Avis(avis: "Parfait pour ceux qui veulent manger italien",note: 4.25),
                                    Avis(avis: "Le meilleur restaurant de l'INSA",note: 4.5)]
        var menusOlivier = [0 : ("Déjeuner", [0 : ("Entrée",["Salade verte","Salade de pâtes"]),
                                               1 : ("Plats",["Pizza 4 fromages","Pennes à la sauce boognaise"]),
                                               2 : ("Desserts", ["Tiramisu","Yaourt nature"])])]
        let affluencesOlivier = [[0: ("11h00",0),1: ("11h30",20), 2 : ("12h00",30), 3:("12h30",60),4 : ("13h00",50), 5:("13h30",10), 6:("14h00",5)]]
        let olivier = Restaurant(name: "L'olivier", lat: 45.784221, lon: 4.874811, avis: avisOlivier, menus: menusOlivier, affluences: affluencesOlivier)
        restaurantFactory.restaurants.append(olivier)
        
        //Restaurant le Prevert
        let avisPrevert : [Avis] = [Avis(avis: "Cher et pas bon",note: 1),
         Avis(avis: "Pas terrible, je suis tombé malade",note: 0),
         Avis(avis: "Assiettes et couverts en plastique à ce prix ???",note: 0),
         Avis(avis: "Bien pour manger rapidement, ouvert matin, midi et soir",note: 3.5),
         Avis(avis: "Sans commentaires",note: 0.5)]
        var menusPrevert = [0 : ("Petit-déjeuner", [0 : ("Plats", ["Pain-confiture", "céréales", "biscuits"]),
                                                     1 : ("Boissons", ["Café","Lait"])]),
                                 1 : ("Déjeuner", [0 : ("Entrée",["Salade verte","Jambon"]),
                                                    1 : ("Plats",["Lasagne","Hachi-parmentié"]),
                                                    2: ("Desserts", ["Fruits","Yaourt aux fruits"])]),
                                 2 : ("Repas du soir", [0 : ("Entrée",["Salade verte","Salade de pâtes"]),
                                                        1 : ("Plats",["Pizza 4 fromages","Pennes à la sauce boognaise"]),
                                                        2 : ("Desserts", ["Tiramisu","Yaourt nature"])]),
                            ]
        let affluencesPrevert = [[0 : ("6h30",0), 1: ("7h00",30), 2: ("7h30",40), 3 : ("8h00",10), 4 : ("8h30",0)],
                                 [0 : ("11h00",0), 1 : ("11h30",10), 2: ("12h00",45), 3: ("12h30",30), 4 : ("13h00",10), 5 : ("13h30",5), 6 :("14h00",0)],
                                 [0 : ("18h00",20), 1 : ("18h30",40), 2: ("19h00",45), 3 : ("19h30",20), 4 : ("20h00",5), 5 : ("20h30",0)]]
        let prevert = Restaurant(name: "Le Prevert", lat: 45.781173, lon: 4.873638, avis: avisPrevert, menus: menusPrevert, affluences: affluencesPrevert)
        restaurantFactory.restaurants.append(prevert)
        
        //Restaurant le grillon
        let avisGrillon : [Avis] = [Avis(avis: "Trop gras",note: 1.5),
                                    Avis(avis: "La nourriture est pas mauvaise mais le resto coute trop cher",note: 2.5),
                                    Avis(avis: "Enormement d'attente",note: 2.5),
                                    Avis(avis: "Bien mais sans plus",note: 3.5),
                                    Avis(avis: "J'y mange assez souvent avec mes amis",note: 3.5)]
        var menusGrillon = [0 : ("Déjeuner",[0 : ("Entrée",["Salade de carottes","Pâté en croûte"]),
                                              1: ("Plats",["Frites - steak","Frites - Nuggets"]),
                                              2: ("Desserts", ["Donnut","Fruits"])])]
        let affluencesGrillon = [[0: ("11h00",0),1: ("11h30",20), 2 : ("12h00",30), 3:("12h30",60),4 : ("13h00",50), 5:("13h30",10), 6:("14h00",5)]]
        let grillon = Restaurant(name: "Le Grillon", lat: 45.783927, lon: 4.875049, avis: avisGrillon, menus: menusGrillon, affluences: affluencesGrillon)
        restaurantFactory.restaurants.append(grillon)
        
        //Restaurant la Roulotte dorée
        let avisRoulotteDoree : [Avis] = [Avis(avis: "Bonne nourriture, mais beaucoup de monde :(",note: 4),
                                         Avis(avis: "Je suis un habitué !",note: 5),
                                         Avis(avis: "Correct pour manger rapidement",note: 4),
                                         Avis(avis: "Nombreux choix de sandwich",note: 4.75),
                                         Avis(avis: "Parfait quand on a pas le temps entre midi",note: 4.5)]
        var menusRoulotteDoree = [0 : ("Déjeuner", [0 : ("Plats",["Sandwich au thon","Sandwich poulet curry","Sandwich saucisson"]),
                                                     1 : ("Desserts", ["Donnut","Tarte aux pommes","Croissant"])])]
        let affluencesRoulotteDoree = [[0: ("11h00",0),1: ("11h30",20), 2 : ("12h00",60), 3:("12h30",50),4 : ("13h00",30), 5:("13h30",10), 6:("14h00",5)]]
        let roulotteDoree = Restaurant(name: "La Roulotte Dorée", lat: 45.782565, lon: 4.876553, avis: avisRoulotteDoree, menus: menusRoulotteDoree, affluences: affluencesRoulotteDoree)
        restaurantFactory.restaurants.append(roulotteDoree);
        
        //Restaurant Ninkasi
        let avisNinkasi : [Avis] = [Avis(avis: "Super fastfood",note: 5),
                                    Avis(avis: "Je recommande l'hamburger avec les frites maison et une bonne bière",note: 5),
                                    Avis(avis: "Pas mal pour du fast food",note: 5),
                                    Avis(avis: "Possibilité de regarder les matchs de foot avec ses amis, au top",note: 5),
                                    Avis(avis: "Menu étudiant abordable",note: 4.25)]
        var menusNinkasi = [0 : ("Déjeuner", [0 : ("Entrée",["Salade de carottes","Salade verte"]),
                                               1: ("Plats",["Frites - Hamburger","Frites - Panni"]),
                                               2: ("Desserts", ["Donnut","Glace"])]),
                            1 : ("Repas du soir", [0 : ("Plats",["Frites - Hamburger","Frites - Panni","Salade composée","Lasagnes"]),
                                                   1: ("Desserts", ["Donnut","Glace","Brownie"])])]
        let affluencesNinkasi = [[0: ("11h00",10), 1: ("11h30",20), 2: ("12h00",45), 3: ("12h30",50), 4: ("13h00",30), 5: ("13h30",20), 6: ("14h00",5)],
                                 [0 : ("18h00",10), 1 : ("18h30",20), 2 : ("19h00",60), 3 : ("19h30",50) , 4 : ("20h00",30), 5: ("20h30",20), 6: ("21h00",10)]]
        let aliBaba = Restaurant(name: "Ninkasi", lat: 45.778878, lon: 4.872942, avis: avisNinkasi, menus: menusNinkasi, affluences: affluencesNinkasi)
        restaurantFactory.restaurants.append(aliBaba);

        return restaurantFactory
    }()

    class func getRestaurants() -> [Restaurant] {
        return restaurantFactory.restaurants;
    }
}
