import React, { useState } from 'react';
import Layout from './components/Layout';
import NumbersModule from './modules/NumbersModule';
import AdditionModule from './modules/AdditionModule';
import SubtractionModule from './modules/SubtractionModule';

function App() {
  const [currentModule, setCurrentModule] = useState('numbers');

  const renderModule = () => {
    switch (currentModule) {
      case 'numbers':
        return <NumbersModule />;
      case 'addition':
        return <AdditionModule />;
      case 'subtraction':
        return <SubtractionModule />;
      default:
        return <NumbersModule />;
    }
  };

  return (
    <Layout currentModule={currentModule} onModuleChange={setCurrentModule}>
      {renderModule()}
    </Layout>
  );
}

export default App;
