//
//  Data.swift
//  Project1_TreeFiddy
//
//  Created by Soo Rin Park on 9/27/16.
//  Copyright © 2016 Soo Rin Park. All rights reserved.
//

import Foundation
import UIKit

class Data: NSObject, NSCoding {

    //MARK: Properties
    var type: String
    var name: String
    var amount: Int
    
    // MARK: Archiving Paths
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("data")
    
    // MARK: Types
    struct PropertyKey {
        static let typeKey = "type"
        static let nameKey = "name"
        static let amountKey = "amount"
    }
    
    // MARK: Initialization
    
    init?(type: String, name: String, amount: Int) {
        // Initialize stored properties.
        self.type = type
        self.name = name
        self.amount = amount
        
        super.init()
        
        // Initialization should fail if there is no name or if the rating is negative.
        if name.isEmpty || amount < 0 {
            return nil
        }
    }
    
    // MARK: NSCoding
    func encode(with aCoder: NSCoder) {
        
        aCoder.encode(type, forKey: PropertyKey.typeKey)
        aCoder.encode(name, forKey: PropertyKey.nameKey)
        aCoder.encode(amount, forKey: PropertyKey.amountKey)

        
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        
        let type = aDecoder.decodeObject(forKey: PropertyKey.typeKey) as! String
        let name = aDecoder.decodeObject(forKey: PropertyKey.nameKey) as! String
        let amount = aDecoder.decodeInteger(forKey: PropertyKey.amountKey)
        
        // Must call designated initializer.
        self.init(type: type, name: name, amount: amount)
    }
    
}
