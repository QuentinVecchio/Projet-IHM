//
//  RestaurantViewAnnotation.swift
//  RestIn
//
//  Created by Quentin Vecchio on 08/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import Cosmos

class RestaurantViewAnnotation : UIView {
    @IBOutlet var ranking: CosmosView!
    @IBOutlet var name: UILabel!
    @IBOutlet var attente: UILabel!
    @IBOutlet var distance: UILabel!
    @IBOutlet weak var buttonInfo: UIButton!
    @IBOutlet weak var prix: UILabel!
    var restaurant : Restaurant!
    
    @IBAction func infoShow(_ sender: Any) {
        print("hello")
    }
}
