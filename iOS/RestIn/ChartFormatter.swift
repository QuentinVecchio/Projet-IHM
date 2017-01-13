//
//  ChartFormatter.swift
//  RestIn
//
//  Created by Quentin Vecchio on 11/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import Foundation
import Charts

class ChartFormatter:NSObject,IAxisValueFormatter{
    var values: [String]!
    func stringForValue(_ value: Double, axis: AxisBase?) -> String {
        return values[Int(value)]
    }
}
