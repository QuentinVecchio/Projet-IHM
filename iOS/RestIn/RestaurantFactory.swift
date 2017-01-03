//
//  RestaurantFactory.swift
//  RestIn
//
//  Created by Quentin Vecchio on 03/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import Foundation

class RestaurantFactory {
    private static var restaurantFactory: RestaurantFactory = {
        let restaurantFactory = RestaurantFactory()
        return restaurantFactory
    }()

    class func getRestaurants() -> [Restaurant] {
        var restaurants = [Restaurant]()
        //Restaurant RU Jussieu
        let avisRUJussieu = [Avis]()
        
        let ruJussieu = Restaurant(name: "RU Jussieu", lat: 45.780691, lon: 4.876519, avis: avisRUJussieu)
        restaurants.append(ruJussieu)
        
        //Restaurant l'olivier
        let avisOlivier = [Avis]()
        let olivier = Restaurant(name: "L'olivier", lat: 45.784221, lon: 4.874811, avis: avisOlivier)
        restaurants.append(olivier)
        
        //Restaurant le Prevert
        let avisPrevert = [Avis]()
        
        let prevert = Restaurant(name: "Le Prevert", lat: 45.781173, lon: 4.873638, avis: avisPrevert)
        restaurants.append(prevert)
        
        //Restaurant le grillon
        let avisGrillon = [Avis]()
        
        let grillon = Restaurant(name: "Le Grillon", lat: 45.783927, lon: 4.875049, avis: avisGrillon)
        restaurants.append(grillon)
        
        //Restaurant la Roulotte dorée
        let avisRoulotteDoree = [Avis]()
        
        let roulotteDoree = Restaurant(name: "La Roulotte Dorée", lat: 45.782565, lon: 4.876553, avis: avisRoulotteDoree)
        restaurants.append(roulotteDoree);
        
        //Restaurant Ninkasi
        let avisNinkasi = [Avis]()
        
        let aliBaba = Restaurant(name: "Ninkasi", lat: 45.778878, lon: 4.872942, avis: avisNinkasi)
        restaurants.append(aliBaba);
        
        return restaurants;
    }
}
