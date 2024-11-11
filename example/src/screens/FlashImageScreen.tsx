import { FlashList } from '@shopify/flash-list';
import { useCallback, useRef } from 'react';
import { View, type ViewabilityConfig } from 'react-native';
import { FlashImage } from 'react-native-flash-image';
import Dataset from '../dummy/data.json';
import { estimatedListSize, styles, width } from '../metric';

const imageUrs = Dataset;
export default function FlashImageScreen() {
  const viewabilityConfig = useRef<ViewabilityConfig>({
    waitForInteraction: true,
    itemVisiblePercentThreshold: 50,
    minimumViewTime: 600,
    viewAreaCoveragePercentThreshold: 50,
  }).current;

  const renderItem = useCallback(({ item }: { item: string }) => {
    return (
      <FlashImage
        style={styles.box}
        autoPlayGif={false}
        cachePolicy="discWithCacheControl"
        source={{
          uri: item,
        }}
      />
    );
  }, []);
  return (
    <View style={styles.container}>
      <FlashList
        data={imageUrs}
        scrollEventThrottle={16}
        viewabilityConfig={viewabilityConfig}
        estimatedItemSize={width / 2}
        estimatedListSize={estimatedListSize}
        showsVerticalScrollIndicator={false}
        renderItem={renderItem}
      />
    </View>
  );
}
