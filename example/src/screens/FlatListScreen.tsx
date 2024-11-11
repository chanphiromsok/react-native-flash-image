import { useCallback, useRef } from 'react';
import {
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  type ViewabilityConfig,
} from 'react-native';
import { clearCache, FlashImage } from 'react-native-flash-image';
import Dataset from '../dummy/data.json';
import { styles } from '../metric';

const imageUrs = [...new Set(Dataset)];
export default function FlatListScreen() {
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
        recyclingKey={item}
        // onSuccess={(e) => {
        //   console.log(e.nativeEvent?.uri);
        // }}
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
      <FlatList
        data={imageUrs}
        viewabilityConfig={viewabilityConfig}
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
