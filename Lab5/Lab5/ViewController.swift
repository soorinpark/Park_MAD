//
//  ViewController.swift
//  location
//
//  Created by Aileen Pierce
//  Copyright (c) 2016 Aileen Pierce. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate{
    
    @IBOutlet weak var mapView: MKMapView!
    var locationManager = CLLocationManager()
    var annotation = MKPointAnnotation()
    var location: CLLocation!

    
    override func viewDidLoad() {
        mapView.mapType=MKMapType.hybrid //hybrid with map and satellite
        let status = CLLocationManager.authorizationStatus()
        if status==CLAuthorizationStatus.notDetermined{
            locationManager.requestWhenInUseAuthorization()
        }
        locationManager.delegate=self
        locationManager.desiredAccuracy=kCLLocationAccuracyBest //specify the desired accuracy
        locationManager.distanceFilter=kCLDistanceFilterNone //specify the distance a device must move laterally(in meters) to generate an update. We specify to be notified of all movements
        mapView.showsUserLocation=true
        super.viewDidLoad()
    }
    
    @IBAction func findMe(_ sender: UIButton) {
        let center = CLLocationCoordinate2D(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
        let region = MKCoordinateRegion(center: center, span: MKCoordinateSpan(latitudeDelta: 0.01, longitudeDelta: 0.01))
        
        mapView.setRegion(region, animated: true)
    }
    
    //CLLocationManagerDelegate methods
    
    //called when a new location value is available
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        self.location = locations.last! as CLLocation
        /*
        let span = MKCoordinateSpanMake(0.05, 0.05) //defines the area spanned by a map region
        let region = MKCoordinateRegionMake(manager.location!.coordinate, span) //region of the map to be displayed
        mapView.setRegion(region, animated: true) //animates changing the currently visible region
        */
        //annotations
        annotation.coordinate=manager.location!.coordinate
        annotation.title="You are here"
        annotation.subtitle="Latitude: \(manager.location!.coordinate.latitude), Longitude: \(manager.location!.coordinate.longitude)"
        mapView.addAnnotation(annotation)
        
    }
    
    //called when the authorization status for the application changed.
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if status==CLAuthorizationStatus.authorizedWhenInUse {
            locationManager.startUpdatingLocation() //starts the location manager
        }
    }
    
    //called when a location cannot be determined
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        /*var errorType=String()
         if let clError=CLError.Code(rawValue: error.code) {
         if clError == .denied {
         errorType="access denied"
         let alert=UIAlertController(title: "Error", message: errorType, preferredStyle: UIAlertControllerStyle.alert)
         let okAction:UIAlertAction=UIAlertAction(title: "OK", style:UIAlertActionStyle.default, handler: nil)
         alert.addAction(okAction)
         present(alert, animated: true, completion: nil)
         }
         }*/
        print("failed")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}

