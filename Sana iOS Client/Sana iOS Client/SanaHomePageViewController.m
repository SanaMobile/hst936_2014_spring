//
//  SanaHomePageViewController.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/2/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaHomePageViewController.h"

#define NUMBER_OF_BUTTONS 3
#define BUTTON_WIDTH 280
#define BUTTON_HEIGHT 44
#define CONTAINER_PADDING 10
#define BUTTON_MARGIN 10

@interface SanaHomePageViewController ()
@property (nonatomic, strong) UIView *containerView;
@property (nonatomic, strong) UIImageView *backgroundImage;
@property (nonatomic, strong) UIImageView *headerImageView;
@property (nonatomic, strong) UIButton *createEncounterButton;
@property (nonatomic, strong) UIButton *viewEncountersButton;
@property (nonatomic, strong) UIButton *viewNotificationsButton;
@end

@implementation SanaHomePageViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // HEADER IMAGE
        self.headerImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 100, 30)];
        [self.headerImageView setImage:[UIImage imageNamed:@"sana_logo"]];
        [self.headerImageView setContentMode:UIViewContentModeScaleAspectFit];
        [self.headerImageView setClipsToBounds:YES];
        [self.navigationItem setTitleView:self.headerImageView];
        [self.navigationItem.titleView sizeToFit];

        // BUTTONS CONTAINER
        double containerWidth = BUTTON_WIDTH + 2 * CONTAINER_PADDING;
        double containerHeight = (NUMBER_OF_BUTTONS * BUTTON_HEIGHT) + ((NUMBER_OF_BUTTONS + 1) * BUTTON_MARGIN);
        self.containerView = [[UIView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH_2 - containerWidth/2, SCREEN_HEIGHT - containerHeight - CONTAINER_PADDING, containerWidth, containerHeight)];
        [SanaImageManager addBlurToView:self.containerView];

        double y_margin = BUTTON_MARGIN;

        // BUTTON 1 : CREATE A NEW ENCOUNTER
        self.createEncounterButton = [[UIButton alloc] initWithFrame:CGRectMake(CONTAINER_PADDING, y_margin, BUTTON_WIDTH, BUTTON_HEIGHT)];
        [self.createEncounterButton setImage:[UIImage imageNamed:@"create_encounter.png"] forState:UIControlStateNormal];
        [self.createEncounterButton setImage:[UIImage imageNamed:@"create_encounter_h.png"] forState:UIControlStateHighlighted];
        [self.createEncounterButton addTarget:self action:@selector(createEncounterClicked:) forControlEvents:UIControlEventTouchUpInside];

        y_margin += BUTTON_HEIGHT + BUTTON_MARGIN;

        // BUTTON 2 : VIEW PREVIOUS ENCOUNTERS
        self.viewEncountersButton = [[UIButton alloc] initWithFrame:CGRectMake(CONTAINER_PADDING, y_margin, BUTTON_WIDTH, BUTTON_HEIGHT)];
        [self.viewEncountersButton setImage:[UIImage imageNamed:@"view_encounters.png"] forState:UIControlStateNormal];
        [self.viewEncountersButton setImage:[UIImage imageNamed:@"view_encounters_h.png"] forState:UIControlStateHighlighted];
        [self.viewEncountersButton addTarget:self action:@selector(viewEncountersClicked:) forControlEvents:UIControlEventTouchUpInside];

        y_margin += BUTTON_HEIGHT + BUTTON_MARGIN;

        // BUTTON 3 : VIEW NOTIFICATIONS
        self.viewNotificationsButton = [[UIButton alloc] initWithFrame:CGRectMake(CONTAINER_PADDING, y_margin, BUTTON_WIDTH, BUTTON_HEIGHT)];
        [self.viewNotificationsButton setImage:[UIImage imageNamed:@"view_notifications.png"] forState:UIControlStateNormal];
        [self.viewNotificationsButton setImage:[UIImage imageNamed:@"view_notifications_h.png"] forState:UIControlStateHighlighted];
        [self.viewNotificationsButton addTarget:self action:@selector(viewNotificationsClicked:) forControlEvents:UIControlEventTouchUpInside];

        [self.containerView addSubview:self.createEncounterButton];
        [self.containerView addSubview:self.viewEncountersButton];
        [self.containerView addSubview:self.viewNotificationsButton];

        [self.view addSubview:self.containerView];
    }
    return self;
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

// BUTTON EVENT HANDLERS
- (void)createEncounterClicked:(UIButton *)sender {
    SanaProceduresViewController *procedureController = [[SanaProceduresViewController alloc] init];
    [self.navigationController pushViewController:procedureController animated:YES];
}

- (void)viewEncountersClicked:(UIButton *)sender {

}


- (void)viewNotificationsClicked:(UIButton *)sender {

}


@end
