//
//  SanaAttributedPickerView.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SanaPickerDataSource.h"
#import "SanaAttributedLabel.h"

@interface SanaAttributedPickerView : UIPickerView

@property (nonatomic, strong) SanaPickerDataSource *dataSourceAndDelegate;
@property (nonatomic, strong) NSArray *dataSourceArray;
@property (nonatomic, strong) UITextField *textField;

- (id)initWithFrame:(CGRect)frame withArray:(NSArray *)array onTextField:(UITextField *)textField;

@end
