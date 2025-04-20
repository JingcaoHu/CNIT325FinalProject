import React from 'react';
import ChatInterface from './components/ChatInterface';
import './styles/App.css';

function App() {
  return (
    <div className="app">
      <header className="app-header">
        <h1>LM Studio Assistant</h1>
      </header>
      <main>
        <ChatInterface />
      </main>
    </div>
  );
}

export default App;