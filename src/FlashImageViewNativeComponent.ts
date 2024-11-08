import type { ViewProps } from 'react-native';
import type { WithDefault } from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type CachePolicy =
  | 'memory'
  | 'discWithCacheControl'
  | 'discNoCacheControl'
  | 'memoryAndDisc';
export interface NativeProps extends ViewProps {
  source: {
    url: string;
    cachePolicy?: WithDefault<CachePolicy, 'discWithCacheControl'>;
    autoPlayGif?: WithDefault<boolean, false>;
  };
}

export default codegenNativeComponent<NativeProps>('FlashImageView');
