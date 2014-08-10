//
//  SanaAttributedLabel.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaAttributedLabel.h"

@implementation SanaAttributedLabel

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.edgeInsets = UIEdgeInsetsMake(5, 5, 5, 5);
    }
    return self;
}

- (void)drawRect:(CGRect)rect
{
    [super drawTextInRect:UIEdgeInsetsInsetRect(rect, self.edgeInsets)];
}

@end
