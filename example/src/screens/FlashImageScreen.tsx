import { useNavigation } from '@react-navigation/native';
import { FlashList } from '@shopify/flash-list';
import { useCallback } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { clearCache, FlashImage } from 'react-native-flash-image';
import Dataset from '../dummy/data.json';
import { estimatedListSize, styles, width } from '../metric';

const imageUrs = [...new Set(Dataset)];
export default function FlashImageScreen() {
  const { navigate } = useNavigation();
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
      <ActionTouchable
        onPress={() => {
          navigate('FlatListScreen');
        }}
        label="FlatList"
      />
      <FlashList
        data={imageUrs}
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
