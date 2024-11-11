import { enableFreeze, enableScreens } from 'react-native-screens';
import AppNavigation from '../AppNavigation';

enableFreeze(true);
enableScreens(true);
const App = () => {
  return <AppNavigation />;
};

export default App;
