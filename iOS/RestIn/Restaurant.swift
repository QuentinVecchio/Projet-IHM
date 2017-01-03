//
//  Restaurant.swift
//  RestIn
//
//  Created by Quentin Vecchio on 03/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import Foundation

class Restaurant {
    var name : String?
    var lat : Double?
    var lon : Double?
    var avis = [Avis]();
    
    init(name : String, lat : Double, lon : Double, avis : [Avis]) {
        self.avis = avis
        self.name = name
        self.lat = lat
        self.lon = lon
    }
    
    func getNoteMoyenne() -> Double {
        var sum : Double = 0
        for a in avis {
            if let note = a.note {
                sum += note
            }
        }
        return sum/Double(avis.count)
    }
}
