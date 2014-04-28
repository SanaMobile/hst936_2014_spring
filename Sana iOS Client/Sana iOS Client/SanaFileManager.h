//
//  SanaFileManager.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Procedure.h"

@interface SanaFileManager : NSObject

+ (Procedure *)saveProcedure:(NSData *)data forType:(NSString *)extension withName:(NSString *)title;

@end
