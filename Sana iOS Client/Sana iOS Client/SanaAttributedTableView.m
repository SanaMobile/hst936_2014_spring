//
//  SanaAttributedTableView.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaAttributedTableView.h"

@implementation SanaAttributedTableView

- (id)initWithFrame:(CGRect)frame withOptions:(NSString *)options andAnswers:(NSString *)answers onDelegate:(id)controller withElementId:(NSString *)elementId
{
    self = [super initWithFrame:frame];
    if (self) {
        self.dataSourceAndDelegate = [[SanaMultiSelectDataSource alloc] initWithSource:options selectedAnswers:answers withDelegate:controller forTable:self withElementId:elementId];
    }
    return self;
}

@end