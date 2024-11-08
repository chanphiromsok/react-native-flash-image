// This guard prevent this file to be compiled in the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#import <UIKit/UIKit.h>

#ifndef FlashImageViewNativeComponent_h
#define FlashImageViewNativeComponent_h

NS_ASSUME_NONNULL_BEGIN

@interface FlashImageView : RCTViewComponentView
@end

NS_ASSUME_NONNULL_END

#endif /* FlashImageViewNativeComponent_h */
#endif /* RCT_NEW_ARCH_ENABLED */
