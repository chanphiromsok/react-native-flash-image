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

export const clearCache = async (): Promise<boolean> => {
  return FlashImageModule.clearCache();
};

export const prefetch = async (
  sources: Array<string>,
  headers: Record<string, string> = {}
): Promise<boolean> => {
  return FlashImageModule.prefetch(sources, headers);
};
