//
//  SDSServices.m
//  Sana2.0
//
//  Created by Richard Lu on 7/30/12.
//  Copyright (c) 2012 Sana, MIT, CSAIL, etc. All rights reserved.
//

#import "SDSServices.h"
#import "SanaConstants.h"
#import "AFNetworking.h"

//a class extension, which just means that this is where private properties go
@interface SDSServices () {
}

@end

@implementation SDSServices

@synthesize sdsServicesDelegate;

-(id)initWithDelegate:(id<SDSServicesDelegate>) theDelegate {
    
    if ((self = [super init])) {
		self.sdsServicesDelegate = theDelegate;
        //hmmm...maybe use a global queue dispatch_get_global_queue()
        const char *uploaderQueue = [ENCOUNTER_UPLOADER_QUEUE UTF8String];
        encounterUploaderQueue = dispatch_queue_create(uploaderQueue, NULL);
	}
	return self;
}

//gets "fresh" available Procedure types from a repository
//loads these Procedures from XML files that are on the phone
//or better, from a network centralized repository (not yet implemented)
//TO DO: make this a block-based method to avoid blocking the main thread
-(void) getProcedureTemplates {

//TO DO: call the SDS to fetch the list of Procedures available
//    dispatch_async(encounterUploaderQueue, ^(void) {
//        
//        NSURL *url = [NSURL URLWithString:@"https://gowalla.com/users/mattt.json"];
//        NSURLRequest *request = [NSURLRequest requestWithURL:url];
//        
//        AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
//            
//            NSLog(@"Name: %@ %@", [JSON valueForKeyPath:@"first_name"], [JSON valueForKeyPath:@"last_name"]);
//            
//            dispatch_async(dispatch_get_main_queue(), ^(void){
//                NSLog(@"Uploaded successfully");
//            });
//        } 
//                                                                                            failure:nil];
//        
//        [operation start];
//    });
}

-(NSString *)dataFilePath:(BOOL)forSave {
//    return [[NSBundle mainBundle] pathForResource:@"test_procedure_sana_2_0" ofType:@"xml"];
    return [[NSBundle mainBundle] pathForResource:@"test_proc" ofType:@"xml"];
}

//TO DO: make this a block-based method in case it's slow
-(GDataXMLDocument *)loadProcedureXML {
    
    NSString *pathToProcedure = [self dataFilePath:FALSE];
    NSData *xmlNSData = [[NSMutableData alloc] initWithContentsOfFile:pathToProcedure];
    NSError *error;
    GDataXMLDocument *domDoc = [[GDataXMLDocument alloc] initWithData:xmlNSData 
                                                              options:0 error:&error];
    if (domDoc == nil) {
        NSLog(@"error:%@",error);
        return nil; 
    }
    
    //should be <html>
//    NSLog(@"%@", domDoc.rootElement.name);
//    NSLog(@"%@", domDoc.rootElement.children);
    
    return domDoc;

}

-(void) getSavedEncounter {
    
}

-(void) saveEncounter{
    
}

-(void) deleteEncounter:(BOOL) all {
    
}

//TO DO:connect this to Sana Dispatch Services
-(void) uploadEncounters {
    
    dispatch_async(encounterUploaderQueue, ^(void) {
        
        NSURL *url = [NSURL URLWithString:@"https://gowalla.com/users/mattt.json"];
        NSURLRequest *request = [NSURLRequest requestWithURL:url];
        
        AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
            
            NSLog(@"Name: %@ %@", [JSON valueForKeyPath:@"first_name"], [JSON valueForKeyPath:@"last_name"]);
                
                dispatch_async(dispatch_get_main_queue(), ^(void){
                    NSLog(@"Uploaded successfully");
                });
        } 
        failure:nil];
        
        [operation start];
    });
}

-(NSArray *)fetchDataFromDisk{
    
//    NSFetchRequest *request = [[[NSFetchRequest alloc] init] autorelease];
//    NSEntityDescription *entity = [NSEntityDescription entityForName:@"RolodexPatientEntity" inManagedObjectContext:self.managedObjectContext];
//    [request setEntity:entity];
//    //fetch the objects in last,first name order
//    
//    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"lastName" ascending:YES];
//    NSSortDescriptor *sortDescriptor2 = [[NSSortDescriptor alloc] initWithKey:@"firstName" ascending:YES];
//    NSSortDescriptor *sortDescriptor3 = [[NSSortDescriptor alloc] initWithKey:@"mrn" ascending:YES];
//    NSArray *sortDescriptors = [[NSArray alloc] initWithObjects:sortDescriptor, sortDescriptor2,sortDescriptor3, nil];
//    [request setSortDescriptors:sortDescriptors];
//    [sortDescriptor release];
//    [sortDescriptor2 release];
//    [sortDescriptor3 release];
//    
//    NSArray *result = [self.managedObjectContext executeFetchRequest:request error:NULL];
//    return result;

    return nil;
}

-(void)saveDataToDisk:(NSArray *)dataToPersist{
    
    //clear data 
//    NSArray *rolodexPatients = [self fetchDataFromDisk];
//    if ([rolodexPatients count]>0) {
//        for (NSManagedObject *pt in rolodexPatients) {
//            [self.managedObjectContext deleteObject:pt];
//        }
//    }
//    else {
//        NSLog(@"rolodex patients is <= 0:%d",[rolodexPatients count]);
//    }
//    
//    for (NSDictionary *pt in dataToPersist) {
//        RolodexPatientEntity *rolodexPatient = (RolodexPatientEntity *)[NSEntityDescription insertNewObjectForEntityForName:@"RolodexPatientEntity" inManagedObjectContext:mojoService.managedObjectContext];
//        
//        rolodexPatient.firstName = [pt objectForKey:@"first_name"];
//        rolodexPatient.lastName = [pt objectForKey:@"last_name"];
//        rolodexPatient.mrn = [pt objectForKey:@"mrn"];
//        rolodexPatient.inPanel = [[pt objectForKey:@"panel"] toNSNumber];
//    }
//    
//    if ([mojoService save]) {
//        NSLog(@"successfully saved rolodex pts");
//    }
//    else {
//        NSLog(@"did not save rolodex pts");
//    }
}

@end
