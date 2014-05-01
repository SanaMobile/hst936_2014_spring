//
//  SanaAttributedPicturePicker.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 5/1/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaAttributedPicturePicker.h"

@implementation SanaAttributedPicturePicker

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (id)initWithFrame:(CGRect)frame withSelectedImage:(UIImage *)image
{
    self = [super initWithFrame:frame];
    if (self) {
        [self.imageView setContentMode:UIViewContentModeScaleAspectFit];
        [self setClipsToBounds:YES];

        if(image == nil) {
            UIImage *img = [UIImage imageNamed:@"choose_image.jpg"];
            [self setImage:img forState:UIControlStateNormal];
            [self setImage:img forState:UIControlStateHighlighted];
        } else {
            self.selectedImage = image;
        }
    }
    return self;
}

- (void)setSelectedImage:(UIImage *)selectedImage {
    [self setImage:selectedImage forState:UIControlStateNormal];
    [self setImage:selectedImage forState:UIControlStateHighlighted];
}

@end
