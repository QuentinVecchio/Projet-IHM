//
//  RestaurantTableViewController.swift
//  RestIn
//
//  Created by Quentin Vecchio on 06/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import Pulley

class RestaurantTableViewController : UIViewController, UITableViewDelegate, UITableViewDataSource, UISearchBarDelegate, PulleyDrawerViewControllerDelegate {
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var filterButton: UIButton!
    @IBOutlet weak var selectorView: UIView!
    
    var restaurants = [Restaurant]()
    var searchActive : Bool = false
    var filteredRestaurants = [Restaurant]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.dataSource = self
        tableView.delegate = self
        searchBar.delegate = self
        selectorView.layer.cornerRadius = 5
        searchBar.layer.borderWidth = 0
        searchBar.searchBarStyle = .minimal
        restaurants = RestaurantFactory.getRestaurants()
        filterButton.tintColor = UIColor.white
    }
    
    override func viewWillAppear(_ animated: Bool) {
        searchActive = false
        searchBar.text = ""
        filteredRestaurants = [Restaurant]()
        tableView.reloadData()
    }
    
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        searchActive = true
        if let drawerVC = self.parent as? PulleyViewController {
            drawerVC.setDrawerPosition(position: .open, animated: true)
        }
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
        if let drawer = self.parent as? PulleyViewController {
            drawer.setDrawerPosition(position: .collapsed, animated: true)
            let resultViewController = self.storyboard!.instantiateViewController(withIdentifier: "RestaurantViewController") as! RestaurantViewController
            if searchActive {
                resultViewController.restaurant = filteredRestaurants[indexPath.row]
            } else {
                resultViewController.restaurant = restaurants[indexPath.row]
            }
            self.navigationController?.pushViewController(resultViewController, animated: true)
        }
    }
    
    func collapsedDrawerHeight() -> CGFloat {
        return 68-4.0
    }
    
    func partialRevealDrawerHeight() -> CGFloat {
        return 264.0
    }
    
    func openDrawerHeight() -> CGFloat {
        return view.bounds.size.height
    }
    
    func supportedDrawerPositions() -> [PulleyPosition] {
        return PulleyPosition.all // You can specify the drawer positions you support. This is the same as: [.open, .partiallyRevealed, .collapsed, .closed]
    }
    
    func drawerPositionDidChange(drawer: PulleyViewController) {
        tableView.isScrollEnabled = drawer.drawerPosition == .open
        if drawer.drawerPosition != .open {
            searchBar.resignFirstResponder()
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    
    @IBAction func filterAction(_ sender: Any) {
        let resultViewController = self.storyboard!.instantiateViewController(withIdentifier: "FilterViewController") as! FilterViewController
        self.navigationController?.pushViewController(resultViewController, animated: true)
    }
}
