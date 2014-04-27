//
//  SanaCoreData.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaCoreData.h"

@interface SanaCoreData ()
@property(nonatomic, strong) NSManagedObjectModel* managedObjectModel;
@property(atomic, strong) NSManagedObjectContext* managedObjectContext;
@property(nonatomic, strong) NSPersistentStoreCoordinator* persistentStoreCoordinator;
@end

@implementation SanaCoreData
@synthesize managedObjectModel;
@synthesize managedObjectContext;
@synthesize persistentStoreCoordinator;

+ (SanaCoreData *) sharedCoreData {
    static SanaCoreData *sharedData=nil;

    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedData=[[SanaCoreData alloc] init];;
    });
    return sharedData;
}

- (id) init {
    self = [super init];
    if(self) {
        self.managedObjectContext = [[NSManagedObjectContext alloc] init];
        [self.managedObjectContext setMergePolicy:NSMergeByPropertyObjectTrumpMergePolicy];
        [managedObjectContext setPersistentStoreCoordinator:[self persistentStoreCoordinator]];
        [managedObjectContext setUndoManager:nil];
    }

    return self;
}

/**
 *  Creates a new NSManagedObjectModel or returns a previous object.
 */
-(NSManagedObjectModel*) managedObjectModel {
    NSURL* modelURL = [[NSBundle mainBundle] URLForResource:@"Sana_iOS_Client" withExtension:@"momd"];
    self.managedObjectModel = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    return managedObjectModel;
}

/**
 * Creates a new NSPersistentStoreCoordinator or returns a previous object.
 */
- (NSPersistentStoreCoordinator*) persistentStoreCoordinator {

    if(persistentStoreCoordinator!=nil){
        return persistentStoreCoordinator;
    }
    NSURL* applicationDocumentsDirectory = [[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject];
    NSURL *storeURL = [applicationDocumentsDirectory URLByAppendingPathComponent:@"Sana_iOS_Client.sqlite"];

    // Automatic lightweight migration
    NSDictionary* options = [NSDictionary dictionaryWithObjectsAndKeys:
                             [NSNumber numberWithBool:YES], NSMigratePersistentStoresAutomaticallyOption,
                             [NSNumber numberWithBool:YES], NSInferMappingModelAutomaticallyOption, nil];

    NSError *error = nil;
    self.persistentStoreCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:[self managedObjectModel]];
    if (![persistentStoreCoordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:storeURL options:options error:&error]) {
        NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
        abort();
    }

    return persistentStoreCoordinator;
}

/**
 * Returns the application directory.
 */
- (NSString*) applicationDocumentsDirectory {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *basePath = ([paths count] > 0) ? [paths objectAtIndex:0] : nil;
    return basePath;
}

/**
 *  Attempts to find an entity that matches the predicate.
 *
 *  @param entityName Entity name, cannot be nil.
 *  @param predicate  Predicate for the search, may be nil.
 */
- (id<NSObject>) findEntityNamed:(NSString*)entityName withPredicate:(NSPredicate*)predicate {
    NSEntityDescription* entityDescription = [NSEntityDescription entityForName:entityName inManagedObjectContext:[self managedObjectContext]];
    NSFetchRequest* fetchRequest = [[NSFetchRequest alloc] init];
    [fetchRequest setEntity:entityDescription];
    [fetchRequest setPredicate:predicate];
    [fetchRequest setFetchLimit:1];

    NSArray* fetchedEntities = [managedObjectContext executeFetchRequest:fetchRequest error:nil];
    if([fetchedEntities count] > 0) {
        return [fetchedEntities objectAtIndex:0];
    }

    return nil;
}
/**
 * Creates a new CoreData entity with the specified name
 */
-(id) createObjectNamed:(NSString*)entityName {
    return [NSEntityDescription insertNewObjectForEntityForName:entityName inManagedObjectContext:[self managedObjectContext]];
}

- (NSArray *)getFetchResultsForEntityName:(NSString *)entityName usingPredicate:(NSPredicate *)predicate inContext:(NSManagedObjectContext *)context error:(NSError *__autoreleasing *)error{
    NSFetchRequest *request=[[NSFetchRequest alloc] init];
    NSEntityDescription *entity=[NSEntityDescription entityForName:entityName inManagedObjectContext:context];
    [request setEntity:entity];

    if(predicate){
        [request setPredicate:predicate];
    }

    NSArray *results=[context executeFetchRequest:request error:error];
    return results;
}

- (NSArray *)getFetchResultsForEntityName:(NSString *)entityName usingPredicate:(NSPredicate *)predicate withSortDescriptors:(NSArray *)sortDescriptors inContext:(NSManagedObjectContext *)context error:(NSError *__autoreleasing *)error{
    NSFetchRequest *request=[[NSFetchRequest alloc] init];
    if(!context)
        context = [self managedObjectContext];
    NSEntityDescription *entity=[NSEntityDescription entityForName:entityName inManagedObjectContext:context];
    [request setEntity:entity];

    if(predicate){
        [request setPredicate:predicate];
    }

    if (sortDescriptors.count) {
        [request setSortDescriptors:sortDescriptors];
    }

    NSArray *results=[context executeFetchRequest:request error:error];
    return results;
}

- (BOOL) save {
    return [[self managedObjectContext] save:nil];
}

@end
