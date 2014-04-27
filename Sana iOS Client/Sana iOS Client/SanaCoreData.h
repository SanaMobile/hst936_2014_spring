//
//  SanaCoreData.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SanaCoreData : NSObject

/**
 * The shared / common managed object context.
 */
@property(nonatomic, readonly) NSManagedObjectContext* managedObjectContext;

@property(nonatomic, readonly) NSPersistentStoreCoordinator* persistentStoreCoordinator;

+ (SanaCoreData *) sharedCoreData;

/**
 * Creates a new CoreData entity with the specified name
 */
-(id) createObjectNamed:(NSString*)entityName;

/**
 * Returns the entries in Entity with name entityName matching predicate In the given context
 */
-(NSArray *)getFetchResultsForEntityName:(NSString *)entityName usingPredicate:(NSPredicate *)predicate inContext:(NSManagedObjectContext *)context error:(NSError **)error;

/**
 * Returns the entries sorted according to given sort descriptors in Entity with name entityName matching predicate In the given context
 */
-(NSArray *)getFetchResultsForEntityName:(NSString *)entityName usingPredicate:(NSPredicate *)predicate withSortDescriptors:(NSArray *)sortDescriptors inContext:(NSManagedObjectContext *)context error:(NSError **)error;

#pragma mark -
#pragma mark Save Changes in ManagedObjectContext
#pragma mark -

- (BOOL) save;

#pragma mark -

@end
