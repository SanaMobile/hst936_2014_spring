//
//  SanaProcedureDetailViewController.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/3/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Defines.h"
#import "SanaColorManager.h"
#import "SanaImageManager.h"
#import "GDataXMLNode.h"
#import "SanaLoadProcedureFromXML.h"

@interface SanaProcedureDetailViewController : UIViewController

- (id)initWithProcedureDocument:(GDataXMLDocument *)document;

@end
