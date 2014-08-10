//
//  SanaAttributedPicturePicker.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 5/1/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SanaAttributedPicturePicker : UIButton

@property (nonatomic, strong) UIImagePickerController *imagePickerController;
@property (nonatomic, strong) NSString *elementId;
@property (nonatomic, strong) NSString *question;
@property (nonatomic, retain) UIImage *selectedImage;
@property (nonatomic, strong) UIViewController *controller;

- (id)initWithFrame:(CGRect)frame withSelectedImage:(UIImage *)image;

- (void)setSelectedImage:(UIImage *)selectedImage;

@end
