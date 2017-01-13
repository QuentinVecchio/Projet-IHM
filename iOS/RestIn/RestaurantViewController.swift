//
//  RestaurantViewController.swift
//  RestIn
//
//  Created by Quentin Vecchio on 06/01/2017.
//  Copyright © 2017 Quentin Vecchio. All rights reserved.
//

import UIKit
import MapKit
import Charts
import Cosmos

class RestaurantViewController : UIViewController, CLLocationManagerDelegate, MKMapViewDelegate, ChartViewDelegate {
    
    @IBOutlet weak var scrollView: UIScrollView!
    
    private let margin : CGFloat = 0
    private var originY : CGFloat = -65
    private var mapViewHeight : CGFloat = 250
    private var chartViewHeight : CGFloat = 200
    
    var restaurant : Restaurant?
    var mapView : MKMapView!
    var locationManager = CLLocationManager()
    var menuScrollView : UIScrollView!
    var chartsScrollView : UIScrollView!
    var avisView : UIView!
    var prixView : UIView!
    var infoView : UIView!
    var lastAvisView : UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = restaurant?.name
        scrollView.minimumZoomScale = 1
        scrollView.zoomScale = 1
        scrollView.maximumZoomScale = 1
        scrollView.contentSize = CGSize(width: view.bounds.size.width, height: view.bounds.size.height)
        buildMapView()
        buildPrixView()
        buildInfoView()
        buildAvisView()
        buildLastAvisView()
        buildChartView()
        buildMenuView()
        scrollView.contentSize = CGSize(width: view.bounds.size.width, height: originY)
    }
    
    override func viewWillDisappear(_ animated: Bool) {

    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func buildMapView() {
        mapView = MKMapView()
        mapView.delegate = self
        locationManager.delegate = self
        mapView.showsUserLocation = true
        mapView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: mapViewHeight)
        originY += margin
        mapView.frame.origin = CGPoint(x: margin, y: originY)
        originY += mapViewHeight
        scrollView.addSubview(mapView)
        localiseUser()
        if let r = restaurant {
            if let lat = r.lat, let lon = r.lon {
                let annotation = MKPointAnnotation()
                annotation.coordinate = CLLocationCoordinate2D(latitude: lat, longitude: lon)
                mapView.addAnnotation(annotation)
            }
        }
    }
    
    func buildPrixView() {
        prixView = UIView()
        let margin1 : CGFloat = 5
        let bar = UIView()
        bar.frame.size = CGSize(width: scrollView.contentSize.width, height: 1)
        bar.backgroundColor = UIColor(red: 185/255, green: 202/255, blue: 205/255, alpha: 1)
        bar.frame.origin = CGPoint(x: 0, y: 0)
        var originYIntern : CGFloat = 1
        let height : CGFloat = 40
        //
        let labelPrix = UILabel()
        labelPrix.text = "Prix : 3.75 €"
        labelPrix.textAlignment = .center
        labelPrix.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: height-2*margin1)
        labelPrix.frame.origin = CGPoint(x: margin1, y: margin1)
        originYIntern += height
        //
        prixView.addSubview(bar)
        prixView.addSubview(labelPrix)
        prixView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: originYIntern + margin1)
        originY += margin
        prixView.frame.origin = CGPoint(x: margin, y: originY)
        originY += originYIntern
        scrollView.addSubview(prixView)
    }
    
    func buildInfoView() {
        infoView = UIView()
        let margin1 : CGFloat = 5
        let bar = UIView()
        bar.frame.size = CGSize(width: scrollView.contentSize.width, height: 1)
        bar.backgroundColor = UIColor(red: 185/255, green: 202/255, blue: 205/255, alpha: 1)
        bar.frame.origin = CGPoint(x: 0, y: 0)
        var originYIntern : CGFloat = 1
        let height : CGFloat = 40
        //
        let labelAttente = UILabel()
        labelAttente.text = "Attente : 20 min"
        labelAttente.textAlignment = .center
        labelAttente.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: height-2*margin1)
        labelAttente.frame.origin = CGPoint(x: margin1, y: margin1)
        originYIntern += height
        //
        let labelDistance = UILabel()
        labelDistance.text = "Distance : 150 m"
        labelDistance.textAlignment = .center
        labelDistance.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: height-2*margin1)
        labelDistance.frame.origin = CGPoint(x: margin1, y: originYIntern)
        originYIntern += height
        //
        infoView.addSubview(bar)
        infoView.addSubview(labelAttente)
        infoView.addSubview(labelDistance)
        infoView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: originYIntern + margin1)
        originY += margin
        infoView.frame.origin = CGPoint(x: margin, y: originY)
        originY += originYIntern
        scrollView.addSubview(infoView)
    }
    
    func buildAvisView() {
        avisView = UIView()
        let margin1 : CGFloat = 5
        let bar = UIView()
        bar.frame.size = CGSize(width: scrollView.contentSize.width, height: 1)
        bar.backgroundColor = UIColor(red: 185/255, green: 202/255, blue: 205/255, alpha: 1)
        bar.frame.origin = CGPoint(x: 0, y: 0)
        var originYIntern : CGFloat = 1
        let height : CGFloat = 30
        //
        let labelAvis = UILabel()
        labelAvis.text = "Avis"
        labelAvis.textAlignment = .center
        labelAvis.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: height-2*margin1)
        labelAvis.frame.origin = CGPoint(x: margin1, y: margin1)
        originYIntern += (height + margin1)
        //
        let labelNote = UILabel()
        if let avis = restaurant?.avis.count {
            labelNote.text = "Note moyenne : (\(avis) avis)"
        }
        labelNote.textAlignment = .left
        labelNote.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: height - 2*margin1)
        labelNote.frame.origin = CGPoint(x: margin1, y: originYIntern + margin1)
        originYIntern += (height + margin1)
        //
        let rankingView = CosmosView()
        rankingView.settings.emptyBorderColor = UIColor(red: 200/255, green: 200/255, blue: 205/255, alpha: 1)
        rankingView.settings.emptyColor = UIColor(red: 200/255, green: 200/255, blue: 205/255, alpha: 1)
        rankingView.settings.filledColor = UIColor(red: 111/255, green: 113/255, blue: 121/255, alpha: 1)
        rankingView.settings.filledBorderColor = UIColor(red: 200/255, green: 200/255, blue: 205/255, alpha: 1)
        if let moy = restaurant?.getNoteMoyenne() {
            rankingView.rating = moy
        }
        rankingView.frame.size = CGSize(width: 110, height: height - 2*margin1)
        rankingView.frame.origin = CGPoint(x: scrollView.contentSize.width/2-55, y: originYIntern + margin1)
        originYIntern += (height + margin1)
        //
        avisView.addSubview(bar)
        avisView.addSubview(labelAvis)
        avisView.addSubview(labelNote)
        avisView.addSubview(rankingView)
        avisView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: originYIntern + margin1)
        originY += margin
        avisView.frame.origin = CGPoint(x: margin, y: originY)
        originY += originYIntern
        scrollView.addSubview(avisView)
    }
    
    func buildLastAvisView() {
        let lastAvis = restaurant?.avis.last
        lastAvisView = UIView()
        let bar = UIView()
        bar.frame.size = CGSize(width: scrollView.contentSize.width, height: 1)
        bar.backgroundColor = UIColor(red: 185/255, green: 202/255, blue: 205/255, alpha: 1)
        bar.frame.origin = CGPoint(x: 0, y: 0)
        let margin1 : CGFloat = 5
        var originYIntern : CGFloat = 1
        let height : CGFloat = 25
        //
        let labelAvis = UILabel()
        labelAvis.text = "Dernier avis :"
        labelAvis.textAlignment = .left
        labelAvis.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: height - 2*margin1)
        labelAvis.frame.origin = CGPoint(x: margin1, y: 10)
        originYIntern += (height + margin1)
        //
        let rankingView = CosmosView()
        rankingView.settings.emptyBorderColor = UIColor(red: 200/255, green: 200/255, blue: 205/255, alpha: 1)
        rankingView.settings.emptyColor = UIColor(red: 200/255, green: 200/255, blue: 205/255, alpha: 1)
        rankingView.settings.filledColor = UIColor(red: 111/255, green: 113/255, blue: 121/255, alpha: 1)
        rankingView.settings.filledBorderColor = UIColor(red: 200/255, green: 200/255, blue: 205/255, alpha: 1)
        if let n = lastAvis?.note {
            rankingView.rating = n
        }
        rankingView.frame.size = CGSize(width: 110, height: height - 2*margin1)
        rankingView.frame.origin = CGPoint(x: scrollView.contentSize.width/2-55, y: originYIntern + margin1)
        originYIntern += (height + margin1)
        //
        let avisTextView = UITextView()
        if let t = lastAvis?.avis {
            avisTextView.text = t
        }
        avisTextView.isEditable = false
        avisTextView.isSelectable = false
        avisTextView.isScrollEnabled = true
        avisTextView.sizeToFit()
        avisTextView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin1, height: avisTextView.contentSize.height)
        avisTextView.isScrollEnabled = false
        avisTextView.frame.origin = CGPoint(x: margin1, y: originYIntern + margin1)
        originYIntern += (height * CGFloat(avisTextView.numberOfLines()) + margin1)
        //
        let buttonMoreAvis = UIButton()
        buttonMoreAvis.setTitle("Voir tous les avis", for: .normal)
        buttonMoreAvis.addTarget(self, action: #selector(showMoreAvis), for: .touchUpInside)
        buttonMoreAvis.titleLabel?.textColor = UIColor.white
        buttonMoreAvis.titleLabel?.font = .systemFont(ofSize: 18)
        buttonMoreAvis.titleLabel?.textAlignment = .center
        buttonMoreAvis.backgroundColor = UIColor(red: 0/255, green: 124/255, blue: 246/255, alpha: 1)
        buttonMoreAvis.frame.size = CGSize(width: 200, height: 40)
        buttonMoreAvis.frame.origin = CGPoint(x: scrollView.contentSize.width/2 - 100, y: originYIntern + margin1)
        originYIntern += (40 + 2*margin1)
        //
        lastAvisView.addSubview(bar)
        lastAvisView.addSubview(labelAvis)
        lastAvisView.addSubview(rankingView)
        lastAvisView.addSubview(avisTextView)
        lastAvisView.addSubview(buttonMoreAvis)
        lastAvisView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: originYIntern + margin1)
        originY += margin
        lastAvisView.frame.origin = CGPoint(x: margin, y: originY)
        originY += originYIntern
        scrollView.addSubview(lastAvisView)
    }
    
    func showMoreAvis() {
        let resultViewController = self.storyboard!.instantiateViewController(withIdentifier: "AvisViewController") as! AvisViewController
        resultViewController.restaurant = restaurant
        self.navigationController?.pushViewController(resultViewController, animated: true)
    }
    
    func buildChartView() {
        let view = UIView()
        originY += margin
        view.frame.origin = CGPoint(x: margin, y: originY)
        originY += chartViewHeight
        view.frame.size = CGSize(width: scrollView.contentSize.width, height: chartViewHeight+1)
        let bar = UIView()
        bar.frame.size = CGSize(width: scrollView.contentSize.width, height: 1)
        bar.backgroundColor = UIColor(red: 185/255, green: 202/255, blue: 205/255, alpha: 1)
        bar.frame.origin = CGPoint(x: 0, y: 0)
        chartsScrollView = UIScrollView()
        chartsScrollView.isPagingEnabled = true
        chartsScrollView.showsHorizontalScrollIndicator = false
        chartsScrollView.showsVerticalScrollIndicator = false
        chartsScrollView.scrollsToTop = false
        chartsScrollView!.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: chartViewHeight)
        if let affluences = restaurant?.getAffluences() {
            chartsScrollView.contentSize = CGSize(width: scrollView.contentSize.width * CGFloat(affluences.count), height: chartViewHeight)
            for i in 0 ..< affluences.count {
                let v = LineChartView()
                v.delegate = self
                v.animate(xAxisDuration: 2.0, yAxisDuration: 2.0)
                v.xAxis.labelPosition = .bottom
                v.noDataText = "Aucunes données"
                v.rightAxis.drawGridLinesEnabled = false
                v.rightAxis.drawAxisLineEnabled = false
                v.rightAxis.drawLabelsEnabled = false
                v.chartDescription?.text = "Attente lors du déjeuner"
                v.xAxis.labelCount = affluences[i].count - 1
                v.leftAxis.minWidth = 0
                v.leftAxis.maxWidth = 60
                v.leftAxis.labelCount = 7
                v.legend.enabled = false
                //
                var yVals1 : [ChartDataEntry] = [ChartDataEntry]()
                var x = 0
                var keys = [String]()
                for j in 0..<affluences[i].count {
                    yVals1.append(ChartDataEntry(x: Double(x), y: Double(affluences[i][j]!.1)))
                    keys.append(affluences[i][j]!.0)
                    x += 1
                }
                let chartFormatter = ChartFormatter()
                chartFormatter.values = keys
                let xAxis=XAxis()
                xAxis.valueFormatter=chartFormatter
                v.xAxis.valueFormatter=xAxis.valueFormatter
                let set1: LineChartDataSet = LineChartDataSet(values: yVals1, label: "Tps")
                //set1.setColor(UIColor.redColor().colorWithAlphaComponent(0.5)) // our line's opacity is 50%
                set1.drawCirclesEnabled = false
                set1.lineWidth = 2.0
                var dataSets : [LineChartDataSet] = [LineChartDataSet]()
                dataSets.append(set1)
                let data: LineChartData = LineChartData(dataSets: dataSets)
                v.data = data
                v.frame = CGRect(x: CGFloat(i) * chartsScrollView.frame.size.width, y: 0, width: scrollView.contentSize.width, height: chartViewHeight)
                chartsScrollView.addSubview(v)
            }
            
        }
        view.addSubview(bar)
        chartsScrollView.frame.origin = CGPoint(x: 0, y: 1)
        view.addSubview(chartsScrollView)
        scrollView.addSubview(view)
    }
    
    func buildMenuView() {
        let bar = UIView()
        bar.frame.size = CGSize(width: scrollView.contentSize.width, height: 1)
        bar.backgroundColor = UIColor(red: 185/255, green: 202/255, blue: 205/255, alpha: 1)
        bar.frame.origin = CGPoint(x: 0, y: originY)
        originY += 1
        scrollView.addSubview(bar)
        var ogirinYIntern : CGFloat = 0
        menuScrollView = UIScrollView()
        if let menus = restaurant?.getMenus() {
            let menuHeight : CGFloat = 40
            var nbMaxPlats : Int = 0
            var nb = 0
            for m1 in menus {
                nb = 1
                for m2 in m1.value.1 {
                    nb += m2.value.1.count + 1
                }
                if nbMaxPlats == 0 || nbMaxPlats < nb {
                    nbMaxPlats = nb
                }
            }
            menuScrollView.isPagingEnabled = true
            menuScrollView.showsHorizontalScrollIndicator = false
            menuScrollView.showsVerticalScrollIndicator = false
            menuScrollView.scrollsToTop = false
            menuScrollView.frame.size = CGSize(width: scrollView.contentSize.width - 2*margin, height: CGFloat(nbMaxPlats)*menuHeight)
            originY += margin
            menuScrollView.frame.origin = CGPoint(x: margin, y: originY)
            originY += (CGFloat(nbMaxPlats)*menuHeight)
            menuScrollView.contentSize = CGSize(width: scrollView.contentSize.width * CGFloat(menus.count), height: CGFloat(nbMaxPlats)*menuHeight)
            var i = 0
            for m in 0..<menus.count {
                ogirinYIntern = 0
                let viewM = UIView()
                viewM.frame = CGRect(x: CGFloat(i) * menuScrollView.frame.size.width, y: 0, width: scrollView.contentSize.width, height: CGFloat(nbMaxPlats)*menuHeight)
                menuScrollView.addSubview(viewM)
                //Gestion du titre
                let titre = UILabel()
                titre.text = menus[m]?.0
                titre.frame.size = CGSize(width: viewM.frame.width, height: menuHeight)
                titre.frame.origin =  CGPoint(x: 0, y: ogirinYIntern)
                ogirinYIntern += menuHeight
                titre.textAlignment = .center
                titre.font = .systemFont(ofSize: 23)
                titre.textColor = UIColor.black
                viewM.addSubview(titre)
                //Gestion des plats
                for i in 0..<menus[m]!.1.count {
                    let type = UILabel()
                    type.text = menus[m]?.1[i]?.0
                    type.frame.size = CGSize(width: viewM.frame.width, height: menuHeight)
                    type.frame.origin =  CGPoint(x: 0, y: ogirinYIntern)
                    ogirinYIntern += menuHeight
                    type.textAlignment = .center
                    type.font = .systemFont(ofSize: 18)
                    type.textColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.8)
                    viewM.addSubview(type)
                    if menus[m]!.1[i] != nil {
                        for plats in menus[m]!.1[i]!.1 {
                            let p = UILabel()
                            p.text = plats
                            p.frame.size = CGSize(width: viewM.frame.width, height: menuHeight)
                            p.frame.origin =  CGPoint(x: 0, y: ogirinYIntern)
                            ogirinYIntern += menuHeight
                            p.textAlignment = .center
                            p.font = .systemFont(ofSize: 13)
                            p.textColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.6)
                            viewM.addSubview(p)
                        }
                    }
                }
                i+=1
            }
        }
        scrollView.addSubview(menuScrollView)
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
        mapView.setRegion(coordinateRegion, animated: true)
        locationManager.stopUpdatingLocation()
    }

}
