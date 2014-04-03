//
//  SanaLoadProcedureFromXML.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/4/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaLoadProcedureFromXML.h"

#define PADDING 10

@interface SanaLoadProcedureFromXML() {
    double current_y;
}
@property (nonatomic, strong) GDataXMLDocument *document;
@property (nonatomic, strong) NSMutableArray *currentGroupWidgetsArray;
@property (nonatomic, strong) NSMutableArray *procedureWidgetsArray;
@property (nonatomic, strong) NSMutableArray *viewsArray;
@end

@implementation SanaLoadProcedureFromXML

- (NSArray *)loadProcedureFromDocument:(GDataXMLDocument *)document {
    self.document = document;
    self.viewsArray = [[NSMutableArray alloc] init];
    double _x = 0.0;

    NSArray *groupsArray = [self getGroups:self.document];
    for (GDataXMLElement *group in groupsArray) {
        UIView *v = [[UIView alloc] initWithFrame:CGRectMake(PADDING + _x, PADDING, MAIN_SCREEN_WIDTH - (2 * PADDING), MAIN_SCREEN_HEIGHT_NC - (4 * PADDING) - PAGE_CONTROL_HEIGHT)];
        [SanaImageManager addBlurToView:v];
//        [v.layer setRasterizationScale:1.0];
//        [v.layer setShouldRasterize:YES];

        [self.viewsArray addObject:v];
        _x += MAIN_SCREEN_WIDTH;
        
        current_y = 0.0;
        for (GDataXMLElement *groupChild in group.children) {

//            NSMutableDictionary *widgetPackage = [self createWidgetFromXML:groupChild];
//            if (widgetPackage) {
//                [self.currentGroupWidgetsArray addObject:widgetPackage];
//            }
        }
    }

    return self.viewsArray;
}

-(NSArray *)getGroups:(GDataXMLDocument *) domDoc {
    NSError *error;
    NSArray *groupsArray = [domDoc nodesForXPath:@"//sana:group" error:&error];
    return groupsArray;
}

//-(id) createWidgetFromXML:(GDataXMLElement *)element {
//
//    id widgetPackage;
//    if ([element.name isEqualToString:@"xforms:input"]) {
//        widgetPackage = [self createInputField:element];
//    }
//    else if ([element.name isEqualToString:@"xforms:label"]){
//        widgetPackage = [self createLabel:element];
//    }
//    else if ([element.name isEqualToString:@"xforms:textarea"]){
//        widgetPackage = [self createTextArea:element];
//    }
//    else if ([element.name isEqualToString:@"xforms:select1"]){
//        widgetPackage = [self createPicker:element];
//    }
//    else if ([element.name isEqualToString:@"xforms:select"]){
//        widgetPackage = [self createCheckboxes:element];
//    }
//    else if ([element.name isEqualToString:@"xforms:repeat"]){
//        widgetPackage = [self createMultimediaCaptureGUI:element];
//        NSLog(@"widget Pckage inside create:%@",widgetPackage);
//    }
//    else {
//        NSLog(@"%@ element is not going to be widgetified",element.name);
//    }
//    return widgetPackage;
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
//-(NSMutableDictionary *)createInputField:(GDataXMLElement *) inputElement {
//
//    UITextField *inputField = [[UITextField alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width, self.scrollView.frame.size.height)];
//    double h = inputField.frame.size.height;
//    inputField.backgroundColor = [UIColor whiteColor];
//
//    //an <xforms:input> must have its first child as a <xforms:label>; not going to parse deeper than that
//    if ([[[inputElement childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        NSMutableDictionary *labelWidgetPackage = [self createLabel:[[inputElement elementsForName:@"xforms:label"] objectAtIndex:0]];
//        //add immediately b/c label goes on top of input box.
//        [self.currentGroupWidgetsArray addObject:labelWidgetPackage];
//    }
//
//    NSMutableDictionary *inputWidgetPackage = [[NSMutableDictionary alloc]init];
//    [inputWidgetPackage setObject:inputField forKey:@"widgetObject"];
//
//    return inputWidgetPackage;
//}
//
////xforms:textarea = UITextView
//-(NSMutableDictionary *)createTextArea:(GDataXMLElement *) textAreaElement {
//
//    UITextView *textView = [[UITextView alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width, 300)];
//    textView.backgroundColor = [UIColor whiteColor];
//
//    if ([[[textAreaElement childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        NSMutableDictionary *labelWidgetPackage = [self createLabel:[[textAreaElement elementsForName:@"xforms:label"] objectAtIndex:0]];
//        [self.currentGroupWidgetsArray addObject:labelWidgetPackage];
//
//    }
//
//    NSMutableDictionary *textAreaWidgetPackage = [[NSMutableDictionary alloc]init];
//    [textAreaWidgetPackage setObject:textView forKey:@"widgetObject"];
//
//    return textAreaWidgetPackage;
//}
//
////xforms:label = UILabel
//-(NSMutableDictionary *)createLabel:(GDataXMLElement *)labelElement {
//
//    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width, self.scrollView.frame.size.height)];
//    label.backgroundColor = [UIColor clearColor];
//    label.font = [UIFont boldSystemFontOfSize:18];
//    label.textAlignment = UITextAlignmentCenter;
//    label.text = [labelElement stringValue];
//    NSMutableDictionary *widgetPackage = [[NSMutableDictionary alloc]init];
//    [widgetPackage setObject:label forKey:@"widgetObject"];
//    //    [widgetPackage setObject:nil forKey:@"widgetDatasource"];
//    return widgetPackage;
//}
//
////xforms:select1 = UIPickerView
//-(NSMutableDictionary *)createPicker:(GDataXMLElement *) select1Element {
//
//    UIPickerView *select1 = [[UIPickerView alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width, self.scrollView.bounds.size.height)];
//    select1.showsSelectionIndicator = YES;
//
//    if ([[[select1Element childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        NSMutableDictionary *labelWidgetPackage = [self createLabel:[[select1Element elementsForName:@"xforms:label"] objectAtIndex:0]];
//        //add immediately b/c xforms:label goes on top of xforms:textarea in our UI.
//        [self.currentGroupWidgetsArray addObject:labelWidgetPackage];
//
//    }
//
//    NSArray *itemsArray = [select1Element elementsForName:@"xforms:item"];
//    NSMutableArray *pickerDatasourceArray = [[NSMutableArray alloc]init];
//    for (GDataXMLElement *item in itemsArray) {
//        GDataXMLElement *valueElement = [[item elementsForName:@"xforms:value"] objectAtIndex:0];
//        [pickerDatasourceArray addObject:[valueElement stringValue]];
//    }
//
//    //gather all the <xforms:item> elements to generate the UIPickerView datasource
//    NSMutableDictionary *widgetPackage = [[NSMutableDictionary alloc]init];
//    [widgetPackage setObject:select1 forKey:@"widgetObject"];
//    [widgetPackage setObject:pickerDatasourceArray forKey:@"widgetDatasource"];
//
//    return widgetPackage;
//}
//
////xforms:select = A series of UISegmentedControls (which are the closest thing to checkboxes)
///*
// widgetPackage is { "widgetType": "checkboxes",
// "widgetObject"= [{"UILabel" = UILabel, "UISegmentedControl" = UISegmentedControl, "Value":"fever"},
// {"UILabel" = UILabel, "UISegmentedControl" = UISegmentedControl, "Value"="headache"}
// ]
// }
// */
//-(NSMutableDictionary *)createCheckboxes:(GDataXMLElement *) selectElement {
//
//    if ([[[selectElement childAtIndex:0] name] isEqualToString:@"xforms:label"]) {
//        NSMutableDictionary *labelWidgetPackage = [self createLabel:[[selectElement elementsForName:@"xforms:label"] objectAtIndex:0]];
//        //add immediately b/c xforms:label goes on top of xforms:textarea in our UI.
//        [self.currentGroupWidgetsArray addObject:labelWidgetPackage];
//
//    }
//
//    NSArray *itemsArray = [selectElement elementsForName:@"xforms:item"];
//    NSMutableArray *checkBoxArray = [[NSMutableArray alloc]init];
//    for (GDataXMLElement *item in itemsArray) {
//        GDataXMLElement *valueElement = [[item elementsForName:@"xforms:value"] objectAtIndex:0];
//
//        NSMutableDictionary *segmentedControlDictionary = [[NSMutableDictionary alloc]init];
//        UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width, self.scrollView.frame.size.height)];
//        label.backgroundColor = [UIColor clearColor];
//        label.font = [UIFont boldSystemFontOfSize:16];
//        label.textAlignment = UITextAlignmentLeft;
//        label.text = [valueElement stringValue];
//
//        UISegmentedControl *segmentedControl = [[UISegmentedControl alloc]initWithItems:[NSArray arrayWithObjects:@"yes",@"no", nil]];
//        [segmentedControlDictionary setObject:label forKey:@"UILabel"];
//        [segmentedControlDictionary setObject:segmentedControl forKey:@"UISegmentedControl"];
//        [segmentedControlDictionary setObject:label.text forKey:@"Value"];
//        [checkBoxArray addObject:segmentedControlDictionary];
//    }
//
//    //gather all the <xforms:item> elements to generate the UILabel and UISegmentedControls
//    NSMutableDictionary *widgetPackage = [[NSMutableDictionary alloc]init];
//    [widgetPackage setObject:checkBoxArray forKey:@"widgetObject"];
//    [widgetPackage setObject:@"checkboxes" forKey:@"widgetType"];
//
//    return widgetPackage;
//}
//
///*
// widgetPackage is {"widgetType" = "multimediaGUI","widgetObject"= [UIButton, UIButton,UIButton]}
// */
//-(NSMutableDictionary *)createMultimediaCaptureGUI:(GDataXMLElement *) inputElement {
//
//    UIButton *addPictureButton = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width/3, self.scrollView.frame.size.height)];
//    addPictureButton.backgroundColor = [UIColor clearColor];
//    [addPictureButton setTitle:@" Add Picture" forState:UIControlStateNormal];
//    [addPictureButton setTitleColor:[UIColor darkTextColor] forState:UIControlStateNormal];
//    [addPictureButton setImage:[UIImage imageNamed:@"photo.png"] forState:UIControlStateNormal];
//
//    UIButton *addVideoButton = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width/3, self.scrollView.frame.size.height)];
//    addVideoButton.backgroundColor = [UIColor clearColor];
//    [addVideoButton setTitle:@" Add Video" forState:UIControlStateNormal];
//    [addVideoButton setTitleColor:[UIColor darkTextColor] forState:UIControlStateNormal];
//    [addVideoButton setImage:[UIImage imageNamed:@"movie.png"] forState:UIControlStateNormal];
//
//    UIButton *addAudioButton = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, self.scrollView.bounds.size.width/3, self.scrollView.frame.size.height)];
//    addAudioButton.backgroundColor = [UIColor clearColor];
//    [addAudioButton setTitle:@" Add Audio" forState:UIControlStateNormal];
//    [addAudioButton setTitleColor:[UIColor darkTextColor] forState:UIControlStateNormal];
//    [addAudioButton setImage:[UIImage imageNamed:@"mic.png"] forState:UIControlStateNormal];
//
//    NSMutableArray *mmButtonsArray = [[NSMutableArray alloc]init];
//    [mmButtonsArray addObject:addPictureButton];
//    [mmButtonsArray addObject:addVideoButton];
//    [mmButtonsArray addObject:addAudioButton];
//
//    NSMutableDictionary *widgetPackage = [[NSMutableDictionary alloc]init];
//    [widgetPackage setObject:@"multimediaGUI" forKey:@"widgetType"];
//    [widgetPackage setObject:mmButtonsArray forKey:@"widgetObject"];
//
//    return widgetPackage;
//}

@end
