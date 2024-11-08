import { StyleSheet, View } from 'react-native';
import { FlashImage } from 'react-native-flash-image';

export default function App() {
  return (
    <View style={styles.container}>
      <FlashImage
        style={styles.box}
        source={{
          cachePolicy: 'discWithCacheControl',
          autoPlayGif: true,
          url: 'https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExaHN4ZWNveWIwaW4xZjMzdm9rMXI4dzBzNzhrY2gwdnNtaGpwa3k2aSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9dg/2JncG7P8IXTGKm7FRt/giphy.gif',
        }}
      />
      <FlashImage
        style={styles.box}
        source={{
          cachePolicy: 'discWithCacheControl',
          autoPlayGif: true,
          url: 'https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExaHN4ZWNveWIwaW4xZjMzdm9rMXI4dzBzNzhrY2gwdnNtaGpwa3k2aSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9dg/2JncG7P8IXTGKm7FRt/giphy.gif',
        }}
      />
      <FlashImage
        style={styles.box}
        source={{
          cachePolicy: 'discWithCacheControl',
          autoPlayGif: true,
          url: 'https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExaHN4ZWNveWIwaW4xZjMzdm9rMXI4dzBzNzhrY2gwdnNtaGpwa3k2aSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9dg/2JncG7P8IXTGKm7FRt/giphy.gif',
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
    width: 200,
    height: 200,
    marginVertical: 20,
  },
});
