//
//  ViewController.swift
//  RestIn
//
//  Created by Quentin Vecchio on 03/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import MapKit

class MapViewController: UIViewController, CLLocationManagerDelegate, MKMapViewDelegate {
    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var locationButton: UIButton!
    @IBOutlet weak var locationView: UIView!
    
    var locationManager = CLLocationManager()
    var timer : Timer?
    var usersAround = [MKAnnotation]()
    var userLocation : CLLocation?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.delegate = self
        locationManager.delegate = self
        locationView.layer.cornerRadius = 5
        locationButton.layer.cornerRadius = 5
        locationView.layer.borderWidth = 1
        locationView.layer.borderColor = UIColor(colorLiteralRed: 222/255, green: 218/255, blue: 210/255, alpha: 1).cgColor
        locationButton.setImage(UIImage(named: "compass")?.tintPicto(UIColor(colorLiteralRed: 0, green: 124/255, blue: 246/255, alpha: 1)), for: UIControlState.normal)
        locationButton.setImage(UIImage(named: "compass")?.tintPicto(UIColor(colorLiteralRed: 0, green: 124/255, blue: 246/255, alpha: 1)), for: UIControlState.selected)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        localiseUser()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        if let timer = timer {
            timer.invalidate()
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func refreshPositionAction(_ sender: Any) {
        localiseUser()
    }
    
    func localiseUser() {
        if CLLocationManager.authorizationStatus() == .authorizedWhenInUse {
            mapView.showsUserLocation = true
            locationManager.startUpdatingLocation()
        } else {
            locationManager.requestWhenInUseAuthorization()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if status == .authorizedWhenInUse {
            locationManager.startUpdatingLocation()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations.first!
        let coordinateRegion = MKCoordinateRegionMakeWithDistance(location.coordinate, 1000, 1000)
        userLocation = location
        mapView.setRegion(coordinateRegion, animated: true)
        locationManager.stopUpdatingLocation()
        addRestaurantPin()
    }
    
    func addRestaurantPin() {
        for r in RestaurantFactory.getRestaurants() {
            if let lat = r.lat, let lon = r.lon {
                let annotation = MKPointAnnotation()
                annotation.coordinate = CLLocationCoordinate2D(latitude: lat, longitude: lon)
                mapView.addAnnotation(annotation)
            }
        }
    }
}

