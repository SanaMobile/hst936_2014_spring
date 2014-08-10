//
//  SanaAttributedSwitch.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaAttributedSwitch.h"

@implementation SanaAttributedSwitch

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self setOnImage:[UIImage imageNamed:@"switch_on.jpg"]];
        [self setOffImage:[UIImage imageNamed:@"switch_off.jpg"]];
    }
    return self;
}

@end
