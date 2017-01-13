//
//  RestaurantAnnotation.swift
//  RestIn
//
//  Created by Quentin Vecchio on 08/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import MapKit
import Cosmos

class RestaurantAnnotation: NSObject, MKAnnotation {
    
    var name: String!
    var attente : String!
    var distance : String!
    var ranking : Double!
    var coordinate: CLLocationCoordinate2D
    var prix: String!
    init(coordinate: CLLocationCoordinate2D) {
        self.coordinate = coordinate
        super.init()
    }
    
}
