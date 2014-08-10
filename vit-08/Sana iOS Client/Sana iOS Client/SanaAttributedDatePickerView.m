//
//  SanaAttributedDatePickerView.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/20/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaAttributedDatePickerView.h"

@implementation SanaAttributedDatePickerView

- (id)initWithFrame:(CGRect)frame onTextField:(UITextField *)textField forDateFlag:(BOOL)dateFlag {

    self = [super initWithFrame:frame];
    if (self) {
        self.textField = textField;
        self.datePickerMode = dateFlag ? UIDatePickerModeDate : UIDatePickerModeTime;
    }
    return self;
}

@end
