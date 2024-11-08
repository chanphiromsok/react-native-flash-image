#ifdef RCT_NEW_ARCH_ENABLED
#import "FlashImageView.h"

#import <react/renderer/components/RNFlashImageViewSpec/ComponentDescriptors.h>
#import <react/renderer/components/RNFlashImageViewSpec/EventEmitters.h>
#import <react/renderer/components/RNFlashImageViewSpec/Props.h>
#import <react/renderer/components/RNFlashImageViewSpec/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"
#import "Utils.h"

using namespace facebook::react;

@interface FlashImageView () <RCTFlashImageViewViewProtocol>

@end

@implementation FlashImageView {
    UIView * _view;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
    return concreteComponentDescriptorProvider<FlashImageViewComponentDescriptor>();
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    static const auto defaultProps = std::make_shared<const FlashImageViewProps>();
    _props = defaultProps;

    _view = [[UIView alloc] init];

    self.contentView = _view;
  }

  return self;
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<FlashImageViewProps const>(_props);
    const auto &newViewProps = *std::static_pointer_cast<FlashImageViewProps const>(props);

    if (oldViewProps.color != newViewProps.color) {
        NSString * colorToConvert = [[NSString alloc] initWithUTF8String: newViewProps.color.c_str()];
        [_view setBackgroundColor: [Utils hexStringToColor:colorToConvert]];
    }

    [super updateProps:props oldProps:oldProps];
}

Class<RCTComponentViewProtocol> FlashImageViewCls(void)
{
    return FlashImageView.class;
}

@end
#endif
