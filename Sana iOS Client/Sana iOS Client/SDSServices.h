//
//  SDSServices.h "Sana Dispatch Server Services"
//  Sana2.0
//
//  Created by Richard Lu on 7/30/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GDataXMLNode.h"

@protocol SDSServicesDelegate 
@optional
-(void)gotProcedureTemplates:(NSArray *) procedureTemplatesArray;
-(void)gotProcedureTemplatesError:(NSString *)errorString;

-(void)successfullyUploadedAllPendingEncounters;
-(void)unsuccessfullyUploadedAllPendingEncounters;

@end

//Should this be a singleton?
@interface SDSServices : NSObject {
    
    __weak id<SDSServicesDelegate> sdsServicesDelegate;
    dispatch_queue_t encounterUploaderQueue;
}

//this class has a delegate (FormTemplatesVC is one. You want this class to have a weak references
//to FormTemplatesVC in order to prevent retain cycles.  FormTemplates VC has a strong
//references to MDSServices; MDSServices should have a weak references to FormTemplatesVC
@property (nonatomic, weak) id<SDSServicesDelegate> sdsServicesDelegate;

-(id)initWithDelegate:(id<SDSServicesDelegate>) mdsServicesDelegate;
-(GDataXMLDocument *)loadProcedureXML;
//download available form templates from repository
-(void) getProcedureTemplates;
//retrieve forms from core data. Forms can be saved in the middle
//or when completed. This method retrieves them all
//TO DO: extend this to retrieve forms from MDS
-(void) getSavedEncouters;
//save the form to core data in the not-yet-uploaded forms
-(void) saveEncounter;
//TO DO: this should start uploading the queue of forms to MDS
-(void) deleteEncounter:(BOOL) all;
-(void) uploadEncounters;


@end
