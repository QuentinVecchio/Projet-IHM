//
//  RestaurantCell.swift
//  RestIn
//
//  Created by Quentin Vecchio on 06/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import Cosmos

class RestaurantCell : UITableViewCell {
    @IBOutlet weak var restaurantLabel: UILabel!
    @IBOutlet weak var attenteLabel: UILabel!
    @IBOutlet weak var distanceLabel: UILabel!
    @IBOutlet weak var avisLabel: UILabel!
    @IBOutlet weak var rankingView: CosmosView!

    @IBOutlet weak var prixLabel: UILabel!
}
