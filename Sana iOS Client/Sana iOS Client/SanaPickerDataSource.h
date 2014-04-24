//
//  SanaPickerDataSource.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Defines.h"
#import "SanaColorManager.h"
#import "SanaAttributedLabel.h"
#import "SanaAttributedTextField.h"

@interface SanaPickerDataSource : NSObject <UIPickerViewDelegate, UIPickerViewDataSource>

- (id)initWithDataSourceArray:(NSArray *)array forPicker:(UIPickerView *)picker withTextField:(UITextField *)textField;

@end
