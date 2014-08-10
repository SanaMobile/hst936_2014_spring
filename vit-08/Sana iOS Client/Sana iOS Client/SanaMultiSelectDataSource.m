//
//  SanaMultiSelectDataSource.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaMultiSelectDataSource.h"

@interface SanaMultiSelectDataSource()
@property (nonatomic, strong) NSMutableArray *allOptions;
@property (nonatomic, strong) NSMutableArray *allAnswers;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSString *elementId;
@end

@implementation SanaMultiSelectDataSource

- (id)initWithSource:(NSString *)options selectedAnswers:(NSString *)answers withDelegate:(id)controller forTable:(UITableView *)tableView withElementId:(NSString *)elementId {
    self = [super init];
    if(self) {
        self.delegate = controller;
        self.elementId = elementId;
        self.allOptions = [[NSMutableArray alloc] initWithArray:[options componentsSeparatedByString:@","]];
        self.allAnswers = answers.length > 0 ? [[NSMutableArray alloc] initWithArray:[answers componentsSeparatedByString:@","]] : [NSMutableArray array];

        self.tableView = tableView;
        self.tableView.delegate = self;
        self.tableView.dataSource = self;

        self.tableView.backgroundColor = [UIColor clearColor];
        self.tableView.backgroundView = nil;
    }
    return self;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.allOptions.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *cellIdentifier = @"CellIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleGray;
        cell.backgroundColor = [UIColor clearColor];
        cell.backgroundView = nil;
    }

    cell.textLabel.backgroundColor = [UIColor clearColor];
    cell.textLabel.textColor = NAVIGATION_COLOR;
    cell.textLabel.font = [UIFont fontWithName:HELVETICA_SEMIBOLD size:30.0];
    cell.textLabel.textAlignment = NSTextAlignmentLeft;
    cell.textLabel.text = self.allOptions[indexPath.row];

    if([self.allAnswers containsObject:self.allOptions[indexPath.row]]) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    } else {
        cell.accessoryType = UITableViewCellAccessoryNone;
    }

    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    UITableViewCell * cell = [tableView cellForRowAtIndexPath:indexPath];
    NSString *option = cell.textLabel.text;

    if([self.allAnswers containsObject:option]) {
        [self.allAnswers removeObject:option];
        cell.accessoryType = UITableViewCellAccessoryNone;
    } else {
        [self.allAnswers addObject:option];
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    }

    if([self.delegate respondsToSelector:@selector(multiSelectAnswersUpdated:onElementId:)]) {
        NSString *answers = [self.allAnswers componentsJoinedByString:@","];
        [self.delegate multiSelectAnswersUpdated:answers onElementId:self.elementId];
    }
}

@end
