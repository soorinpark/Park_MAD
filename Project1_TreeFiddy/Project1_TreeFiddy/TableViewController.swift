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

    @IBOutlet weak var incomeTotal: UILabel!
    @IBOutlet weak var expenseTotal: UILabel!
    @IBOutlet weak var totalLabel: UILabel!
    
    override func viewDidLoad() {
        
        self.tableView.dataSource = self;
        self.tableView.delegate = self;
        super.viewDidLoad()
        
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return items.count
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return items[section].count
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
        let data = items[indexPath.section][indexPath.row]
        let dataArr = data.characters.split{$0 == "-"}.map(String.init)
        
        cell.label.text = dataArr[0]
        cell.cellAmount.text = "$" + dataArr[1]
        
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
        if let sourceViewController = sender.sourceViewController as? ViewController, data = sourceViewController.data {
            // Add a new meal item.
            var newIndex = 0
            var newData = ""
            
            if (data[0] == "Income") {
                
                newData = data[1] + "-" + data[2]
                items[0].append(newData)
                newIndex = 0
                
            }
                
            else if (data[0] == "Bills") {
                
                newData = data[1] + "-" + data[2]
                items[1].append(newData)
                newIndex = 1
                
            }
                
            else {

                newData = data[1] + "-" + data[2]
                print(newData)
                items[2].append(newData)
                newIndex = 2
            }
            
            let newIndexPath = NSIndexPath(forRow: items[newIndex].count-1, inSection: newIndex)
            tableView.beginUpdates()
            tableView.insertRowsAtIndexPaths([newIndexPath], withRowAnimation: .Bottom)
            tableView.endUpdates()
        }
    }

}
