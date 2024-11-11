import { createStaticNavigation } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import FlashImageScreen from './src/screens/FlashImageScreen';
import { default as FlatListScreen } from './src/screens/FlatListScreen';
import HomeScreen from './src/screens/HomeScreen';

const RootStack = createNativeStackNavigator({
  initialRouteName: 'FlashImageScreen',
  screens: {
    Home: HomeScreen,
    FlashImageScreen: FlashImageScreen,
    FlatListScreen,
  },
  screenOptions: {
    headerShown: true,
  },
});

const AppNavigation = createStaticNavigation(RootStack);

export default AppNavigation;
