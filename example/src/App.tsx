import {
  Dimensions,
  FlatList,
  StyleSheet,
  View,
  processColor,
} from 'react-native';
import { FlashImage } from 'react-native-flash-image';
import type { Int32 } from 'react-native/Libraries/Types/CodegenTypes';

const { width } = Dimensions.get('window');
export default function App() {
  const imageUrs = [
    'https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExaHN4ZWNveWIwaW4xZjMzdm9rMXI4dzBzNzhrY2gwdnNtaGpwa3k2aSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9dg/2JncG7P8IXTGKm7FRt/giphy.gif',
    'https://images.unsplash.com/5/unsplash-kitsune-4.jpg',
    'https://www.cloudynights.com/uploads/profile/photo-34923.gif?_r=0',
    'https://nham24.com/assets/icons/new_logo.png',
    'https://www.cloudynights.com/uploads/profile/photo-24641.gif?_r=0',
    'https://imagery.go24.app/media/upload/Company/27603/024062715563527603.png?w=100',
    'https://imagery.go24.app/media/upload/Company/27603/024062715563527603.png?w=800',
  ];
  console.log(processColor('red'));
  return (
    <View style={styles.container}>
      {/* <FlatList
        data={imageUrs}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => {
          return (
            <Image
              style={styles.box}
              source={{
                uri: item,
              }}
            />
          );
        }}
      /> */}
      <FlatList
        data={imageUrs}
        numColumns={2}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => {
          return (
            <FlashImage
              style={styles.box}
              autoPlayGif={false}
              tint={processColor('blue') as Int32}
              cachePolicy="discWithCacheControl"
              source={{
                uri: item,
              }}
            />
          );
        }}
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
