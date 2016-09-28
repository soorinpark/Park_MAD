//
//  ViewController.swift
//  Project1_TreeFiddy
//
//  Created by Soo Rin Park on 9/19/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit
import Foundation

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate {
    
    @IBOutlet weak var typeField: UITextField!
    @IBOutlet weak var amountField: UITextField!
    @IBOutlet weak var freqField: UITextField!
    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var displayField: UILabel!
    
    @IBOutlet weak var addButton: UIButton!
    
    
    let typeData = ["Income", "Bills", "Expenses"]
    let freqData = ["week", "bi-week", "month", "bi-month", "6 months", "year"]
    
    //var data: [String]?
    var item: Data?
    
    override func viewDidLoad() {

        typeField.delegate = self
        freqField.delegate = self
        amountField.delegate = self
        nameField.delegate = self
        
        super.viewDidLoad()
        
        addButton.tintColor = UIColor.whiteColor()
        addButton.backgroundColor = UIColor.lightGrayColor()
        addButton.layer.cornerRadius = 5
        
        let typePickerView = UIPickerView(frame: CGRectMake(0, 200, view.frame.width, 250))
        let freqPickerView = UIPickerView(frame: CGRectMake(0, 200, view.frame.width, 250))
        
        typePickerView.delegate = self
        freqPickerView.delegate = self
        
        typePickerView.tag = 1
        freqPickerView.tag = 2
        
        typeField.inputView = typePickerView
        freqField.inputView = freqPickerView
        
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.Default
        toolBar.translucent = true
        toolBar.tintColor = UIColor.blueColor()
        toolBar.sizeToFit()
        
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.Plain, target: self, action: #selector(ViewController.donePicker))
        doneButton.tintColor = UIColor.darkGrayColor()
        
        let spaceButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.FlexibleSpace, target: nil, action: nil)
        
        let cancelButton = UIBarButtonItem(title: "Cancel", style: UIBarButtonItemStyle.Plain, target: self, action: #selector(ViewController.donePicker))
        cancelButton.tintColor = UIColor.darkGrayColor()
        
        
        toolBar.setItems([cancelButton, spaceButton, doneButton], animated: false)
        toolBar.userInteractionEnabled = true
        
        typeField.inputAccessoryView = toolBar
        freqField.inputAccessoryView = toolBar
        amountField.inputAccessoryView = toolBar
        
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool // called when 'return' key pressed. return false to ignore.
    {
        textField.resignFirstResponder()
        return true
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        updateAmount()
    }
    
    func updateAmount() {
    
        displayField.text = amountField.text! + "/" + freqField.text!
    }
    
    func donePicker() {
        
        typeField.resignFirstResponder()
        freqField.resignFirstResponder()
        amountField.resignFirstResponder()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1 {
            return typeData.count
        }
        
        if pickerView.tag == 2 {
            return freqData.count
        }
        return 0
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1 {
            return typeData[row]
        }
        
        if pickerView.tag == 2 {
            return freqData[row]
        }
        return nil
    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1 {
            typeField.text = typeData[row]
        }
        
        if pickerView.tag == 2 {
            freqField.text = freqData[row]
        }
    }
    
    override func shouldPerformSegueWithIdentifier(identifier: String?, sender: AnyObject?) -> Bool {
        
        if (typeField.text == "" || nameField.text == "" || amountField.text == "" || freqField.text == "") {
            let alertController = UIAlertController(title: "Error", message:
                "You have not entered in one or more fields. Please go back and fill each field.", preferredStyle: UIAlertControllerStyle.Alert)
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.Default,handler: nil))
            alertController.view.tintColor = UIColor.darkGrayColor()
            
            self.presentViewController(alertController, animated: true, completion: nil)
            
            return false
            
        }
        else {
            return true
        }
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if addButton === sender {
            
            let type = typeField.text!
            var amount = amountField.text
            var amount1 = amount!.stringByReplacingOccurrencesOfString("$", withString: "", options: NSStringCompareOptions.LiteralSearch, range: nil)

            let amount2 = Int(amount1)
            
            let freq = freqField.text!
            let name = nameField.text!
            
            var newAmount: Int = 0
            
            if (freq == "week") {
                newAmount = amount2! * 4
            }
            
            else if (freq == "bi-week") {
                newAmount = amount2!*2
            }
            
            else if (freq == "month") {
                newAmount = amount2!
            }
            else if (freq == "bi-month") {
            
                newAmount = amount2!/2
            }
            else if (freq == "6 months") {
                newAmount = amount2!/6
            }
            else if (freq == "year") {
            
                newAmount = amount2!/12
            }
            
            item = Data(type: type, name: name, amount: newAmount)
            
        }
    }
    
}

