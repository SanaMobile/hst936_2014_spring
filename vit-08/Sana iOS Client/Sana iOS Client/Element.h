//
//  Element.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/20/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Element : NSObject

@property (nonatomic, assign) NSString *elementId;
@property (nonatomic, assign) NSString *concept;
@property (nonatomic, assign) NSString *question;
@property (nonatomic, assign) NSString *answer;
@property (nonatomic, assign) NSString *type;

- (id)initWithID:(NSString *)elementId question:(NSString *)question answer:(NSString *)answer concept:(NSString *)concept;

@end
