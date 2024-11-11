import { createStaticNavigation } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import FlashImageScreen from './src/screens/FlashImageScreen';
import HomeScreen from './src/screens/HomeScreen';

const RootStack = createNativeStackNavigator({
  initialRouteName: 'FlashImageScreen',
  screens: {
    Home: HomeScreen,
    FlashImageScreen: FlashImageScreen,
  },
  screenOptions: {
    headerShown: false,
  },
});

const AppNavigation = createStaticNavigation(RootStack);

export default AppNavigation;
