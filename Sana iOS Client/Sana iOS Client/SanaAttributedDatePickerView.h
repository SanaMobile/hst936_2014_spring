//
//  SanaAttributedDatePickerView.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/20/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SanaAttributedLabel.h"

@interface SanaAttributedDatePickerView : UIDatePicker <UIPickerViewDelegate>

@property (nonatomic, strong) UITextField *textField;

- (id)initWithFrame:(CGRect)frame onTextField:(UITextField *)textField forDateFlag:(BOOL)dateFlag;

@end
