//
//  ViewController.swift
//  Lab2
//
//  Created by Soo Rin Park on 9/8/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var label: UILabel!
    @IBOutlet weak var imgSeg: UISegmentedControl!
    @IBOutlet weak var capitalSwitch: UISwitch!
    @IBOutlet weak var fontSizeSlider: UISlider!
    @IBOutlet weak var fontSizeLabel: UILabel!

    @IBAction func changeImg(sender: UISegmentedControl) {
        updateImg()
        updateCaps()
    }
    
    @IBAction func fontUpdate(sender: UISwitch) {
        updateCaps()
    }
    
    @IBAction func changeFontSize(sender: UISlider) {
        let fontSize = sender.value
        fontSizeLabel.text = String(format: "%.0f", fontSize)
        let fontSizeCGFloat = CGFloat(fontSize)
        label.font = UIFont.systemFontOfSize(fontSizeCGFloat)
    }
    @IBOutlet weak var colorChange: UISegmentedControl!
    
    @IBAction func changeFontColor(sender: UISegmentedControl) {
        
        if colorChange.selectedSegmentIndex == 0 {
        
            label.textColor = UIColor.redColor()
        }
        else if colorChange.selectedSegmentIndex == 1 {
        
            label.textColor = UIColor.blueColor()
        }
        else {
        
            label.textColor = UIColor.greenColor()
        }
        
        
    }
    
    func updateCaps() {
    
        if capitalSwitch.on {
            
            label.text = label.text?.uppercaseString
        }
        else {
            
            label.text = label.text?.lowercaseString
        }
        
    }
    
    func updateImg() {
    
        if imgSeg.selectedSegmentIndex == 0 {
            
            imageView.image = UIImage(named: "angry")
            label.text = "Angry Eleven"
        }
        else {
            imageView.image = UIImage(named: "happy")
            label.text = "Happy Eleven"
            
        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

