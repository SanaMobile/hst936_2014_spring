//
//  SanaProcedureDetailsViewController.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/20/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaProcedureDetailsViewController.h"

#define PAGE_CONTROL_HEIGHT 30
#define ELEMENT_HEIGHT 30
#define PADDING 10

@interface SanaProcedureDetailsViewController (){
    BOOL viewMoved;
    double _x;
    int currentPage;
}
@property (nonatomic, strong) GDataXMLDocument *domDocument;
@property (nonatomic, strong) UIScrollView *backgroundScrollView;
@property (nonatomic, strong) UIScrollView *procedureScrollView;
@property (nonatomic, strong) UITextField *currentTextField;
@property (nonatomic, strong) UITextView *currentTextView;
@property (nonatomic, strong) UIBarButtonItem *nextButton;
@property (nonatomic, strong) UIBarButtonItem *backButton;

@property (nonatomic, strong) NSMutableArray *widgetsArray;
@property (nonatomic, strong) NSMutableArray *currentGroupWidgetsArray;

@property (nonatomic, strong) Procedure *procedure;
@property (nonatomic, strong) NSArray *allPages;
@property (nonatomic, strong) NSMutableArray *currentElements;
@property (nonatomic, strong) NSMutableArray *allAnsweredElements;
@property (nonatomic, strong) SanaParseXML *xmlparser;
@end

@implementation SanaProcedureDetailsViewController

- (id)initWithProcedure:(Procedure *)procedure {
    self = [super initWithNibName:nil bundle:nil];
    if (self) {

        self.procedure = procedure;
        self.domDocument = [self loadDocumentWithProcedure:procedure];

        // NAVIGATION BUTTON
        self.nextButton = [[UIBarButtonItem alloc] initWithTitle:@"Next" style:UIBarButtonItemStylePlain target:self action:@selector(didTapNext:)];
        self.backButton = [[UIBarButtonItem alloc] initWithTitle:@"Close" style:UIBarButtonItemStylePlain target:self action:@selector(didTapPrev:)];
        [self.navigationItem setLeftBarButtonItem:self.backButton];
        [self.navigationItem setRightBarButtonItem:self.nextButton];

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
        [self.procedureScrollView setScrollEnabled:NO];

        self.xmlparser = [[SanaParseXML alloc] init];
        self.allPages = [self getPages:self.domDocument];

        currentPage = 0;
        self.allAnsweredElements = [[NSMutableArray alloc] init];
        NSMutableArray *newArray = self.allAnsweredElements;

        GDataXMLElement *firstPage = self.allPages[currentPage];
        UIView *firstView = [self.xmlparser loadProcedureForPage:firstPage forDelegate:self withExistingArray:newArray onPageNumber:currentPage];
        [firstView setTag:100 + currentPage];

        self.allAnsweredElements = newArray;
        currentPage = _x = 0;
        [self.procedureScrollView addSubview:firstView];
        _x += SCREEN_WIDTH;

        [self.procedureScrollView setContentSize:CGSizeMake(_x, self.procedureScrollView.bounds.size.height)];

        [self setupScrollView:self.backgroundScrollView];
        [self setupScrollView:self.procedureScrollView];
        [self.view addSubview:self.backgroundScrollView];
        [self.view addSubview:self.procedureScrollView];
        
        [self.navigationItem setTitle:[self getProcedureTitle:self.domDocument]];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

-(NSArray *)getPages:(GDataXMLDocument *) domDoc {
    NSError *error;
    NSArray *groupsArray = [domDoc nodesForXPath:@"//Page" error:&error];
    return groupsArray;
}

-(NSString *)getProcedureTitle:(GDataXMLDocument *) domDoc {
    NSError *error;
    NSArray *groupsArray = [domDoc nodesForXPath:@"//Procedure" error:&error];
    if(groupsArray.count == 0)
        return @"Procedure";

    GDataXMLElement *element = groupsArray[0];
    return [[element attributeForName:@"title"] stringValue];
}

- (void)didTapPrev:(id)sender {
    if(currentPage == 0) {
        // CLOSE
        [self dismissViewControllerAnimated:YES completion:nil];
        return;
    }

    _x -= SCREEN_WIDTH;
    [self.procedureScrollView setContentOffset:CGPointMake(_x - SCREEN_WIDTH, self.procedureScrollView.contentOffset.y) animated:YES];

    [self removeElementsInScrollView:self.procedureScrollView afterOffset:_x];
    [self.allAnsweredElements removeLastObject];

    currentPage = [[[self.allAnsweredElements lastObject] objectForKey:@"Page"] intValue];

    [self updateButtonTitles];

    [self resignKeyboard];
}

- (void)didTapNext:(id)sender {
    if(currentPage >= self.allPages.count - 1) {
        // FINISHED
        [self terminateProcedure];
        return;
    }

    UIView *nextView;
    NSMutableArray *newArray = self.allAnsweredElements;

    while(nextView == nil) {
        currentPage++;
        if(currentPage == self.allPages.count)
            break;

        GDataXMLElement *nextPage = self.allPages[currentPage];
        nextView = [self.xmlparser loadProcedureForPage:nextPage forDelegate:self withExistingArray:newArray onPageNumber:currentPage];
        self.allAnsweredElements = newArray;
    }

    if(currentPage == self.allPages.count) {
        [self terminateProcedure];
    } else {

        [self.procedureScrollView addSubview:nextView];

        double currentX = _x;
        _x += SCREEN_WIDTH;

        [self.procedureScrollView setContentSize:CGSizeMake(_x, self.procedureScrollView.bounds.size.height)];
        [self.procedureScrollView setContentOffset:CGPointMake(currentX, self.procedureScrollView.contentOffset.y) animated:YES];
        [self updateButtonTitles];
    }

    [self resignKeyboard];
}

- (void)updateButtonTitles {
    if(currentPage == 0) {
        [self.backButton setTitle:@"Close"];
        [self.nextButton setTitle:@"Next"];
    } else if(currentPage == self.allPages.count - 1) {
        [self.backButton setTitle:@"Prev"];
        [self.nextButton setTitle:@"Done"];
    } else {
        [self.backButton setTitle:@"Prev"];
        [self.nextButton setTitle:@"Next"];
    }
}

- (void)resignKeyboard {
    // RESIGN KEYBOARD ASYNCHRONOUSLY
    dispatch_async(dispatch_get_main_queue(), ^{
        if(self.currentTextField != nil)
            [self.currentTextField resignFirstResponder];

        if(self.currentTextView != nil)
            [self.currentTextView resignFirstResponder];
    });
}

- (void)terminateProcedure {
    NSString *final = @"";
    for(NSDictionary *dict in self.allAnsweredElements) {
        for(UIView *view in dict[@"Elements"]) {
            NSString *pageNumber = [NSString stringWithFormat:@"%@", dict[@"Page"]];
            final = [[[final stringByAppendingString:@"Page : "] stringByAppendingString:pageNumber] stringByAppendingString:@", "];
            final = [[[final stringByAppendingString:@"Element : "] stringByAppendingString:[view valueForKey:@"elementId"]] stringByAppendingString:@" => "];
            final = [final stringByAppendingString:@" "];
            final = [final stringByAppendingString:[view valueForKey:@"answer"]];
            final = [final stringByAppendingString:@"\n"];
        }
    }

    for(NSDictionary *dict in self.allAnsweredElements) {
        for(UIView *view in dict[@"Elements"]) {
            NSString *answer = [view valueForKey:@"answer"];
            NSString *elementId = [view valueForKey:@"elementId"];

            Answer *ans = [[SanaCoreData sharedCoreData] createObjectNamed:@"Answer"];
            if(ans) {
                [ans setProcedure:self.procedure];
                [ans setAnswer:answer];
                [ans setElementId:elementId];
            }
        }
    }
    [[SanaCoreData sharedCoreData] save];

    [[[UIAlertView alloc] initWithTitle:@"Procedure" message:@"Complete" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil] show];
}



- (void)removeElementsInScrollView:(UIScrollView *)scrollView afterOffset:(double)xPoint {
    for(UIView *view in scrollView.subviews) {
        if(view.frame.origin.x > xPoint) {
            [UIView animateWithDuration:0.2 delay:0.0 options:UIViewAnimationOptionTransitionCurlDown animations:^{
                [view setAlpha:0.0];
            } completion:^(BOOL finished) {
                [view removeFromSuperview];
            }];
        }
    }
}

- (void)setupScrollView:(UIScrollView *)scrollView {
    scrollView.pagingEnabled = YES;
    scrollView.showsHorizontalScrollIndicator = NO;
    scrollView.showsVerticalScrollIndicator = NO;
    scrollView.scrollsToTop = NO;
}

- (GDataXMLDocument *)loadSampleDocument {
    NSString *pathToProcedure = [[NSBundle mainBundle] pathForResource:@"sample_1" ofType:@"xml"];
    NSData *xmlNSData = [[NSMutableData alloc] initWithContentsOfFile:pathToProcedure];
    NSError *error;
    return [[GDataXMLDocument alloc] initWithData:xmlNSData options:0 error:&error];
}

- (GDataXMLDocument *)loadDocumentWithProcedure:(Procedure *)procedure {
    NSData *data = [[NSData alloc] initWithData:[NSData dataWithContentsOfFile:procedure.originalFile]];
    NSError *error;
    return [[GDataXMLDocument alloc] initWithData:data options:0 error:&error];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)elementForId:(NSString *)elemId{
    for(NSDictionary *dict in self.allAnsweredElements) {
        for(UIView *view in dict[@"Elements"]) {
            if([view valueForKey:@"elementId"] != nil) {
                if([[view valueForKey:@"elementId"] isEqual:elemId]) {
                    return YES;
                }
            }
        }
    }

    return NO;
}

- (void)setAnswer:(NSString *)answer forElementId:(NSString *)elemId {
    for(NSDictionary *dict in self.allAnsweredElements) {
        for(UIView *view in dict[@"Elements"]) {
            if([view valueForKey:@"elementId"] != nil) {
                if([[view valueForKey:@"elementId"] isEqual:elemId]) {
                    [view setValue:answer forKey:@"answer"];
                }
            }
        }
    }
}

// DELEGATES TO RECORD ANSWERS
- (void)didChangeSwitchValue:(id)sender {
    SanaAttributedSwitch *sanaSwitch = (SanaAttributedSwitch *)sender;
    if([self elementForId:sanaSwitch.elementId]) {
        [self setAnswer:(sanaSwitch.isOn) ? @"YES" : @"NO" forElementId:sanaSwitch.elementId];
    }
}

- (void)textFieldDidChangeValue:(NSNotification *)notification {
    SanaAttributedTextField *textField = (SanaAttributedTextField *)notification.object;
    if([self elementForId:textField.elementId]) {
        [self setAnswer:textField.text forElementId:textField.elementId];
    }
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

    UIScrollView *superView = (UIScrollView *)textField.superview;
    [superView setContentOffset:CGPointMake(0, 0) animated:YES];

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
    [self resignKeyboard];
}

@end