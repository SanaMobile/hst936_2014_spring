//
//  SanaProcedureDetailViewController.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/3/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaProcedureDetailViewController.h"

#define PAGE_CONTROL_HEIGHT 30
#define ELEMENT_HEIGHT 30
#define PADDING 10

@interface SanaProcedureDetailViewController () <UIScrollViewDelegate>
@property (nonatomic, strong) GDataXMLDocument *domDocument;
@property (nonatomic, strong) UIPageControl *pageControl;
@property (nonatomic, strong) UIScrollView *backgroundScrollView;
@property (nonatomic, strong) UIScrollView *procedureScrollView;

@property (nonatomic, strong) NSMutableArray *widgetsArray;
@property (nonatomic, strong) NSMutableArray *currentGroupWidgetsArray;
@end

@implementation SanaProcedureDetailViewController

- (id)initWithProcedureDocument:(GDataXMLDocument *)document
{
    self = [super initWithNibName:nil bundle:nil];
    if (self) {
        self.domDocument = [self loadSampleDocument];

        // NAVIGATION BUTTON
        UIBarButtonItem *leftButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(didTapCancel:)];
        UIBarButtonItem *rightButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(didTapDone:)];
        [self.navigationItem setLeftBarButtonItem:leftButton];
        [self.navigationItem setRightBarButtonItem:rightButton];

        // PAGECONTROL
        self.pageControl = [[UIPageControl alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT - PAGE_CONTROL_HEIGHT, SCREEN_WIDTH, PAGE_CONTROL_HEIGHT)];
        [self.pageControl setBackgroundColor:[UIColor colorWithWhite:1.0 alpha:0.6]];
        [self.pageControl setPageIndicatorTintColor:[SanaColorManager colorWithHexString:@"E5CDB8"]];
        [self.pageControl setCurrentPageIndicatorTintColor:[SanaColorManager colorWithHexString:@"E67E22"]];

        // SCROLLVIEW 1 : BACKGROUND
        self.backgroundScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
        UIImageView *backgroundImageView = [[UIImageView alloc] initWithFrame:CGRectMake(- SCREEN_WIDTH, 0, SCREEN_WIDTH * 4, SCREEN_HEIGHT)];
        [backgroundImageView setImage:[UIImage imageNamed:@"mountain_village.jpg"]];
        [backgroundImageView setContentMode:UIViewContentModeScaleAspectFill];
        [self.backgroundScrollView addSubview:backgroundImageView];
        [self.backgroundScrollView setContentSize:CGSizeMake(SCREEN_WIDTH * 2, SCREEN_HEIGHT)];

        // SCROLLVIEW 2 : PROCEDURE
        self.procedureScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, NC_HEIGHT + STATUS_BAR_HEIGHT + PADDING, SCREEN_WIDTH, SCREEN_HEIGHT_NC - (2 * PADDING) - PAGE_CONTROL_HEIGHT)];
        [self.procedureScrollView setBackgroundColor:[UIColor clearColor]];
        [self.procedureScrollView setDelegate:self];

        SanaLoadProcedureFromXML *loader = [[SanaLoadProcedureFromXML alloc] init];
        NSArray *views = [loader loadProcedureFromDocument:[self loadSampleDocument]];

        double _x = 0;
//        for(int i=0; i<5; i++) {
//            UIView *v = [[UIView alloc] initWithFrame:CGRectMake(PADDING + _x, PADDING, SCREEN_WIDTH - (2 * PADDING), self.procedureScrollView.bounds.size.height - (2 * PADDING))];
//            [v setBackgroundColor:[UIColor colorWithWhite:0.7 alpha:0.4]];
////            [SanaImageManager addBlurToView:v];
//            [self.procedureScrollView addSubview:v];
//            _x += SCREEN_WIDTH;
//        }

        for(int i=0; i<views.count; i++) {
            UIView *v = [views objectAtIndex:i];
            [self.procedureScrollView addSubview:v];
            _x += SCREEN_WIDTH;
        }

        [self.procedureScrollView setContentSize:CGSizeMake(_x, self.procedureScrollView.bounds.size.height)];
        [self.pageControl setNumberOfPages:ceil(self.procedureScrollView.contentSize.width / SCREEN_WIDTH)];
        [self.pageControl setCurrentPage:0];

        [self setupScrollView:self.backgroundScrollView];
        [self setupScrollView:self.procedureScrollView];

        [self.view addSubview:self.backgroundScrollView];
        [self.view addSubview:self.procedureScrollView];
        [self.view addSubview:self.pageControl];

        [self.navigationItem setTitle:@"Procedure"];
    }
    return self;
}

- (void)didTapCancel:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)didTapDone:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)setupScrollView:(UIScrollView *)scrollView {
    scrollView.pagingEnabled = YES;
    scrollView.showsHorizontalScrollIndicator = NO;
    scrollView.showsVerticalScrollIndicator = NO;
    scrollView.scrollsToTop = NO;
}

- (GDataXMLDocument *)loadSampleDocument {
    NSString *pathToProcedure = [[NSBundle mainBundle] pathForResource:@"test_proc" ofType:@"xml"];
    NSData *xmlNSData = [[NSMutableData alloc] initWithContentsOfFile:pathToProcedure];
    NSError *error;
    return [[GDataXMLDocument alloc] initWithData:xmlNSData options:0 error:&error];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    if(scrollView == self.procedureScrollView) {
        // PARALLAX EFFECT
        float speedFactor = self.backgroundScrollView.contentSize.width / scrollView.contentSize.width;
        [self.backgroundScrollView setContentOffset:CGPointMake(speedFactor * scrollView.contentOffset.x, 0)];

        // PAGECONTROL UPDATE
        CGFloat pageWidth = scrollView.frame.size.width;
        int currentPage = floor((scrollView.contentOffset.x - pageWidth / 2) / pageWidth) + 1;
        [self.pageControl setCurrentPage:currentPage];
    }
}

- (void)addFormToView:(UIView *)view withFields:(NSArray *)array {
    UIScrollView *scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, view.bounds.size.width, view.bounds.size.height)];
    [scrollView setShowsVerticalScrollIndicator:NO];
    [scrollView setScrollsToTop:YES];

    double _y = PADDING;
    double _width = view.bounds.size.width - (2 * PADDING);
    double _height = 20.0f;
    for(NSDictionary *dict in array) {
        [scrollView addSubview:[self getLabelWithText:dict[@"label"] ofFrame:CGRectMake(PADDING, _y, _width, _height)]];
        _y += _height + PADDING;
        [scrollView addSubview:[self getFieldOfFrame:CGRectMake(PADDING, _y, _width, _height)]];
        _y += _height + PADDING;
    }
}

- (UILabel *)getLabelWithText:(NSString *)text ofFrame:(CGRect)frame {
    UILabel *label = [[UILabel alloc] initWithFrame:frame];
    [label setText:text];
    [label setFont:[UIFont systemFontOfSize:15.0]];
    return label;
}

- (UITextField *)getFieldOfFrame:(CGRect)frame {
    UITextField *textField = [[UITextField alloc] initWithFrame:frame];
    [textField setFont:[UIFont systemFontOfSize:15.0]];
    return textField;
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

@end
