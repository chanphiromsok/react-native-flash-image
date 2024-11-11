import { FlashList } from '@shopify/flash-list';
import { useCallback, useRef } from 'react';
import {
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  type ViewabilityConfig,
} from 'react-native';
import { clearCache, FlashImage } from 'react-native-flash-image';
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
      <ActionTouchable
        onPress={() => {
          clearCache()
            .then((isClear) => {
              console.log('Clear All Cached Images', isClear);
            })
            .catch((err) => {
              console.warn('Failed to clear All Cached Images', err);
            });
        }}
        label="Clear Cache"
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

type ActionTouchableType = {
  onPress: () => void;
  label: string;
};
const ActionTouchable = ({ label, onPress }: ActionTouchableType) => {
  return (
    <TouchableOpacity onPress={onPress}>
      <Text style={actions.label}>{label}</Text>
    </TouchableOpacity>
  );
};

const actions = StyleSheet.create({
  label: {
    fontWeight: '600',
    fontSize: 16,
    color: 'black',
    padding: 20,
    backgroundColor: 'red',
  },
});
