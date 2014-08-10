//
//  SanaAttributedTableView.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SanaMultiSelectDataSource.h"

@interface SanaAttributedTableView : UITableView

@property (nonatomic, strong) SanaMultiSelectDataSource *dataSourceAndDelegate;
@property (nonatomic, strong) NSString *elementId;
@property (nonatomic, strong) NSString *question;
@property (nonatomic, strong) NSString *answer;

- (id)initWithFrame:(CGRect)frame withOptions:(NSString *)options andAnswers:(NSString *)answers onDelegate:(id)controller withElementId:(NSString *)elementId;

@end
