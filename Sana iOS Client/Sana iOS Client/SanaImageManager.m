//
//  SanaImageManager.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/2/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaImageManager.h"

@implementation SanaImageManager

+ (void)addBlurToView:(UIView *)view {
    [view setBackgroundColor:[UIColor clearColor]];
    UIToolbar *toolBar = [[UIToolbar alloc] initWithFrame:view.bounds];
    toolBar.barStyle = UIBarStyleDefault;
    [view insertSubview:toolBar atIndex:0];
}

@end
