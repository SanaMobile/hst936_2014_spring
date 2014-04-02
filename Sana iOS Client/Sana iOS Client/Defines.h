//
//  Defines.h
//  Sana iOS Client
//
//  Created by Prince Shekhar on 4/2/14.
//  Copyright (c) 2014 MIT. All rights reserved.
//

#define SCREEN_WIDTH self.view.bounds.size.width
#define SCREEN_WIDTH_2 (self.view.bounds.size.width/2)
#define SCREEN_HEIGHT self.view.bounds.size.height
#define SCREEN_HEIGHT_2 (self.view.bounds.size.height/2)
#define SCREEN_HEIGHT_NC (self.view.bounds.size.height - NC_HEIGHT - STATUS_BAR_HEIGHT)
#define NC_HEIGHT 44
#define STATUS_BAR_HEIGHT 15

#define IOS_VERSION [[[[UIDevice currentDevice].systemVersion componentsSeparatedByString:@"."] objectAtIndex:0] intValue]
