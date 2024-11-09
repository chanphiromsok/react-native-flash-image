import type { ViewProps } from 'react-native';
import type {
  BubblingEventHandler,
  Int32,
  WithDefault,
} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type CachePolicy =
  | 'memory'
  | 'discWithCacheControl'
  | 'discNoCacheControl'
  | 'memoryAndDisc';

type ContentFit = 'contain' | 'cover' | 'stretch' | 'center';

type Headers = {
  Authorization: string;
};
type ImageSuccess = {
  width: Int32;
  height: Int32;
};

export interface NativeProps extends ViewProps {
  cachePolicy?: WithDefault<CachePolicy, 'discWithCacheControl'>;
  autoPlayGif?: WithDefault<boolean, false>;
  contentFit?: WithDefault<ContentFit, 'contain'>;
  tint?: WithDefault<Int32, undefined>;
  source: {
    uri: string;
  };
  allowHardware?: WithDefault<boolean, false>;
  headers?: Headers;
  onSuccess?: BubblingEventHandler<ImageSuccess> | null;
}

export default codegenNativeComponent<NativeProps>('FlashImageView');
