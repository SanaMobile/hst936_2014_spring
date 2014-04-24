//
//  Element.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/20/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "Element.h"

@implementation Element

- (id)initWithID:(NSString *)elementId question:(NSString *)question answer:(NSString *)answer concept:(NSString *)concept {
    self = [super init];
    if(self) {
        self.elementId = elementId;
        self.question = question;
        self.answer = answer;
        self.concept = concept;
    }
    return self;
}

@end
