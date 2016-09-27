//
//  TableViewController.swift
//  Project1_TreeFiddy
//
//  Created by Soo Rin Park on 9/20/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import UIKit

class TableViewController: UITableViewController {

    let sectionTitles = ["Income", "Bills", "Expenses"]
    var items: [[String]] = [[],[],[]]
    var income: Int = 0
    var expense: Int = 0

    @IBOutlet weak var incomeTotal: UILabel!
    @IBOutlet weak var expenseTotal: UILabel!
    @IBOutlet weak var totalLabel: UILabel!
    
    var data: [[Data]] = [[],[],[]]
    
    var incomeNum: Int = 0
    var expenseNum: Int = 0
    var billsNum: Int = 0
    
    override func viewDidLoad() {
        
        do {
            try NSFileManager.defaultManager().removeItemAtPath(Data.ArchiveURL.path!)
        } catch {
            
        }
        
        self.tableView.dataSource = self;
        self.tableView.delegate = self;
        super.viewDidLoad()
        
        /*
        if let savedData = loadData() {
            data += savedData
        } else {
            // Load the sample data.
            loadData()
        }
         */
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return sectionTitles.count
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        
        if (section == 0) {
            return incomeNum
        }
        else if (section == 1) {
            return billsNum
        }
        
        else {
            return expenseNum
        }
        
    }
    
    override func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        
        if section < sectionTitles.count {
            return sectionTitles[section]
        }
        
        return nil
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        let cellIdentifier = "tableViewCell"
        let cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier, forIndexPath: indexPath) as! TableViewCell
        //let data = items[indexPath.section][indexPath.row]
        //let dataArr = data.characters.split{$0 == "-"}.map(String.init)
        
        let item = data[indexPath.section][indexPath.row]
        
        //cell.label.text = dataArr[0]
        
        let currencyFormatter = NSNumberFormatter()
        currencyFormatter.numberStyle=NSNumberFormatterStyle.CurrencyStyle
        
        cell.label.text = item.name
        cell.cellAmount.text = currencyFormatter.stringFromNumber(Int(item.amount))
        
        
        /*
        let currencyFormatter = NSNumberFormatter()
        currencyFormatter.numberStyle=NSNumberFormatterStyle.CurrencyStyle
        
        cell.cellAmount.text = currencyFormatter.stringFromNumber(Int(dataArr[1])!)
         */

        return cell
    }
    
    override func tableView(tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        if let headerView = view as? UITableViewHeaderFooterView, textLabel = headerView.textLabel {
            
            let newSize = CGFloat(15)
            //let fontName = textLabel.font.fontName
            textLabel.font = UIFont(name: "KannadaSangamMN", size: newSize)
        }
    }
    
    @IBAction func addToTable(sender: UIStoryboardSegue) {
        if let sourceViewController = sender.sourceViewController as? ViewController, item = sourceViewController.item {
            // Add a new meal item.
            //var newIndex = 0
            //var newData = ""
            
            print(item.name)
            print(item.type)
            print(item.amount)
            
            var sectionNum: Int = 0
            
            var newIndexPath = NSIndexPath(forRow:0, inSection: 0)
            
            if (item.type == "Income") {
                
                sectionNum = 0
                income += item.amount
                incomeNum += 1
                newIndexPath = NSIndexPath(forRow: incomeNum-1, inSection: sectionNum)

            }
            
            else if (item.type == "Bills") {
                sectionNum = 1
                expense += item.amount
                billsNum += 1
                newIndexPath = NSIndexPath(forRow: billsNum-1, inSection: sectionNum)

            }
            
            else if (item.type == "Expenses") {
            
                sectionNum = 2
                expense += item.amount
                expenseNum += 1
                newIndexPath = NSIndexPath(forRow: expenseNum-1, inSection: sectionNum)

            }
            
            let total: Int = income - expense
            
            data[sectionNum].append(item)
            print(data)

            tableView.beginUpdates()
            tableView.insertRowsAtIndexPaths([newIndexPath], withRowAnimation: .Bottom)
            tableView.endUpdates()
            
            let currencyFormatter = NSNumberFormatter()
            currencyFormatter.numberStyle=NSNumberFormatterStyle.CurrencyStyle
            
            incomeTotal.text = currencyFormatter.stringFromNumber(income)
            expenseTotal.text = currencyFormatter.stringFromNumber(expense)
            totalLabel.text = currencyFormatter.stringFromNumber(total)
 

 
            /*
            if (item[0] == "Income") {
                
                newData = item[1] + "-" + data[2]
                items[0].append(newData)
                newIndex = 0
                income += Int(data[2])!
                
            }
                
            else if (item[0] == "Bills") {
                
                newData = item[1] + "-" + item[2]
                items[1].append(newData)
                newIndex = 1
                expense += Int(data[2])!
                
            }
                
            else {

                newData = data[1] + "-" + data[2]
                print(newData)
                items[2].append(newData)
                newIndex = 2
                expense += Int(data[2])!
            }
 */
        /*
            var total: Int = income - expense
            
            let newIndexPath = NSIndexPath(forRow: items[newIndex].count-1, inSection: newIndex)
            tableView.beginUpdates()
            tableView.insertRowsAtIndexPaths([newIndexPath], withRowAnimation: .Bottom)
            tableView.endUpdates()
            
            let currencyFormatter = NSNumberFormatter()
            currencyFormatter.numberStyle=NSNumberFormatterStyle.CurrencyStyle

            incomeTotal.text = currencyFormatter.stringFromNumber(income)
            expenseTotal.text = currencyFormatter.stringFromNumber(expense)
            totalLabel.text = currencyFormatter.stringFromNumber(total)
 */
        }
        
        //saveData()
        
    }
    
    // MARK: NSCoding
    /*
    func saveData() {
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(data, toFile: Data.ArchiveURL.path!)
        if !isSuccessfulSave {
            print("Failed to save meals...")
        }
    }
    
    func loadData() -> [Data]? {
        return NSKeyedUnarchiver.unarchiveObjectWithFile(Data.ArchiveURL.path!) as? [Data]
    }
 */
}
