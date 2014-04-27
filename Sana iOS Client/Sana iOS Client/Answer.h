//
//  Answer.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Procedure;

@interface Answer : NSManagedObject

@property (nonatomic, retain) NSString * elementId;
@property (nonatomic, retain) NSString * answer;
@property (nonatomic, retain) Procedure *procedure;

@end
