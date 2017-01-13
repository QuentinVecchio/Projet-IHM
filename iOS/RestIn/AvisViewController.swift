//
//  AvisViewController.swift
//  RestIn
//
//  Created by Quentin Vecchio on 08/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import Cosmos

class AvisViewController : UIViewController, UITableViewDelegate, UITableViewDataSource, UITextViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var ranking: CosmosView!
    @IBOutlet weak var avisTextView: UITextView!
    @IBOutlet weak var publishButton: UIButton!
    @IBOutlet weak var avisView: UIView!
    
    var restaurant : Restaurant!
    var keyboardIsUp = false
    var originPoint = CGPoint()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = restaurant?.name
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 80
        avisTextView.delegate = self
        NotificationCenter.default.addObserver(self, selector: #selector(AvisViewController.keyboardWillShow), name:NSNotification.Name.UIKeyboardWillShow, object: nil);
        NotificationCenter.default.addObserver(self, selector: #selector(AvisViewController.keyboardWillHide), name:NSNotification.Name.UIKeyboardWillHide, object: nil);
    }
    
    override func viewDidAppear(_ animated: Bool) {
        tableView.frame.origin = CGPoint(x: 0, y: 0)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    func numberOfSections(in tableView : UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView : UITableView, numberOfRowsInSection section:Int) -> Int {
        return restaurant.avis.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "AvisCell", for: indexPath) as! AvisCell
        cell.avis.text = restaurant.avis[indexPath.row].avis
        if let n = restaurant.avis[indexPath.row].note {
            cell.ranking.rating = n
        }
        return cell
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    
    func keyboardWillShow(notification: NSNotification) {
        if let keyboardSize = (notification.userInfo?[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue {
            originPoint = avisView.frame.origin
            avisView.frame.origin.y = self.view.frame.height - avisView.frame.height - keyboardSize.height
            keyboardIsUp = true
        }
        
    }
    
    func keyboardWillHide(notification: NSNotification) {
        if keyboardIsUp {
            if let _ = (notification.userInfo?[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue {
                avisView.frame.origin = originPoint
                tableView.frame.origin = CGPoint(x: 0, y: 0)
                keyboardIsUp = false
            }
        }
    }
    
    @IBAction func publishAction(_ sender: Any) {
        self.view.endEditing(true)
        let avis = Avis(avis: avisTextView.text, note: ranking.rating)
        restaurant.avis.insert(avis, at: 0)
        avisTextView.text = ""
        tableView.reloadData()
        tableView.frame.origin = CGPoint(x: 0, y: 0)
    }
}
