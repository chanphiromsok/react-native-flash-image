import { Dimensions, StyleSheet } from 'react-native';

export const { height, width } = Dimensions.get('window');
export const estimatedListSize = {
  width: width / 2,
  height: width / 2,
};
export const styles = StyleSheet.create({
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
