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
#import "Procedure.h"
#import "Answer.h"
#import "SanaCoreData.h"
#import "SanaParseXML.h"
#import "SanaFileManager.h"

@interface SanaProcedureDetailsViewController : UIViewController <UIScrollViewDelegate, UITextFieldDelegate, UITextViewDelegate, MultiSelectDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate>

- (id)initWithProcedure:(Procedure *)procedure inEditMode:(BOOL)editMode;

- (void)didChangeSwitchValue:(id)sender;
- (void)textFieldDidChangeValue:(id)sender;
- (void)didTapPicturePicker:(SanaAttributedPicturePicker *)picker;

@end
