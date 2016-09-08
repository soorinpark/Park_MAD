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
    @IBOutlet weak var logo: UIImageView!
    
    @IBAction func rickButton(sender: UIButton) {
        
        imageView.image=UIImage(named: "rick")
        
    }
    @IBAction func mortyButton(sender: UIButton) {
        
        imageView.image=UIImage(named: "morty")
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        //imageView.image=UIImage(named: "morty")
        //logo.image=UIImage(named: "rick_and_morty")

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

