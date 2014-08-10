//
//  SanaStoredProceduresViewController.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaStoredProceduresViewController.h"
#import "SanaFileManager.h"
#import "SanaCoreData.h"

#define PADDING 10
#define ROW_HEIGHT 50.0f

@interface SanaStoredProceduresViewController () <UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) UIView *containerView;
@property (nonatomic, strong) UITableView *proceduresTableView;
@property (nonatomic, strong) NSArray *procedures;
@end

@implementation SanaStoredProceduresViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        int numberOfRowsPossible = (SCREEN_HEIGHT_NC - PADDING * 2)/ROW_HEIGHT;
        int rows = [self tableView:nil numberOfRowsInSection:0];
        double tableHeight = SCREEN_HEIGHT_NC - PADDING * 2;
        if(rows < numberOfRowsPossible) {
            tableHeight = rows * ROW_HEIGHT;
        }

        self.procedures = [self getStoredProcedures];

        self.containerView = [[UIView alloc] initWithFrame:CGRectMake(PADDING, PADDING + NC_HEIGHT + STATUS_BAR_HEIGHT, SCREEN_WIDTH - PADDING * 2, SCREEN_HEIGHT - PADDING * 2 - (NC_HEIGHT + STATUS_BAR_HEIGHT))];
        [SanaImageManager addBlurToView:self.containerView];

        self.proceduresTableView = [[UITableView alloc] initWithFrame:self.containerView.bounds];
        [self.proceduresTableView setDelegate:self];
        [self.proceduresTableView setDataSource:self];
        [self.proceduresTableView setRowHeight:ROW_HEIGHT];
        [self.proceduresTableView setSeparatorStyle:UITableViewCellSeparatorStyleSingleLine];
        [self.proceduresTableView setBackgroundColor:[UIColor clearColor]];

        [self.containerView addSubview:self.proceduresTableView];
        [self.view addSubview:self.containerView];

        //        [self.view addSubview:self.proceduresTableView];

        [self.navigationItem setTitle:@"Procedures"];
        [self.view setBackgroundColor:[UIColor clearColor]];

        NSArray *array = [[SanaCoreData sharedCoreData] getFetchResultsForEntityName:@"Procedure" usingPredicate:nil inContext:[[SanaCoreData sharedCoreData] managedObjectContext] error:nil];
        NSLog(@"%d", array.count);
    }
    return self;
}

// UITABLEVIEW DATA SOURCE
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.procedures.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *cellIdentifier = @"CellIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        UIImageView *rightArrow = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"list_arrow.png"]];
        cell.accessoryView = rightArrow;
        cell.backgroundColor = [UIColor clearColor];
        cell.textLabel.font = [UIFont fontWithName:HELVETICA_LIGHT size:15.0];
        cell.textLabel.textColor = NAVIGATION_COLOR;
    }

    Procedure *proc = self.procedures[indexPath.row];
    cell.textLabel.text = proc.name;

    return cell;
}

// UITABLEVIEW DATA DELEGATE
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];

    Procedure *proc = self.procedures[indexPath.row];
    SanaProcedureDetailsViewController *detailView = [[SanaProcedureDetailsViewController alloc] initWithProcedure:proc inEditMode:YES];
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:detailView];
    [self presentViewController:navigationController animated:YES completion:^{
        [self.navigationController popViewControllerAnimated:YES];
    }];
}

- (void)tableView:(UITableView *)tableView didHighlightRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    cell.textLabel.textColor = ORANGE_COLOR;
    UIImageView *rightArrow = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"list_arrow_h.png"]];
    cell.accessoryView = rightArrow;
}

- (void)tableView:(UITableView *)tableView didUnhighlightRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    cell.textLabel.textColor = NAVIGATION_COLOR;
    UIImageView *rightArrow = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"list_arrow.png"]];
    cell.accessoryView = rightArrow;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSArray *)getStoredProcedures {
    NSArray *proc = [[SanaCoreData sharedCoreData] getFetchResultsForEntityName:@"Procedure" usingPredicate:nil inContext:[[SanaCoreData sharedCoreData] managedObjectContext] error:nil];
    return proc;
}

@end