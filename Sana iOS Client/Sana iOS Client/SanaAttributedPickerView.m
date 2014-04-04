//
//  SanaAttributedPickerView.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaAttributedPickerView.h"

@implementation SanaAttributedPickerView

- (id)initWithFrame:(CGRect)frame withArray:(NSArray *)array onTextField:(UITextField *)textField
{
    self = [super initWithFrame:frame];
    if (self) {
        self.dataSourceArray = array;
        self.textField = textField;
        self.dataSourceAndDelegate = [[SanaPickerDataSource alloc] initWithDataSourceArray:self.dataSourceArray forPicker:self withTextField:self.textField];
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
