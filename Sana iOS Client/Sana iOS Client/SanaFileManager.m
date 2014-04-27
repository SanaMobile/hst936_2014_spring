//
//  SanaFileManager.m
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/27/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#import "SanaFileManager.h"
#import "Procedure.h"
#import "SanaCoreData.h"

#define kPrivateFolderPath @"procedures"

@implementation SanaFileManager

+ (NSString *)getUniqueUUID {
    return [[NSUUID UUID] UUIDString];
}

+ (NSString *)getFullPathForFileName:(NSString *)fileName{
    NSString *fullPath=[[self getLibraryDirectoryPath] stringByAppendingPathComponent:fileName];
    return fullPath;
}

+ (BOOL)createDirectoryAtPath:(NSString *)path error:(NSError **)error{
    return [[NSFileManager defaultManager] createDirectoryAtPath:path withIntermediateDirectories:YES attributes:nil error:error];
}

+ (NSString *)getLibraryDirectoryPath{
    NSArray *paths=NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask, YES);

    NSString *libraryPath=[paths objectAtIndex:0];
    libraryPath=[libraryPath stringByAppendingPathComponent:kPrivateFolderPath];

    BOOL isDirectory;
    NSError *error=nil;
    if([[NSFileManager defaultManager] fileExistsAtPath:libraryPath isDirectory:&isDirectory]){
        if(!isDirectory){
            [self createDirectoryAtPath:libraryPath error:&error];
        }
    }else{
        [self createDirectoryAtPath:libraryPath error:&error];
    }

    if(error) {
        //File not created
    }

    return libraryPath;
}

+ (Procedure *)saveProcedure:(NSData *)data forType:(NSString *)extension {
    if(data == nil)
        return nil;

    BOOL saved = NO;

    NSString *filePath = [self getFullPathForFileName:[[self getUniqueUUID] stringByAppendingPathExtension:extension]];
    if([[NSFileManager defaultManager] createFileAtPath:filePath contents:data attributes:nil]){
        saved = YES;
    }

    if(saved) {
        Procedure *proc = [[SanaCoreData sharedCoreData] createObjectNamed:@"Procedure"];
        if(proc != nil) {
            [proc setOriginalFile:filePath];
            [proc setCreatedAt:[NSDate date]];
            [[SanaCoreData sharedCoreData] save];

            return proc;
        }
    }

    return nil;
}

@end
