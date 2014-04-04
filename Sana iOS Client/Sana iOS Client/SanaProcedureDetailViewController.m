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

@interface SanaProcedureDetailViewController () {
    BOOL viewMoved;
}
@property (nonatomic, strong) GDataXMLDocument *domDocument;
@property (nonatomic, strong) UIPageControl *pageControl;
@property (nonatomic, strong) UIScrollView *backgroundScrollView;
@property (nonatomic, strong) UIScrollView *procedureScrollView;
@property (nonatomic, strong) UITextField *currentTextField;
@property (nonatomic, strong) UITextView *currentTextView;
@property (nonatomic, strong) UIBarButtonItem *doneButton;

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
        self.doneButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(didTapDone:)];
        [self.doneButton setEnabled:NO];
        [self.navigationItem setLeftBarButtonItem:leftButton];
        [self.navigationItem setRightBarButtonItem:self.doneButton];

        // PAGECONTROL
        self.pageControl = [[UIPageControl alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT - PAGE_CONTROL_HEIGHT, SCREEN_WIDTH, PAGE_CONTROL_HEIGHT)];
        [self.pageControl setBackgroundColor:[UIColor colorWithWhite:1.0 alpha:TRANSLUCENT_ALPHA]];
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
        NSArray *views = [loader loadProcedureFromDocument:[self loadSampleDocument] forDelegate:self];

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
    NSString *pathToProcedure = [[NSBundle mainBundle] pathForResource:@"test_proc_2" ofType:@"xml"];
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

        if(currentPage == self.pageControl.numberOfPages - 1) {
            [self.doneButton setEnabled:YES];
        } else {
            [self.doneButton setEnabled:NO];
        }
    }

    // RESIGN KEYBOARD ASYNCHRONOUSLY
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self.currentTextField != nil)
            [self.currentTextField resignFirstResponder];

        if(self.currentTextView != nil)
            [self.currentTextView resignFirstResponder];
    });

}

// TEXTFIELD AND TEXTVIEW DELEGATES
- (void)textFieldDidBeginEditing:(UITextField *)textField {
    self.currentTextField = textField;
    [textField.layer setBorderColor:ORANGE_COLOR.CGColor];
    [textField.layer setBorderWidth:1.0];

    double height = textField.frame.origin.y - (3 * PADDING);
    UIScrollView *superView = (UIScrollView *)textField.superview;
    [superView setContentOffset:CGPointMake(0, height) animated:YES];
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    [textField.layer setBorderColor:[UIColor whiteColor].CGColor];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

- (void)textViewDidBeginEditing:(UITextView *)textView {
    self.currentTextView = textView;
    [textView.layer setBorderColor:ORANGE_COLOR.CGColor];
    [textView.layer setBorderWidth:1.0];
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    [textView.layer setBorderColor:[UIColor whiteColor].CGColor];
}

// TOUCH EVENT CAPTURE
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.view resignFirstResponder];
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
