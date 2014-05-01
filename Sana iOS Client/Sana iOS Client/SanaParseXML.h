//
//  SanaParseXML.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/19/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "Defines.h"
#import "GDataXMLNode.h"
#import "SanaImageManager.h"
#import "SanaAttributedLabel.h"
#import "SanaAttributedTextField.h"
#import "SanaAttributedTextView.h"
#import "SanaAttributedPickerView.h"
#import "SanaAttributedSwitch.h"
#import "SanaAttributedDatePickerView.h"
#import "SanaAttributedTableView.h"
#import "SanaAttributedPicturePicker.h"
#import "Element.h"
#import "Procedure.h"
#import "Answer.h"

#import "SanaProcedureDetailsViewController.h"

@interface SanaParseXML : NSObject <CLLocationManagerDelegate>

- (UIView *)loadProcedureForPage:(GDataXMLElement *)page forDelegate:(UIViewController *)controller withExistingArray:(NSMutableArray *)elements onPageNumber:(int)pageNumber onPreviousAnswer:(NSArray *)previousAnswers;

@end

@protocol LoadProcedureDelegate <NSObject>

- (void)didLoadProcedureViewsArray:(NSArray *)array;

@end
