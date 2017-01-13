//
//  ViewController.swift
//  RestIn
//
//  Created by Quentin Vecchio on 03/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import MapKit
import Pulley

class MapViewController: UIViewController, PulleyPrimaryContentControllerDelegate, CLLocationManagerDelegate, MKMapViewDelegate {
    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var locationButton: UIButton!
    @IBOutlet weak var locationView: UIView!
    
    var locationManager = CLLocationManager()
    var restaurants = [Restaurant]()
    var userLocation : CLLocation?
    var currentlyAnnotation : RestaurantViewAnnotation?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.delegate = self
        locationManager.delegate = self
        mapView.showsUserLocation = true
        locationView.layer.cornerRadius = 5
        locationButton.layer.cornerRadius = 5
        locationView.layer.borderWidth = 1
        locationView.layer.borderColor = UIColor(colorLiteralRed: 222/255, green: 218/255, blue: 210/255, alpha: 1).cgColor
        locationButton.setImage(UIImage(named: "compass")?.tintPicto(UIColor(colorLiteralRed: 0, green: 124/255, blue: 246/255, alpha: 1)), for: UIControlState.normal)
        locationButton.setImage(UIImage(named: "compass")?.tintPicto(UIColor(colorLiteralRed: 0, green: 124/255, blue: 246/255, alpha: 1)), for: UIControlState.selected)
        restaurants = RestaurantFactory.getRestaurants()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        localiseUser()
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
    
    /*func mapView(_ mapView: MKMapView, didDeselect view: MKAnnotationView) {
        for subview in view.subviews  {
            subview.removeFromSuperview()
        }
    }*/
    
    func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {
        if view.annotation is MKUserLocation {
            return
        }
        let restaurantAnnotation = view.annotation as! RestaurantAnnotation
        let views = Bundle.main.loadNibNamed("RestaurantViewAnnotation", owner: nil, options: nil)
        let calloutView = views?[0] as! RestaurantViewAnnotation
        if currentlyAnnotation != nil {
            if currentlyAnnotation?.name.text != restaurantAnnotation.name {
                currentlyAnnotation?.removeFromSuperview()
            } else {
                print("on est bon")
            }
        }
        currentlyAnnotation = calloutView
        calloutView.name.text = restaurantAnnotation.name
        calloutView.distance.text = restaurantAnnotation.distance
        calloutView.attente.text = restaurantAnnotation.attente
        calloutView.ranking.rating = restaurantAnnotation.ranking
        calloutView.prix.text = restaurantAnnotation.prix
        calloutView.center = CGPoint(x: view.bounds.size.width / 2, y: -calloutView.bounds.size.height*0.52)
        view.addSubview(calloutView)
        mapView.setCenter((view.annotation?.coordinate)!, animated: true)
    }
    
    func addRestaurantPin() {
        for r in restaurants {
            if let lat = r.lat, let lon = r.lon, let name = r.name {
                let annotation = RestaurantAnnotation(coordinate: CLLocationCoordinate2D(latitude: lat, longitude: lon))
                annotation.name = name
                annotation.attente = "Attente : \(5) min"
                annotation.distance = "Distane : \(150) m"
                annotation.ranking = r.getNoteMoyenne()
                annotation.prix = "Prix : \(3.75) €"
                mapView.addAnnotation(annotation)
            }
        }
    }
    
    func showRestaurant(_ sender: UITapGestureRecognizer) {
        print("ok")
    }
}

