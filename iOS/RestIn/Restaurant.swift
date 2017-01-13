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
    var menus = [Int : (String,[Int: (String,[String])])]();
    var affluences = [[Int : (String,Int)]]()
    
    init(name : String, lat : Double, lon : Double, avis : [Avis], menus : [Int : (String,[Int: (String,[String])])], affluences : [[Int : (String,Int)]]) {
        self.avis = avis
        self.name = name
        self.lat = lat
        self.lon = lon
        self.menus = menus
        self.affluences = affluences
    }
    
    func getNoteMoyenne() -> Double {
        if avis.count == 0 {
            return 0
        } else {
            var sum : Double = 0
            for a in avis {
                if let note = a.note {
                    sum += note
                }
            }
            return sum/Double(avis.count)
        }
    }
    
    func getMenus() -> [Int : (String,[Int: (String,[String])])] {
        return menus
    }
    
    func getAffluences() -> [[Int : (String,Int)]] {
        return affluences
    }
}
