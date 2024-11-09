import { FlashList } from '@shopify/flash-list';
import { useCallback, useRef } from 'react';
import {
  Dimensions,
  StyleSheet,
  View,
  type ViewabilityConfig,
} from 'react-native';
import { FlashImage } from 'react-native-flash-image';
import Dataset from './dummy/data.json';
const { width } = Dimensions.get('window');
const estimatedListSize = {
  width: width / 2,
  height: width / 2,
};
export default function App() {
  const imageUrs = Dataset;
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
      <FlashImage
        style={styles.box}
        autoPlayGif={false}
        cachePolicy="discWithCacheControl"
        onSuccess={(event) => {
          console.log(event.nativeEvent);
        }}
        source={{
          uri: 'https://images.unsplash.com/5/unsplash-kitsune-4.jpg',
        }}
      />
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

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    height: width / 2,
    width: width / 2,
    borderRadius: 200,
  },
});
