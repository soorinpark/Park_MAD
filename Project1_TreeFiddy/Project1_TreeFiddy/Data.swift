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
    static let DocumentsDirectory = NSFileManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.URLByAppendingPathComponent("data")
    
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
    func encodeWithCoder(aCoder: NSCoder) {
        
        aCoder.encodeObject(type, forKey: PropertyKey.typeKey)
        aCoder.encodeObject(name, forKey: PropertyKey.nameKey)
        aCoder.encodeObject(amount, forKey: PropertyKey.amountKey)

        
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        
        let type = aDecoder.decodeObjectForKey(PropertyKey.typeKey) as! String
        let name = aDecoder.decodeObjectForKey(PropertyKey.nameKey) as! String
        let amount = aDecoder.decodeIntegerForKey(PropertyKey.amountKey)
        
        // Must call designated initializer.
        self.init(type: type, name: name, amount: amount)
    }
    
}
