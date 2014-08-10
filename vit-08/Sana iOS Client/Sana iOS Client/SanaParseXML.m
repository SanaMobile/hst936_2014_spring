//
//  SanaParseXML.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/19/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaParseXML.h"

#define PADDING 10
#define INPUT_FIELD_HEIGHT 30
#define LABEL_HEIGHT 20
#define TEXT_VIEW_HEIGHT 300
#define PICKER_HEIGHT 150
#define NORMAL_FONT_SIZE 15.0f
#define IMPORTANT_FONT_SIZE 18.0f
#define MULTIMEDIA_WIDTH 100
#define MULTIMEDIA_HEIGHT 60

@interface SanaParseXML() {
    double current_y;
}
@property (nonatomic, strong) SanaProcedureDetailsViewController *controller;
@property (nonatomic, strong) GDataXMLElement *page;

@property (nonatomic, strong) NSMutableArray *existingElements;
@property (nonatomic, strong) NSMutableArray *allElements;
@property (nonatomic, strong) NSArray *previousAnswers;
@property (nonatomic, strong) NSMutableArray *viewsArray;

@property (nonatomic, retain) NSMutableParagraphStyle *paraAttr;
@property (nonatomic, retain) NSDictionary *attrDictionary;
@property (nonatomic, retain) NSDictionary *attrDictionary2;

@property (nonatomic, strong) SanaAttributedTextField *geoField;
@end

@implementation SanaParseXML

- (UIView *)loadProcedureForPage:(GDataXMLElement *)page forDelegate:(UIViewController *)controller withExistingArray:(NSMutableArray *)elements onPageNumber:(int)pageNumber onPreviousAnswer:(NSArray *)previousAnswers {
    self.page = page;
    self.allElements = elements;
    self.previousAnswers = previousAnswers;

    NSMutableArray *currentElements = [[NSMutableArray alloc] init];
    self.existingElements = currentElements;

    // NSAttributedString Support
    self.paraAttr = [[NSMutableParagraphStyle defaultParagraphStyle] mutableCopy];
    [self.paraAttr setAlignment:NSTextAlignmentLeft];
    [self.paraAttr setLineBreakMode:NSLineBreakByWordWrapping];
    self.attrDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                           [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE], NSFontAttributeName,
                           [UIColor whiteColor], NSForegroundColorAttributeName,
                           self.paraAttr, NSParagraphStyleAttributeName, nil];
    self.attrDictionary2 = [NSDictionary dictionaryWithObjectsAndKeys:
                            [UIFont fontWithName:HELVETICA_LIGHT size:IMPORTANT_FONT_SIZE], NSFontAttributeName,
                            [UIColor whiteColor], NSForegroundColorAttributeName,
                            self.paraAttr, NSParagraphStyleAttributeName, nil];

    self.controller = (SanaProcedureDetailsViewController *)controller;
    self.viewsArray = [[NSMutableArray alloc] init];
    double _x = MAIN_SCREEN_WIDTH * elements.count;

    if([self validateElement:page]) {
        // RENDER THIS PAGE
        UIScrollView *v = [[UIScrollView alloc] initWithFrame:CGRectMake(PADDING + _x, PADDING, MAIN_SCREEN_WIDTH - (2 * PADDING), MAIN_SCREEN_HEIGHT_NC - (4 * PADDING) - PAGE_CONTROL_HEIGHT)];
        [v setBackgroundColor:[UIColor colorWithWhite:1.0 alpha:TRANSLUCENT_ALPHA]];

        [self.viewsArray addObject:v];
        _x += MAIN_SCREEN_WIDTH;

        current_y = PADDING;
        for (GDataXMLElement *pageChild in page.children) {
            if([pageChild.name isEqual:@"Element"]) {
                [self createWidgetFromXML:pageChild inView:v];
            }
        }

        if(current_y <= v.bounds.size.height) {
            [v setScrollEnabled:NO];
            CGRect frame = v.frame;
            frame.origin.y = frame.size.height/2 - current_y/2;
            frame.size.height = current_y;
            v.frame = frame;
        } else {
            [v setScrollEnabled:YES];
        }

        [v setScrollEnabled:YES];
        [v setBounces:YES];
        [v setContentSize:CGSizeMake(v.bounds.size.width, current_y)];

        // ADD TO THE OBJECTS ARRAY
        NSDictionary *dict = [[NSDictionary alloc] initWithObjects:@[[NSNumber numberWithInt:pageNumber], self.existingElements]
                                                           forKeys:@[@"Page", @"Elements"]];
        [elements addObject:dict];
        
        return v;
    }

    // PROCEED TO NEXT PAGE
    return nil;
}

- (BOOL)validateElement:(GDataXMLElement *)element {
    int notFlag = 0;
    NSArray *showif = [element elementsForName:@"ShowIf"];
    if(showif.count == 0)
        return YES;

    GDataXMLElement *showIfElement = showif[0];
    NSArray *notElements = [showIfElement elementsForName:@"not"];
    if(notElements.count == 0){
        notFlag = 0;
    } else {
        notFlag = 1;
        showIfElement = notElements[0];
    }

    GDataXMLElement *elem = showIfElement.children[0];
    BOOL result = [self evaluateShowIfWithOperatorWithElement:elem];
    return notFlag ? !result : result;
}

- (BOOL)evaluateShowIfWithOperatorWithElement:(GDataXMLElement *)element{
    NSString *operator = [element name];

    if([operator isEqual:@"Criteria"]) {
        NSString *type = [[element attributeForName:@"type"] stringValue];
        NSString *elementId = [[element attributeForName:@"id"] stringValue];
        NSString *value = [[element attributeForName:@"value"] stringValue];
        return [self evaluateCriteriaWithType:type elementId:elementId value:value];
    } else if([operator isEqual:@"or"]) {
        BOOL ans = YES;
        for(GDataXMLElement *elem in element.children) {
            ans = ans || [self evaluateShowIfWithOperatorWithElement:elem];
        }
        return ans;
    } else if([operator isEqual:@"and"]) {
        BOOL ans = YES;
        for(GDataXMLElement *elem in element.children) {
            ans = ans && [self evaluateShowIfWithOperatorWithElement:elem];
        }
        return ans;
    } else if([operator isEqual:@"not"]) {
        return [self evaluateShowIfWithOperatorWithElement:element.children[0]];
    }

    return YES;
}

- (BOOL)evaluateCriteriaWithType:(NSString *)type elementId:(NSString *)elementId value:(NSString *)value {
    NSString *elementValue = [self elementForId:elementId];

    if([type isEqual:@"GREATER"]) {
        if([elementValue intValue] > [value intValue]) {
            return YES;
        }
        return NO;
    } else if([type isEqual:@"LESS"]) {
        if([elementValue intValue] < [value intValue]) {
            return YES;
        }
        return NO;
    } else {
        if([[elementValue lowercaseString] isEqual:[value lowercaseString]]) {
            return YES;
        }
        return NO;
    }
}

- (NSString *)elementForId:(NSString *)elemId{
    for(NSDictionary *dict in self.allElements) {
        for(UIView *view in dict[@"Elements"]) {
            if([view valueForKey:@"elementId"] != nil) {
                if([[view valueForKey:@"elementId"] isEqual:elemId]) {
                    return [view valueForKey:@"answer"];
                }
            }
        }
    }

    return nil;
}

-(void) createWidgetFromXML:(GDataXMLElement *)element inView:(UIView *)view {

    NSString *type = [[element attributeForName:@"type"] stringValue];

    if ([type isEqualToString:@"ENTRY"]) {
        // TEXT INPUT
        [self createInputField:element inView:view forNumberInput:NO];
    }
    else if ([type isEqualToString:@"TEXT"]){
        // LABEL
        [self createLabel:[[element attributeForName:@"question"] stringValue] inView:view];
    }
    else if ([type isEqualToString:@"SELECT"]){
        // PICKER
        [self createPicker:element inView:view];
    }
    else if ([type isEqualToString:@"PATIENT_ID"]){
        // NUMBER INPUT
        [self createInputField:element inView:view forNumberInput:NO];
    }
    else if ([type isEqualToString:@"MULTI_SELECT"]){
        // MULTIPLE SELECT
        [self createMultiSelectPicker:element inView:view];
    }
    else if ([type isEqualToString:@"RADIO"]){
        // SELECT ONE
        [self createPicker:element inView:view];
    }
    else if ([type isEqualToString:@"IMAGE"] || [type isEqualToString:@"PICTURE"]){
        // IMAGE PICKER
//        [self element:element inView:view];
        [self createImagePicker:element inView:view];
    }
//    else if ([type isEqualToString:@"PICTURE"]){
//        // IMAGE PICKER
//        [self element:element inView:view];
//    }
    else if ([type isEqualToString:@"SOUND"]){
        // RECORD SOUND
    }
    else if ([type isEqualToString:@"BINARYFILE"]){
        /*
         * NO REFERENCE
         */
        [self element:element inView:view];
    }
    else if ([type isEqualToString:@"INVALID"]){
        /*
         * NO REFERENCE
         */
        [self element:element inView:view];
    }
    else if ([type isEqualToString:@"GPS"] || [type isEqualToString:@"GEOGRAPHICAL LOCATION"]){
        // RECORD CURRENT GPS COORDINATES
        [self createGeographicalLabel:element inView:view];
    }
    else if ([type isEqualToString:@"TIME"]){
        // TIME PICKER
        [self createDatePicker:element inView:view forDate:NO];
    }
    else if ([type isEqualToString:@"DATE"]){
        // DATE PICKER
        [self createDatePicker:element inView:view forDate:YES];
    }
    else if ([type isEqualToString:@"EDUCATION_RESOURCE"]){
        /*
         * ONLY FOR ANDROID
         */
        [self element:element inView:view];
    }
    else if ([type isEqualToString:@"PLUGIN"]){
        /*
         * ONLY FOR ANDROID 
         */
        [self element:element inView:view];
    }
    else if ([type isEqualToString:@"PLUGIN_ENTRY"]){
        /* 
         * ONLY FOR ANDROID 
         */
        [self element:element inView:view];
    }
    else {
        NSLog(@"%@ : Element cannot be parsed.", type);
    }
}

- (NSString *)answerForId:(NSString *)elemId {
    for(Answer *ans in self.previousAnswers) {
        if([ans.elementId isEqualToString:elemId])
            return ans.answer;
    }

    return @"";
}

#pragma mark iOS Widget Creation Methods
/************************************************/
/* Converts:                                    */
/* <ELEMENT type="ENTRY"                        */
/* Into:                                        */
/*    UILabel                                   */
/*    UITextField                               */
/************************************************/
-(UITextField *)createInputField:(GDataXMLElement *) inputElement inView:(UIView *)view forNumberInput:(BOOL)numberInput  {

    if ([[[inputElement attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[inputElement attributeForName:@"question"] stringValue] inView:view];
    }

    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
    inputField.backgroundColor = [UIColor whiteColor];
    inputField.delegate = self.controller;
    inputField.textAlignment = NSTextAlignmentCenter;
    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];

    inputField.elementId = [[inputElement attributeForName:@"id"] stringValue];
    inputField.question = [[inputElement attributeForName:@"question"] stringValue];
    inputField.answer = [self answerForId:inputField.elementId];
    [inputField setText:[self answerForId:inputField.elementId]];
    inputField.concept = [[inputElement attributeForName:@"concept"] stringValue];

    [self.existingElements addObject:inputField];

    [[NSNotificationCenter defaultCenter] addObserver:self.controller selector:@selector(textFieldDidChangeValue:) name:UITextFieldTextDidChangeNotification object:inputField];

    if(numberInput) inputField.keyboardType = UIKeyboardTypeNumberPad;

    [view addSubview:inputField];
    current_y += INPUT_FIELD_HEIGHT + PADDING;

    return inputField;
}

- (void)element:(GDataXMLElement *)element inView:(UIView *)view {
    if ([[[element attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[element attributeForName:@"question"] stringValue] inView:view];
    }

    NSString *type = [[[element attributeForName:@"type"] stringValue] lowercaseString];
    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
    inputField.backgroundColor = [UIColor whiteColor];
    inputField.delegate = self.controller;
    inputField.textAlignment = NSTextAlignmentCenter;
    inputField.enabled = NO;
    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
    inputField.text = [type stringByAppendingString:@" feature coming soon"];

    [view addSubview:inputField];
    current_y += INPUT_FIELD_HEIGHT + PADDING;
}

-(void)createGeographicalLabel:(GDataXMLElement *) geoLocationElement inView:(UIView *) view {

    if ([[[geoLocationElement attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[geoLocationElement attributeForName:@"question"] stringValue] inView:view];
    }

    CLLocationManager *locationManager = [[CLLocationManager alloc] init];
    [locationManager startUpdatingLocation];

    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
    inputField.backgroundColor = [UIColor whiteColor];
    inputField.delegate = self.controller;
    inputField.textAlignment = NSTextAlignmentCenter;
    inputField.enabled = NO;
    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];

    inputField.elementId = [[geoLocationElement attributeForName:@"id"] stringValue];
    inputField.question = [[geoLocationElement attributeForName:@"question"] stringValue];
    inputField.answer = [self answerForId:inputField.elementId];
    [inputField setText:[self answerForId:inputField.elementId]];
    inputField.concept = [[geoLocationElement attributeForName:@"concept"] stringValue];

    self.geoField = inputField;

    [self.existingElements addObject:inputField];

    [view addSubview:inputField];
    current_y += INPUT_FIELD_HEIGHT + PADDING;
}

- (void)locationManager:(CLLocationManager *)manager didChangeAuthorizationStatus:(CLAuthorizationStatus)status {
    if(status == kCLAuthorizationStatusAuthorized) {
        if(self.geoField != nil) {
            self.geoField.text = [NSString stringWithFormat:@"%f, %f", manager.location.coordinate.latitude, manager.location.coordinate.longitude];
        }
    } else {
        if(self.geoField != nil) {
            self.geoField.text = @"No Permission";
        }
    }
}

- (void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations {
    if(self.geoField == nil)
        return;

    if(locations.count == 0) {
        self.geoField.text = @"Could not update";
    } else {
        self.geoField.text = [NSString stringWithFormat:@"%f, %f", ((CLLocation *)locations[0]).coordinate.latitude, ((CLLocation *)locations[0]).coordinate.longitude];
    }
}

-(void)createTextArea:(GDataXMLElement *) textAreaElement inView:(UIView *)view  {

    if ([[[textAreaElement attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[textAreaElement attributeForName:@"question"] stringValue] inView:view];
    }

    SanaAttributedTextView *textView = [[SanaAttributedTextView alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), TEXT_VIEW_HEIGHT)];
    textView.backgroundColor = [UIColor whiteColor];
    textView.delegate = self.controller;
    textView.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];

    textView.elementId = [[textAreaElement attributeForName:@"id"] stringValue];
    textView.question = [[textAreaElement attributeForName:@"question"] stringValue];
    textView.answer = [[textAreaElement attributeForName:@"answer"] stringValue];
    textView.concept = [[textAreaElement attributeForName:@"concept"] stringValue];

    [self.existingElements addObject:textView];

    [view addSubview:textView];
    current_y += TEXT_VIEW_HEIGHT + PADDING;
}

-(void)createLabel:(NSString *)labelText inView:(UIView *)view  {
    SanaAttributedLabel *label = [[SanaAttributedLabel alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), LABEL_HEIGHT)];
    label.backgroundColor = [UIColor clearColor];
    label.textColor = NAVIGATION_COLOR;
    label.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
    label.textAlignment = NSTextAlignmentLeft;
    label.text = labelText;

    [view addSubview:label];
    current_y += LABEL_HEIGHT + PADDING/2;
}

-(void)createPicker:(GDataXMLElement *) select1Element inView:(UIView *)view  {

    if ([[[select1Element attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[select1Element attributeForName:@"question"] stringValue] inView:view];
    }

    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
    inputField.backgroundColor = [UIColor whiteColor];
    inputField.delegate = self.controller;
    inputField.textAlignment = NSTextAlignmentCenter;
    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];

    inputField.elementId = [[select1Element attributeForName:@"id"] stringValue];
    inputField.question = [[select1Element attributeForName:@"question"] stringValue];
    inputField.answer = [self answerForId:inputField.elementId];
    [inputField setText:[self answerForId:inputField.elementId]];
    inputField.concept = [[select1Element attributeForName:@"concept"] stringValue];

    [self.existingElements addObject:inputField];

    [view addSubview:inputField];
    current_y += INPUT_FIELD_HEIGHT + PADDING;

    NSMutableArray *itemsArray = [[NSMutableArray alloc] initWithArray:[[[select1Element attributeForName:@"choices"] stringValue] componentsSeparatedByString:@","]];
    NSMutableArray *finalItems = [[NSMutableArray alloc] init];
    for(NSString *item in itemsArray){
        [finalItems addObject:[item stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]]];
    }

    SanaAttributedPickerView *select1 = [[SanaAttributedPickerView alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), PICKER_HEIGHT) withArray:finalItems onTextField:inputField];
    select1.showsSelectionIndicator = YES;
    [view addSubview:select1];
    current_y += PICKER_HEIGHT + PADDING;
}

-(void)createMultiSelectPicker:(GDataXMLElement *) select1Element inView:(UIView *)view  {

    if ([[[select1Element attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[select1Element attributeForName:@"question"] stringValue] inView:view];
    }

    NSMutableArray *itemsArray = [[NSMutableArray alloc] initWithArray:[[[select1Element attributeForName:@"choices"] stringValue] componentsSeparatedByString:@","]];
    NSMutableArray *finalItems = [[NSMutableArray alloc] init];
    for(NSString *item in itemsArray){
        [finalItems addObject:[item stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]]];
    }

    SanaAttributedTableView *tableView = [[SanaAttributedTableView alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), view.bounds.size.height - LABEL_HEIGHT - PADDING) withOptions:[finalItems componentsJoinedByString:@","] andAnswers:[self answerForId:[[select1Element attributeForName:@"id"] stringValue]] onDelegate:self.controller withElementId:[[select1Element attributeForName:@"id"] stringValue]];
    tableView.elementId = [[select1Element attributeForName:@"id"] stringValue];
    tableView.question = [[select1Element attributeForName:@"question"] stringValue];
    tableView.answer = [self answerForId:[[select1Element attributeForName:@"id"] stringValue]];
    [view addSubview:tableView];
    
    [self.existingElements addObject:tableView];
    current_y += view.bounds.size.height + PADDING;
}

-(void)createDatePicker:(GDataXMLElement *) select1Element inView:(UIView *)view forDate:(BOOL)dateFlag {

    if ([[[select1Element attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[select1Element attributeForName:@"question"] stringValue] inView:view];
    }

    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
    inputField.backgroundColor = [UIColor whiteColor];
    inputField.delegate = self.controller;
    inputField.textAlignment = NSTextAlignmentCenter;
    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];

    inputField.elementId = [[select1Element attributeForName:@"id"] stringValue];
    inputField.question = [[select1Element attributeForName:@"question"] stringValue];
    inputField.answer = [self answerForId:inputField.elementId];
    [inputField setText:[self answerForId:inputField.elementId]];
    inputField.concept = [[select1Element attributeForName:@"concept"] stringValue];

    [self.existingElements addObject:inputField];

    [view addSubview:inputField];
    current_y += INPUT_FIELD_HEIGHT + PADDING;

    SanaAttributedDatePickerView *datePickerView = [[SanaAttributedDatePickerView alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT) onTextField:inputField forDateFlag:dateFlag];
    [view addSubview:datePickerView];
    current_y += PICKER_HEIGHT + PADDING;
}

-(void)createCheckboxes:(GDataXMLElement *) selectElement inView:(UIView *)view {

    if ([[[selectElement attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[selectElement attributeForName:@"question"] stringValue] inView:view];
    }

    NSArray *itemsArray = [selectElement elementsForName:@"xforms:item"];
    for (GDataXMLElement *item in itemsArray) {
        GDataXMLElement *valueElement = [[item elementsForName:@"xforms:value"] objectAtIndex:0];

        double width = (view.bounds.size.width * 0.75) - (2 * PADDING);
        //        CGRect frame = [[valueElement stringValue] boundingRectWithSize:CGSizeMake(width, CGFLOAT_MAX) options:NSStringDrawingUsesLineFragmentOrigin|NSStringDrawingTruncatesLastVisibleLine attributes:self.attrDictionary context:nil];
        SanaAttributedLabel *label = [[SanaAttributedLabel alloc]initWithFrame:CGRectMake(PADDING, current_y, width, LABEL_HEIGHT)];
        label.backgroundColor = [UIColor clearColor];
        label.textColor = NAVIGATION_COLOR;
        label.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
        label.textAlignment = NSTextAlignmentLeft;
        label.text = [valueElement stringValue];
        [view addSubview:label];

        SanaAttributedSwitch *attributedSwitch = [[SanaAttributedSwitch alloc] initWithFrame:CGRectMake((view.bounds.size.width * 0.75) + PADDING, current_y, (view.bounds.size.width * 0.25) - (2 * PADDING), LABEL_HEIGHT)];
        attributedSwitch.onTintColor = ORANGE_COLOR;
        attributedSwitch.tintColor = ORANGE_COLOR;
        [attributedSwitch addTarget:self.controller action:@selector(didChangeSwitchValue:) forControlEvents:UIControlEventValueChanged];

        attributedSwitch.elementId = [[selectElement attributeForName:@"id"] stringValue];
        attributedSwitch.question = [[selectElement attributeForName:@"question"] stringValue];
        attributedSwitch.answer = [[selectElement attributeForName:@"answer"] stringValue];
        attributedSwitch.concept = [[selectElement attributeForName:@"concept"] stringValue];

        [self.existingElements addObject:attributedSwitch];

        [view addSubview:attributedSwitch];
        current_y += LABEL_HEIGHT + (5 * PADDING);
    }
}

-(void)createImagePicker:(GDataXMLElement *) imageElement inView:(UIView *)view {
    if ([[[imageElement attributeForName:@"question"] stringValue] length] > 0) {
        [self createLabel:[[imageElement attributeForName:@"question"] stringValue] inView:view];
    }

    UIImage *img = nil;
    NSString *existing = [self answerForId:[[imageElement attributeForName:@"id"] stringValue]];
    if([existing length] > 0) {
        NSData *data = [NSData dataWithContentsOfFile:existing];
        img = [UIImage imageWithData:data];
    }

    SanaAttributedPicturePicker *picturePicker = [[SanaAttributedPicturePicker alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), PICKER_HEIGHT) withSelectedImage:img];
    picturePicker.elementId = [[imageElement attributeForName:@"id"] stringValue];
    picturePicker.question = [[imageElement attributeForName:@"question"] stringValue];
    [picturePicker addTarget:self.controller action:@selector(didTapPicturePicker:) forControlEvents:UIControlEventTouchDown];

    [self.existingElements addObject:picturePicker];

    [view addSubview:picturePicker];
    current_y += PICKER_HEIGHT + PADDING;
}

/*
 widgetPackage is {"widgetType" = "multimediaGUI","widgetObject"= [UIButton, UIButton,UIButton]}
 */
-(void)createMultimediaCaptureGUI:(GDataXMLElement *) inputElement inView:(UIView *)view {

    UIButton *addPictureButton = [[UIButton alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 - MULTIMEDIA_WIDTH - PADDING, current_y, MULTIMEDIA_WIDTH, MULTIMEDIA_HEIGHT)];
    addPictureButton.backgroundColor = [UIColor clearColor];
    [addPictureButton setImage:[UIImage imageNamed:@"add_photo.png"] forState:UIControlStateNormal];
    [addPictureButton setImage:[UIImage imageNamed:@"add_photo_h.png"] forState:UIControlStateHighlighted];

    [view addSubview:[self createMultimediaBadgeForNumber:0 inView:view]];
    [view addSubview:addPictureButton];

    current_y += MULTIMEDIA_HEIGHT + PADDING;

    UIButton *addVideoButton = [[UIButton alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 - MULTIMEDIA_WIDTH - PADDING, current_y, MULTIMEDIA_WIDTH, MULTIMEDIA_HEIGHT)];
    [addVideoButton setImage:[UIImage imageNamed:@"add_video.png"] forState:UIControlStateNormal];
    [addVideoButton setImage:[UIImage imageNamed:@"add_video_h.png"] forState:UIControlStateHighlighted];

    [view addSubview:[self createMultimediaBadgeForNumber:0 inView:view]];
    [view addSubview:addVideoButton];

    current_y += MULTIMEDIA_HEIGHT + PADDING;

    UIButton *addAudioButton = [[UIButton alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 - MULTIMEDIA_WIDTH - PADDING, current_y, MULTIMEDIA_WIDTH, MULTIMEDIA_HEIGHT)];
    [addAudioButton setImage:[UIImage imageNamed:@"add_video.png"] forState:UIControlStateNormal];
    [addAudioButton setImage:[UIImage imageNamed:@"add_video_h.png"] forState:UIControlStateHighlighted];

    [view addSubview:[self createMultimediaBadgeForNumber:0 inView:view]];
    [view addSubview:addAudioButton];

    current_y += MULTIMEDIA_HEIGHT + PADDING;
}

- (UILabel *)createMultimediaBadgeForNumber:(int)number inView:(UIView *)view{
    UILabel *badge1 = [[UILabel alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 + PADDING, current_y + MULTIMEDIA_HEIGHT/4, MULTIMEDIA_HEIGHT/2, MULTIMEDIA_HEIGHT/2)];
    [badge1 setBackgroundColor:ORANGE_COLOR];
    [badge1.layer setCornerRadius:MULTIMEDIA_HEIGHT/4];
    [badge1 setClipsToBounds:YES];
    [badge1 setTextColor:[UIColor whiteColor]];
    [badge1 setFont:[UIFont fontWithName:HELVETICA_THIN size:20.0]];
    [badge1 setAdjustsFontSizeToFitWidth:YES];
    [badge1 setTextAlignment:NSTextAlignmentCenter];
    [badge1 setText:[NSString stringWithFormat:@"%d", number]];
    return badge1;
}

@end