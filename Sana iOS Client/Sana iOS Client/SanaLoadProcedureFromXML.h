//
//  SanaLoadProcedureFromXML.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Defines.h"
#import "GDataXMLNode.h"
#import "SanaImageManager.h"

@interface SanaLoadProcedureFromXML : NSObject

- (NSArray *)loadProcedureFromDocument:(GDataXMLDocument *)document;

@property (nonatomic, weak) id delegate;

@end

@protocol LoadProcedureDelegate <NSObject>

- (void)didLoadProcedureViewsArray:(NSArray *)array;

@end
