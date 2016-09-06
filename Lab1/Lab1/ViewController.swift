//
//  ViewController.swift
//  Lab1
//
//  Created by Soo Rin Park on 8/30/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var imageView: UIImageView!
    
    @IBAction func rickButton(sender: UIButton) {
        
        imageView.image=UIImage(named: "rick")
        
    }
    @IBAction func mortyButton(sender: UIButton) {
        
        imageView.image=UIImage(named: "morty")
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

