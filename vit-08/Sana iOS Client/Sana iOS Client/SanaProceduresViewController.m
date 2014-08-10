//
//  SanaProceduresViewController.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/2/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaProceduresViewController.h"
#import "SanaFileManager.h"
#import "SanaCoreData.h"

#define PADDING 10
#define ROW_HEIGHT 50.0f

@interface SanaProceduresViewController () <UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) UIView *containerView;
@property (nonatomic, strong) UITableView *proceduresTableView;
@property (nonatomic, strong) NSArray *procedures;
@end

@implementation SanaProceduresViewController

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

        self.procedures = [self getSampleProcedures];

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

    NSDictionary *dict = self.procedures[indexPath.row];

    cell.textLabel.text = dict[@"name"];

    return cell;
}

// UITABLEVIEW DATA DELEGATE
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];

    NSDictionary *dict = self.procedures[indexPath.row];
    NSString *url = dict[@"url"];

    NSURLRequest *req = [[NSURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
    [NSURLConnection sendAsynchronousRequest:req queue:[NSOperationQueue mainQueue] completionHandler:^(NSURLResponse *response, NSData *data, NSError *connectionError) {

        if(data != nil) {
            NSError *error;
            GDataXMLDocument *xmlDoc = [[GDataXMLDocument alloc] initWithData:data options:0 error:&error];
            if(error) {
                [[[UIAlertView alloc] initWithTitle:@"Sana" message:@"Unable to parse document" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil] show];
                return;
            }

            NSString *title = [self getProcedureTitle:xmlDoc];
            Procedure *newProcedure = [SanaFileManager saveProcedure:data forType:@"xml" withName:title];

            SanaProcedureDetailsViewController *detailView = [[SanaProcedureDetailsViewController alloc] initWithProcedure:newProcedure inEditMode:NO];
            UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:detailView];
            [self presentViewController:navigationController animated:YES completion:^{
                [self.navigationController popViewControllerAnimated:YES];
            }];
        } else {
            [[[UIAlertView alloc] initWithTitle:@"Sana" message:@"Please check your internet connection" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil] show];
        }
    }];
}

-(NSString *)getProcedureTitle:(GDataXMLDocument *) domDoc {
    NSError *error;
    NSArray *groupsArray = [domDoc nodesForXPath:@"//Procedure" error:&error];
    if(groupsArray.count == 0)
        return @"Procedure";

    GDataXMLElement *element = groupsArray[0];
    return [[element attributeForName:@"title"] stringValue];
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

- (NSArray *)getSampleProcedures {
    NSMutableArray *proc = [[NSMutableArray alloc] init];
    NSString *initUrl = @"https://moca.googlecode.com/svn/clients/android/tags/release-1.1/res/raw";


    NSDictionary *d1 = [NSDictionary dictionaryWithObjects:@[@"Cervical Cancer", [initUrl stringByAppendingPathComponent:@"cervicalcancer.xml"]]
                                                   forKeys:@[@"name", @"url"]];
    NSDictionary *d2 = [NSDictionary dictionaryWithObjects:@[@"Teledermatology", [initUrl stringByAppendingPathComponent:@"derma.xml"]]
                                                   forKeys:@[@"name", @"url"]];
    NSDictionary *d3 = [NSDictionary dictionaryWithObjects:@[@"Oral Cancer", [initUrl stringByAppendingPathComponent:@"oral_cancer.xml"]]
                                                   forKeys:@[@"name", @"url"]];
    NSDictionary *d4 = [NSDictionary dictionaryWithObjects:@[@"Prenatal Screening", [initUrl stringByAppendingPathComponent:@"prenatal.xml"]]
                                                   forKeys:@[@"name", @"url"]];
    NSDictionary *d5 = [NSDictionary dictionaryWithObjects:@[@"TB Contact Assessment", [initUrl stringByAppendingPathComponent:@"tbcontact.xml"]]
                                                   forKeys:@[@"name", @"url"]];

    [proc addObject:d1];
    [proc addObject:d2];
    [proc addObject:d3];
    [proc addObject:d4];
    [proc addObject:d5];

    return proc;
}

@end
