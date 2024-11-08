import { FlashList } from '@shopify/flash-list';
import { useCallback, useRef } from 'react';
import {
  Dimensions,
  StyleSheet,
  View,
  processColor,
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
  console.log(processColor('red'));
  const viewabilityConfig = useRef<ViewabilityConfig>({
    waitForInteraction: true,
    itemVisiblePercentThreshold: 50,
    minimumViewTime: 600,
    viewAreaCoveragePercentThreshold: 50,
  }).current;
  const renderItem = useCallback(({ item }) => {
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

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    height: width / 2,
    width: width / 2,
    borderRadius: 20,
  },
});
