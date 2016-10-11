//
//  SecondViewController.swift
//  Lab4
//
//  Created by Soo Rin Park on 10/6/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {
    
    @IBOutlet weak var white: UIView!
    @IBOutlet weak var black: UIView!
    @IBOutlet weak var red: UIView!
    @IBOutlet weak var orange: UIView!
    @IBOutlet weak var green: UIView!
    @IBOutlet weak var blue: UIView!
    @IBOutlet weak var magenta: UIView!
    @IBOutlet weak var purple: UIView!
    @IBOutlet weak var yellow: UIView!
    
    
    
    @IBOutlet weak var submit: UIButton!
    
    var views = [UIView]()
    var viewsColor = [UIView:UIColor]()
    
    var colorSelect: UIColor? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        views = [white, black, red, orange, green, blue, magenta, purple, yellow]
        viewsColor = [white: UIColor.white, black: UIColor.black, red: UIColor.red, orange:UIColor.orange, green:UIColor.green, blue:UIColor.blue, magenta:UIColor.magenta, purple:UIColor.purple, yellow:UIColor.yellow]

        for i in 0 ..< views.count {
            let tap = UITapGestureRecognizer(target: self, action: #selector(SecondViewController.colorTapped(_:)))
            views[i].addGestureRecognizer(tap)
    
        }

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func colorTapped(_ sender: UIGestureRecognizer) {
        
        for i in 0 ..< views.count {
            views[i].layer.borderWidth = 0
            
        }
        
        let viewSelect = sender.view

        viewSelect?.layer.borderWidth = 3
        viewSelect?.layer.borderColor = UIColor.black.cgColor
    
        colorSelect = viewsColor[(viewSelect)!]!
    }
    
    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        let destinationVC = segue.destination as! FirstViewController
        destinationVC.view.backgroundColor = colorSelect
        
    }
    

}
