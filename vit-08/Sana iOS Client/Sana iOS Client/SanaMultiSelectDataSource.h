//
//  SanaMultiSelectDataSource.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Defines.h"
#import "SanaColorManager.h"

@interface SanaMultiSelectDataSource : NSObject <UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, weak) id delegate;

- (id)initWithSource:(NSString *)options selectedAnswers:(NSString *)answers withDelegate:(id)controller forTable:(UITableView *)tableView withElementId:(NSString *)elementId;

@end

@protocol MultiSelectDelegate <NSObject>

- (void)multiSelectAnswersUpdated:(NSString *)answers onElementId:(NSString *)elementId;

@end