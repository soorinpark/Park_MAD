//
//  ViewController.swift
//  Midterm
//
//  Created by Soo Rin Park on 10/13/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var timeField: UITextField!
    @IBOutlet weak var weekButton: UISwitch!
    @IBOutlet weak var workoutsLabel: UILabel!
    @IBOutlet weak var workoutSeg: UISegmentedControl!
    @IBOutlet weak var workoutButton: UIButton!
    @IBOutlet weak var weekSlider: UISlider!
    @IBOutlet weak var milesLabel: UILabel!
    @IBOutlet weak var caloriesLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    
    var weekSliderVal: Float = 1.0
    var type = 0
    
    var caloriesBurned: Float?
    var averageSpeed: Float?
    
    @IBAction func weekSliderAction(_ sender: UISlider) {
        
        weekSliderVal = sender.value
        if weekButton.isOn {
            workoutsLabel.text = String(weekSliderVal)
        }
        else {
            workoutsLabel.text = "1"
        }

    }
    @IBAction func segmentAction(_ sender: UISegmentedControl) {
        
        if workoutSeg.selectedSegmentIndex == 0 { //Run
            averageSpeed = 10
            caloriesBurned = 600
            imageView.image = UIImage(named: "run")
        }
        else if workoutSeg.selectedSegmentIndex == 1 { //Swim
            averageSpeed = 30
            caloriesBurned = 420
            imageView.image = UIImage(named: "swim")

        }
        else { //Bike
            averageSpeed = 4
            caloriesBurned = 510
            imageView.image = UIImage(named: "bike")

        }
    
    }
    
    
    @IBAction func workoutAction(_ sender: UIButton) {
        
        if workoutSeg.selectedSegmentIndex == 0 { //Run
            averageSpeed = 10
            caloriesBurned = 600
            imageView.image = UIImage(named: "run")
        }
        else if workoutSeg.selectedSegmentIndex == 1 { //Swim
            averageSpeed = 30
            caloriesBurned = 420
            imageView.image = UIImage(named: "swim")
            
        }
        else { //Bike
            averageSpeed = 4
            caloriesBurned = 510
            imageView.image = UIImage(named: "bike")
            
        }
        
        var time = Float(timeField.text!)
        
        if time! < 30 {
            let alertController = UIAlertController(title: "Warning", message:
                "You are working out for less than 30 minutes", preferredStyle: UIAlertControllerStyle.alert)
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
            self.present(alertController, animated: true, completion: nil)


        }
        
        var miles = ""
        var calories = ""
        
        if weekButton.isOn {
            miles = String(time!/averageSpeed!*weekSliderVal)
            calories = String(time!/60.0*caloriesBurned!*weekSliderVal)
        }
        
        else {
            miles = String(time!/averageSpeed!)
            calories = String(time!/60*caloriesBurned!)
        }
        
        milesLabel.text = miles + " miles"
        caloriesLabel.text = calories + " calories"
        
    }
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        workoutsLabel.text = String(weekSlider.value)
        imageView.image = UIImage(named: "workout")
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

