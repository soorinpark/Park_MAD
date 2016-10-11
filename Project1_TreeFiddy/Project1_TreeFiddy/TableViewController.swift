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
    
    @IBOutlet weak var incomeTotal: UILabel!
    @IBOutlet weak var expenseTotal: UILabel!
    @IBOutlet weak var totalLabel: UILabel!
    
    var data: [[Data]] = [[],[],[]]
 
    override func viewDidLoad() {
        
        
        //delete all data in archive path
        /*
                do {
            try NSFileManager.defaultManager().removeItemAtPath(Data.ArchiveURL.path!)
        } catch {
            
        }*/
        
        if let savedData = loadData() {
            for i in 0...data.count-1 {
                data[i] += savedData[i]
                
            }
        }
        
        updateTotal()
        
        self.tableView.dataSource = self;
        self.tableView.delegate = self;
        
        super.viewDidLoad()
        
        navigationItem.leftBarButtonItem = editButtonItem

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return sectionTitles.count
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        
        if (section == 0) {
            return data[0].count
        }
        else if (section == 1) {
            return data[1].count
        }
        
        else {
            return data[2].count
        }
        
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        
        if section < sectionTitles.count {
            return sectionTitles[section]
        }
        
        return nil
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cellIdentifier = "tableViewCell"
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! TableViewCell

        let item = data[(indexPath as NSIndexPath).section][(indexPath as NSIndexPath).row]
        
        let currencyFormatter = NumberFormatter()
        currencyFormatter.numberStyle=NumberFormatter.Style.currency
        
        var amount = item.amount as NSNumber
        
        cell.label.text = item.name
        cell.cellAmount.text = currencyFormatter.string(from: amount)

        return cell
    }
    
    override func tableView(_ tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        if let headerView = view as? UITableViewHeaderFooterView, let textLabel = headerView.textLabel {
            
            let newSize = CGFloat(17)
            textLabel.font = UIFont(name: "KannadaSangamMN", size: newSize)
            textLabel.tintColor = UIColor.white
        }
    }
    
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView
    {
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: 30))
        
        if (section == 0) {
            headerView.backgroundColor = UIColor(red: 211/255, green: 239/255, blue: 200/255, alpha: 1)

        }
        
        else if (section == 1) {
            
            headerView.backgroundColor = UIColor(red: 186/255, green: 225/255, blue: 255/255, alpha: 1)

        }
        
        else {
            headerView.backgroundColor = UIColor(red: 242/255, green: 199/255, blue: 198/255, alpha: 1)
        }
        
        let headerLabel = UILabel(frame: CGRect(x: 20, y: 5, width: tableView.bounds.size.width, height: 30))
        headerLabel.text = self.tableView(tableView, titleForHeaderInSection: section)
        let newSize = CGFloat(15)
        headerLabel.font = UIFont(name: "KannadaSangamMN", size: newSize)
        headerLabel.textColor = UIColor.darkGray
        headerView.addSubview(headerLabel)


        
        return headerView
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        
        return 35
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.delete) {
            
            data[(indexPath as NSIndexPath).section].remove(at: (indexPath as NSIndexPath).row)
            tableView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.fade)
            saveData()
            updateTotal()
        }
        
        else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        self.tableView.setEditing(editing, animated: animated)
    }
    
    override func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableViewAutomaticDimension
    }
    
    
    @IBAction func addToTable(_ sender: UIStoryboardSegue) {
        
        if let sourceViewController = sender.source as? ViewController, let item = sourceViewController.item {
            
            if let selectedIndexPath = tableView.indexPathForSelectedRow {
                data[selectedIndexPath.section][selectedIndexPath.row] = item
                tableView.reloadRows(at: [selectedIndexPath], with: .none)
            }
            
            else {
                var sectionNum: Int = 0
                var newIndexPath = IndexPath(row:0, section: 0)
                
                if (item.type == "Income") {
                    
                    sectionNum = 0
                    newIndexPath = IndexPath(row: data[0].count, section: sectionNum)

                }
                
                else if (item.type == "Bills") {
                    sectionNum = 1
                    newIndexPath = IndexPath(row: data[1].count, section: sectionNum)

                }
                
                else {
                
                    sectionNum = 2
                    newIndexPath = IndexPath(row: data[2].count, section: sectionNum)

                }
                
                data[sectionNum].append(item)

                saveData()
                
                tableView.beginUpdates()
                tableView.insertRows(at: [newIndexPath], with: UITableViewRowAnimation.fade)
                tableView.endUpdates()
                
                updateTotal()
             
            }
            
        }
    }
    
    func updateTotal() {
    
        var income: Int = 0
        var expense: Int = 0
        
        if data[0].count >= 1 {
            for i in 0...data[0].count-1 {
                
                var item: Data = data[0][i]
                income += item.amount
            }
            
        }
        
        if data[1].count >= 1 {
            for i in 0...data[1].count-1 {
                
                var item: Data = data[1][i]
                expense += item.amount
            }
        }
        
        if data[2].count >= 1 {
            for i in 0...data[2].count-1 {
                
                var item: Data = data[2][i]
                expense += item.amount
            }
        }
    
    
        var total: Int = income - expense
    
        let currencyFormatter = NumberFormatter()
        currencyFormatter.numberStyle=NumberFormatter.Style.currency
    
        //var income = income as NSNumber
        //var expense = expense as NSNumber
        //var total = expense as NSNumber
        
        incomeTotal.text = currencyFormatter.string(from: income as NSNumber)
        expenseTotal.text = currencyFormatter.string(from: expense as NSNumber)
        totalLabel.text = currencyFormatter.string(from: total as NSNumber)
        
        var totalView = self.view.viewWithTag(1)! as UIView
        
        if (total <= 0) {
            totalView.backgroundColor = UIColor(red: 242/255, green: 199/255, blue: 198/255, alpha: 1)
        }
        
        else if (total > 0 && total <= 200) {
            totalView.backgroundColor = UIColor(red: 255/255, green: 255/255, blue: 208/255, alpha: 1)
        }
        
        else {
            totalView.backgroundColor = UIColor(red: 211/255, green: 239/255, blue: 200/255, alpha: 1)
        }


    }
    
    // MARK: NSCoding

    func saveData() {
        let saved = NSKeyedArchiver.archiveRootObject(data, toFile: Data.ArchiveURL.path)
        if !saved {
            let alertController = UIAlertController(title: "Error", message:
                "Could not save data. Please try again.", preferredStyle: UIAlertControllerStyle.alert)
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
            alertController.view.tintColor = UIColor(red: 242/255, green: 199/255, blue: 198/255, alpha: 1)
            
            self.present(alertController, animated: true, completion: nil)
        }
    }
    
    func loadData() -> [[Data]]? {
        let loaded = NSKeyedUnarchiver.unarchiveObject(withFile: Data.ArchiveURL.path) as? [[Data]]
        /*
        if (loaded == nil) {
            let alertController = UIAlertController(title: "Error", message:
                "Could not load data or there was no data to load.", preferredStyle: UIAlertControllerStyle.alert)
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default,handler: nil))
            alertController.view.tintColor = UIColor.darkGray
            self.present(alertController, animated: true, completion: nil)

        }*/
        return loaded
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "showItem" {
            
            let viewController = segue.destination as! ViewController
             
            if let selectedCell = sender as? TableViewCell {
                let indexPath = tableView.indexPath(for: selectedCell)!
                let selectedItem = data[indexPath.section][indexPath.row]
                viewController.item = selectedItem
            }
        }
    }
}
