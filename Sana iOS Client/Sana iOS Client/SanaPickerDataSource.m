//
//  SanaPickerDataSource.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaPickerDataSource.h"

#define PADDING 10

@interface SanaPickerDataSource()
@property (nonatomic, strong) NSArray *array;
@property (nonatomic, strong) UIPickerView *pickerView;
@property (nonatomic, strong) UITextField *textField;
@end

@implementation SanaPickerDataSource

- (id)initWithDataSourceArray:(NSArray *)array forPicker:(UIPickerView *)picker withTextField:(UITextField *)textField {
    self = [super init];
    if(self) {
        self.array = array;
        self.pickerView = picker;
        self.textField = textField;

        [picker setDelegate:self];
        [picker setDataSource:self];
        
        [textField setUserInteractionEnabled:NO];
    }
    return self;
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return self.array.count;
}

//- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
//    return self.array[row];
//}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component {
    return 40.0f;
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view {
    SanaAttributedLabel *label = [[SanaAttributedLabel alloc]initWithFrame:CGRectMake(PADDING, 0, self.pickerView.bounds.size.width - (2 * PADDING), 40.0f)];
    label.backgroundColor = [UIColor clearColor];
    label.textColor = NAVIGATION_COLOR;
    label.font = [UIFont fontWithName:HELVETICA_SEMIBOLD size:30.0];
    label.textAlignment = NSTextAlignmentCenter;
    label.text = self.array[row];
    return label;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    self.textField.text = self.array[row];
}

@end
