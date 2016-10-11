//
//  ViewController.swift
//  Lab6
//
//  Created by Soo Rin Park on 10/6/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit
import AVFoundation

class ViewController: UIViewController, UIGestureRecognizerDelegate {
    
    var audioPlayer: AVAudioPlayer?

    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    @IBAction func handleLongPress(_ sender: UILongPressGestureRecognizer) {
        let audioFilePath = Bundle.main.path(forResource: "tinyrick", ofType: "mp3")
        print(audioFilePath)
        let fileURL = NSURL(fileURLWithPath: audioFilePath!)
        audioPlayer = try? AVAudioPlayer(contentsOf: fileURL as URL)
        
        if audioPlayer != nil {
            audioPlayer!.play()
            print("playing")
        }
    }
    
    @IBAction func handlePan(_ sender: UIPanGestureRecognizer) {
        let translation = sender.translation(in: view)
        sender.view!.center = CGPoint (x: sender.view!.center.x + translation.x, y: sender.view!.center.y + translation.y)
        sender.setTranslation(CGPoint.zero, in: view)
        
    }
    @IBAction func handlePinch(_ sender: UIPinchGestureRecognizer) {
        sender.view!.transform = sender.view!.transform.scaledBy(x: sender.scale, y: sender.scale)
        sender.scale = 1
    }
    @IBAction func handleRotate(_ sender: UIRotationGestureRecognizer) {
        sender.view!.transform = sender.view!.transform.rotated(by: sender.rotation)
        sender.rotation = 0
        
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

