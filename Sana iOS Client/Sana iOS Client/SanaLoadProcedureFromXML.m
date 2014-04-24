////
////  SanaLoadProcedureFromXML.m
////  Sana iOS Client
////
////  Created by Prince Shekhar on 4/4/14.
////  Copyright (c) 2014 MIT. All rights reserved.
////
//
//#import "SanaLoadProcedureFromXML.h"
//
//#define PADDING 10
//#define INPUT_FIELD_HEIGHT 30
//#define LABEL_HEIGHT 20
//#define TEXT_VIEW_HEIGHT 300
//#define PICKER_HEIGHT 150
//#define NORMAL_FONT_SIZE 15.0f
//#define IMPORTANT_FONT_SIZE 18.0f
//#define MULTIMEDIA_WIDTH 100
//#define MULTIMEDIA_HEIGHT 60
//
//@interface SanaLoadProcedureFromXML() {
//    double current_y;
//}
//@property (nonatomic, strong) SanaProcedureDetailViewController *controller;
//@property (nonatomic, strong) GDataXMLDocument *document;
//@property (nonatomic, strong) NSMutableArray *currentGroupWidgetsArray;
//@property (nonatomic, strong) NSMutableArray *procedureWidgetsArray;
//@property (nonatomic, strong) NSMutableArray *viewsArray;
//@property (nonatomic, retain) NSMutableParagraphStyle *paraAttr;
//@property (nonatomic, retain) NSDictionary *attrDictionary;
//@property (nonatomic, retain) NSDictionary *attrDictionary2;
//@end
//
//@implementation SanaLoadProcedureFromXML
//
//- (NSArray *)loadProcedureFromDocument:(GDataXMLDocument *)document forDelegate:(UIViewController *)controller {
//    self.document = document;
//    self.controller = (SanaProcedureDetailViewController *)controller;
//    self.viewsArray = [[NSMutableArray alloc] init];
//    double _x = 0.0;
//
//    NSArray *groupsArray = [self getGroups:self.document];
//    for (GDataXMLElement *group in groupsArray) {
//        UIScrollView *v = [[UIScrollView alloc] initWithFrame:CGRectMake(PADDING + _x, PADDING, MAIN_SCREEN_WIDTH - (2 * PADDING), MAIN_SCREEN_HEIGHT_NC - (4 * PADDING) - PAGE_CONTROL_HEIGHT)];
//        [v setBackgroundColor:[UIColor colorWithWhite:1.0 alpha:TRANSLUCENT_ALPHA]];
//
//        [self.viewsArray addObject:v];
//        _x += MAIN_SCREEN_WIDTH;
//        
//        current_y = PADDING;
//        for (GDataXMLElement *groupChild in group.children) {
//            [self createWidgetFromXML:groupChild inView:v];
//        }
//
//        if(current_y <= v.bounds.size.height) {
//            [v setScrollEnabled:NO];
//            CGRect frame = v.frame;
//            frame.origin.y = frame.size.height/2 - current_y/2;
//            frame.size.height = current_y;
//            v.frame = frame;
//        } else {
//            [v setScrollEnabled:YES];
//        }
//
//        [v setContentSize:CGSizeMake(v.bounds.size.width, current_y)];
//    }
//
//    // NSAttributedString Support
//    self.paraAttr = [[NSMutableParagraphStyle defaultParagraphStyle] mutableCopy];
//    [self.paraAttr setAlignment:NSTextAlignmentLeft];
//    [self.paraAttr setLineBreakMode:NSLineBreakByWordWrapping];
//    self.attrDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
//                           [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE], NSFontAttributeName,
//                           [UIColor whiteColor], NSForegroundColorAttributeName,
//                           self.paraAttr, NSParagraphStyleAttributeName, nil];
//    self.attrDictionary2 = [NSDictionary dictionaryWithObjectsAndKeys:
//                              [UIFont fontWithName:HELVETICA_LIGHT size:IMPORTANT_FONT_SIZE], NSFontAttributeName,
//                              [UIColor whiteColor], NSForegroundColorAttributeName,
//                              self.paraAttr, NSParagraphStyleAttributeName, nil];
//
//    return self.viewsArray;
//}
//
//-(NSArray *)getGroups:(GDataXMLDocument *) domDoc {
//    NSError *error;
//    NSArray *groupsArray = [domDoc nodesForXPath:@"//sana:group" error:&error];
//    return groupsArray;
//}
//
//-(void) createWidgetFromXML:(GDataXMLElement *)element inView:(UIView *)view {
//
//    if ([element.name isEqualToString:@"xforms:input"]) {
//        [self createInputField:element inView:view];
//    }
//    else if ([element.name isEqualToString:@"xforms:label"]){
//        [self createLabel:element inView:view];
//    }
//    else if ([element.name isEqualToString:@"xforms:textarea"]){
//            [self createTextArea:element inView:view];
//    }
//    else if ([element.name isEqualToString:@"xforms:select1"]){
//        [self createPicker:element inView:view];
//    }
//    else if ([element.name isEqualToString:@"xforms:select"]){
//        [self createCheckboxes:element inView:view];
//    }
//    else if ([element.name isEqualToString:@"xforms:repeat"]){
//        [self createMultimediaCaptureGUI:element inView:view];
//    }
//    else {
//        NSLog(@"%@ element is not going to be widgetified",element.name);
//    }
//}
//
//#pragma mark iOS Widget Creation Methods
///************************************************/
///* Converts:                                    */
///* <xforms:input ref="data/observation@1/value">*/
///* <xforms:label>Enter Text</xforms:label>      */
///* </xforms:input>                              */
///* Into:                                        */
///*    UILabel                                   */
///*    UITextField                               */
///************************************************/
//-(UITextField *)createInputField:(GDataXMLElement *) inputElement inView:(UIView *)view  {
//
//    //an <xforms:input> must have its first child as a <xforms:label>; not going to parse deeper than that
//    if ([[[inputElement childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        [self createLabel:[[inputElement elementsForName:@"xforms:label"] objectAtIndex:0] inView:view];
//    }
//
//    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
//    inputField.backgroundColor = [UIColor whiteColor];
//    inputField.delegate = self.controller;
//    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
//    inputField.ref = [inputElement attributeForName:@"ref"].stringValue;
//
//    [view addSubview:inputField];
//    current_y += INPUT_FIELD_HEIGHT + PADDING;
//
//    return inputField;
//}
//
////xforms:textarea = UITextView
//-(void)createTextArea:(GDataXMLElement *) textAreaElement inView:(UIView *)view  {
//
//    if ([[[textAreaElement childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        [self createLabel:[[textAreaElement elementsForName:@"xforms:label"] objectAtIndex:0] inView:view];
//    }
//
//    SanaAttributedTextView *textView = [[SanaAttributedTextView alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), TEXT_VIEW_HEIGHT)];
//    textView.backgroundColor = [UIColor whiteColor];
//    textView.delegate = self.controller;
//    textView.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
//    textView.ref = [textAreaElement attributeForName:@"ref"].stringValue;
//
//    [view addSubview:textView];
//    current_y += TEXT_VIEW_HEIGHT + PADDING;
//}
//
////xforms:label = UILabel
//-(void)createLabel:(GDataXMLElement *)labelElement inView:(UIView *)view  {
////    double width = view.bounds.size.width - (2 * PADDING);
////    CGRect frame = [[labelElement stringValue] boundingRectWithSize:CGSizeMake(width, CGFLOAT_MAX) options:NSStringDrawingUsesLineFragmentOrigin attributes:self.attrDictionary context:nil];
//
//    SanaAttributedLabel *label = [[SanaAttributedLabel alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), LABEL_HEIGHT)];
//    label.backgroundColor = [UIColor clearColor];
//    label.textColor = NAVIGATION_COLOR;
//    label.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
//    label.textAlignment = NSTextAlignmentLeft;
//    label.text = [labelElement stringValue];
//
//    [view addSubview:label];
//    current_y += LABEL_HEIGHT + PADDING/2;
//}
//
////xforms:select1 = UIPickerView
//-(void)createPicker:(GDataXMLElement *) select1Element inView:(UIView *)view  {
//
//    if ([[[select1Element childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        [self createLabel:[[select1Element elementsForName:@"xforms:label"] objectAtIndex:0] inView:view];
//    }
//
//    SanaAttributedTextField *inputField = [[SanaAttributedTextField alloc]initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), INPUT_FIELD_HEIGHT)];
//    inputField.backgroundColor = [UIColor whiteColor];
//    inputField.delegate = self.controller;
//    inputField.textAlignment = NSTextAlignmentCenter;
//    inputField.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
//    inputField.ref = [select1Element attributeForName:@"ref"].stringValue;
//    [view addSubview:inputField];
//    current_y += INPUT_FIELD_HEIGHT + PADDING;
//
//    NSArray *itemsArray = [select1Element elementsForName:@"xforms:item"];
//    NSMutableArray *pickerDatasourceArray = [[NSMutableArray alloc] init];
//    for (GDataXMLElement *item in itemsArray) {
//        GDataXMLElement *valueElement = [[item elementsForName:@"xforms:value"] objectAtIndex:0];
//        [pickerDatasourceArray addObject:[valueElement stringValue]];
//    }
//
//    SanaAttributedPickerView *select1 = [[SanaAttributedPickerView alloc] initWithFrame:CGRectMake(PADDING, current_y, view.bounds.size.width - (2 * PADDING), PICKER_HEIGHT) withArray:pickerDatasourceArray onTextField:inputField];
//    select1.showsSelectionIndicator = YES;
//    [view addSubview:select1];
//    current_y += PICKER_HEIGHT + PADDING;
//}
//
//-(void)createCheckboxes:(GDataXMLElement *) selectElement inView:(UIView *)view {
//
//    if ([[[selectElement childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        [self createLabel:[[selectElement elementsForName:@"xforms:label"] objectAtIndex:0] inView:view];
//    }
//
//    NSArray *itemsArray = [selectElement elementsForName:@"xforms:item"];
//    for (GDataXMLElement *item in itemsArray) {
//        GDataXMLElement *valueElement = [[item elementsForName:@"xforms:value"] objectAtIndex:0];
//
//        double width = (view.bounds.size.width * 0.75) - (2 * PADDING);
////        CGRect frame = [[valueElement stringValue] boundingRectWithSize:CGSizeMake(width, CGFLOAT_MAX) options:NSStringDrawingUsesLineFragmentOrigin|NSStringDrawingTruncatesLastVisibleLine attributes:self.attrDictionary context:nil];
//        SanaAttributedLabel *label = [[SanaAttributedLabel alloc]initWithFrame:CGRectMake(PADDING, current_y, width, LABEL_HEIGHT)];
//        label.backgroundColor = [UIColor clearColor];
//        label.textColor = NAVIGATION_COLOR;
//        label.font = [UIFont fontWithName:HELVETICA_LIGHT size:NORMAL_FONT_SIZE];
//        label.textAlignment = NSTextAlignmentLeft;
//        label.text = [valueElement stringValue];
//        [view addSubview:label];
//
//        SanaAttributedSwitch *attributedSwitch = [[SanaAttributedSwitch alloc] initWithFrame:CGRectMake((view.bounds.size.width * 0.75) + PADDING, current_y, (view.bounds.size.width * 0.25) - (2 * PADDING), LABEL_HEIGHT)];
//        attributedSwitch.onTintColor = ORANGE_COLOR;
//        attributedSwitch.tintColor = ORANGE_COLOR;
//        attributedSwitch.ref = [selectElement attributeForName:@"ref"].stringValue;
//        [view addSubview:attributedSwitch];
//        current_y += LABEL_HEIGHT + (5 * PADDING);
//    }
//}
//
///*
// widgetPackage is {"widgetType" = "multimediaGUI","widgetObject"= [UIButton, UIButton,UIButton]}
// */
//-(void)createMultimediaCaptureGUI:(GDataXMLElement *) inputElement inView:(UIView *)view {
//
//    UIButton *addPictureButton = [[UIButton alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 - MULTIMEDIA_WIDTH - PADDING, current_y, MULTIMEDIA_WIDTH, MULTIMEDIA_HEIGHT)];
//    addPictureButton.backgroundColor = [UIColor clearColor];
//    [addPictureButton setImage:[UIImage imageNamed:@"add_photo.png"] forState:UIControlStateNormal];
//    [addPictureButton setImage:[UIImage imageNamed:@"add_photo_h.png"] forState:UIControlStateHighlighted];
//
//    [view addSubview:[self createMultimediaBadgeForNumber:0 inView:view]];
//    [view addSubview:addPictureButton];
//
//    current_y += MULTIMEDIA_HEIGHT + PADDING;
//
//    UIButton *addVideoButton = [[UIButton alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 - MULTIMEDIA_WIDTH - PADDING, current_y, MULTIMEDIA_WIDTH, MULTIMEDIA_HEIGHT)];
//    [addVideoButton setImage:[UIImage imageNamed:@"add_video.png"] forState:UIControlStateNormal];
//    [addVideoButton setImage:[UIImage imageNamed:@"add_video_h.png"] forState:UIControlStateHighlighted];
//
//    [view addSubview:[self createMultimediaBadgeForNumber:0 inView:view]];
//    [view addSubview:addVideoButton];
//
//    current_y += MULTIMEDIA_HEIGHT + PADDING;
//
//    UIButton *addAudioButton = [[UIButton alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 - MULTIMEDIA_WIDTH - PADDING, current_y, MULTIMEDIA_WIDTH, MULTIMEDIA_HEIGHT)];
//    [addAudioButton setImage:[UIImage imageNamed:@"add_video.png"] forState:UIControlStateNormal];
//    [addAudioButton setImage:[UIImage imageNamed:@"add_video_h.png"] forState:UIControlStateHighlighted];
//
//    [view addSubview:[self createMultimediaBadgeForNumber:0 inView:view]];
//    [view addSubview:addAudioButton];
//
//    current_y += MULTIMEDIA_HEIGHT + PADDING;
//}
//
//- (UILabel *)createMultimediaBadgeForNumber:(int)number inView:(UIView *)view{
//    UILabel *badge1 = [[UILabel alloc] initWithFrame:CGRectMake(view.bounds.size.width/1.5 + PADDING, current_y + MULTIMEDIA_HEIGHT/4, MULTIMEDIA_HEIGHT/2, MULTIMEDIA_HEIGHT/2)];
//    [badge1 setBackgroundColor:ORANGE_COLOR];
//    [badge1.layer setCornerRadius:MULTIMEDIA_HEIGHT/4];
//    [badge1 setClipsToBounds:YES];
//    [badge1 setTextColor:[UIColor whiteColor]];
//    [badge1 setFont:[UIFont fontWithName:HELVETICA_THIN size:20.0]];
//    [badge1 setAdjustsFontSizeToFitWidth:YES];
//    [badge1 setTextAlignment:NSTextAlignmentCenter];
//    [badge1 setText:[NSString stringWithFormat:@"%d", number]];
//    return badge1;
//}
//
//@end
