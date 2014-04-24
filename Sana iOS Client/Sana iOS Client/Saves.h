//
//  Saves.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/24/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Saves : NSManagedObject

@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) NSDate * created;
@property (nonatomic, retain) NSDate * modified;
@property (nonatomic, retain) NSString * originalFile;
@property (nonatomic, retain) NSString * savedFile;

@end
