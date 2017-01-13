//
//  FilterViewController.swift
//  RestIn
//
//  Created by Quentin Vecchio on 11/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import Cosmos

class FilterViewController : UIViewController, UITableViewDelegate, UITableViewDataSource, UISearchBarDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var prixMoyenSlider: RangeSlider!
    @IBOutlet weak var tpsAttenteSlider: RangeSlider!
    @IBOutlet weak var distanceSlider: RangeSlider!
    @IBOutlet weak var ranking: CosmosView!
    @IBOutlet weak var prixMinTextField: UITextField!
    @IBOutlet weak var prixMaxTextField: UITextField!
    @IBOutlet weak var tpsMinTextField: UITextField!
    @IBOutlet weak var tpsMaxTextField: UITextField!
    @IBOutlet weak var distanceMinTextField: UITextField!
    @IBOutlet weak var distanceMaxTextField: UITextField!
    
    var restaurants : [Restaurant]!
    var searchActive : Bool = false
    var filteredRestaurants = [Restaurant]()
    var keyboardIsUp = false
    var originPoint = CGPoint()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "Filtre"
        tableView.dataSource = self
        tableView.delegate = self
        searchBar.delegate = self
        restaurants = RestaurantFactory.getRestaurants()
        prixMoyenSlider.addTarget(self, action: #selector(FilterViewController.prixSliderValuechanged(_:)), for: .valueChanged)
        prixMoyenSlider.minimumValue = 0
        prixMoyenSlider.maximumValue = 20
        tpsAttenteSlider.addTarget(self, action: #selector(FilterViewController.tpsSliderValuechanged(_:)), for: .valueChanged)
        tpsAttenteSlider.minimumValue = 0
        tpsAttenteSlider.maximumValue = 60
        distanceSlider.addTarget(self, action: #selector(FilterViewController.distanceSliderValuechanged(_:)), for: .valueChanged)
        distanceSlider.minimumValue = 0
        distanceSlider.maximumValue = 1500
        NotificationCenter.default.addObserver(self, selector: #selector(FilterViewController.keyboardWillShow), name:NSNotification.Name.UIKeyboardWillShow, object: nil);
        NotificationCenter.default.addObserver(self, selector: #selector(FilterViewController.keyboardWillHide), name:NSNotification.Name.UIKeyboardWillHide, object: nil);
        defaultAction(self)
    }

    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        searchActive = true
    }
    
    func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        searchActive = false
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchActive = false
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchActive = false
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        filteredRestaurants = restaurants.filter { restaurant in
            return restaurant.name!.lowercased().contains(searchText.lowercased())
        }
        if(filteredRestaurants.count == 0){
            searchActive = false;
        } else {
            searchActive = true;
        }
        self.tableView.reloadData()
    }
    
    func keyboardWillShow(notification: NSNotification) {
        if let keyboardSize = (notification.userInfo?[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue {
            originPoint = searchBar.frame.origin
            searchBar.frame.origin.y = self.view.frame.height - searchBar.frame.height - keyboardSize.height
            keyboardIsUp = true
        }
        
    }
    
    func keyboardWillHide(notification: NSNotification) {
        if keyboardIsUp {
            if let _ = (notification.userInfo?[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue {
                tableView.frame.origin = originPoint
            }
        }
    }
    
    func numberOfSections(in tableView : UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView : UITableView, numberOfRowsInSection section:Int) -> Int {
        if searchActive {
            return filteredRestaurants.count
        }
        return restaurants.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RestaurantCell", for: indexPath) as! RestaurantCell
        if searchActive {
            cell.restaurantLabel.text = filteredRestaurants[indexPath.row].name
            cell.avisLabel.text = "\(filteredRestaurants[indexPath.row].avis.count) avis"
            cell.prixLabel.text = "Prix moyen : \(3.75)€"
            cell.attenteLabel.text = "Attente : \(10) min"
            cell.distanceLabel.text = "\(200) m"
            cell.rankingView.rating = filteredRestaurants[indexPath.row].getNoteMoyenne()
        } else {
            cell.restaurantLabel.text = restaurants[indexPath.row].name
            cell.avisLabel.text = "\(restaurants[indexPath.row].avis.count) avis"
            cell.prixLabel.text = "Prix moyen : \(3.75)€"
            cell.attenteLabel.text = "Attente : \(10) min"
            cell.distanceLabel.text = "\(200) m"
            cell.rankingView.rating = restaurants[indexPath.row].getNoteMoyenne()
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let resultViewController = self.storyboard!.instantiateViewController(withIdentifier: "RestaurantViewController") as! RestaurantViewController
        if searchActive {
            resultViewController.restaurant = filteredRestaurants[indexPath.row]
        } else {
            resultViewController.restaurant = restaurants[indexPath.row]
        }
        self.navigationController?.pushViewController(resultViewController, animated: true)
    }
    
    @IBAction func defaultAction(_ sender: Any) {
        distanceSlider.lowerValue = 300
        distanceSlider.upperValue = 1000
        distanceMinTextField.text = "300"
        distanceMaxTextField.text = "1000"
        tpsAttenteSlider.lowerValue = 20
        tpsAttenteSlider.upperValue = 40
        tpsMinTextField.text = "20"
        tpsMaxTextField.text = "40"
        prixMoyenSlider.lowerValue = 2
        prixMoyenSlider.upperValue = 10
        prixMinTextField.text = "2"
        prixMaxTextField.text = "10"
    }
    
    func prixSliderValuechanged(_ rangeSlider: RangeSlider) {
        prixMinTextField.text = "\(Int(rangeSlider.lowerValue))"
        prixMaxTextField.text = "\(Int(rangeSlider.upperValue))"
    }
    
    func tpsSliderValuechanged(_ rangeSlider: RangeSlider) {
        tpsMinTextField.text = "\(Int(rangeSlider.lowerValue))"
        tpsMaxTextField.text = "\(Int(rangeSlider.upperValue))"
    }
    
    func distanceSliderValuechanged(_ rangeSlider: RangeSlider) {
        distanceMinTextField.text = "\(Int(rangeSlider.lowerValue))"
        distanceMaxTextField.text = "\(Int(rangeSlider.upperValue))"
    }
    
}
