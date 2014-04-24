//
//  SanaProcedureDetailsViewController.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/20/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Defines.h"
#import "SanaColorManager.h"
#import "SanaImageManager.h"
#import "GDataXMLNode.h"
#import "SanaLoadProcedureFromXML.h"
#import "SanaParseXML.h"

@interface SanaProcedureDetailsViewController : UIViewController <UIScrollViewDelegate, UITextFieldDelegate, UITextViewDelegate>

- (id)initWithProcedureDocument:(GDataXMLDocument *)document;

- (void)didChangeSwitchValue:(id)sender;
- (void)textFieldDidChangeValue:(id)sender;

@end
