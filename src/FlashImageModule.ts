import { NativeModules } from 'react-native';
const FlashImageModule = NativeModules.FlashImageModule;

type PrefetchImageOptions = {
  cachePolicy?: 'discWithCacheControl';
  headers?: Record<string, string>;
};
export const prefetchImage = async (
  url: string,
  { cachePolicy = 'discWithCacheControl', headers = {} }: PrefetchImageOptions
): Promise<boolean> => {
  return FlashImageModule.prefetchImage(url, cachePolicy, headers);
};
