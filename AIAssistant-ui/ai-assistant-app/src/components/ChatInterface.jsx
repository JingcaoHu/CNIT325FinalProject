import React, { useState, useEffect } from 'react';
import PromptTypeSelector from './PromptTypeSelector';
import ResponseDisplay from './ResponseDisplay';
import HistoryPanel from './HistoryPanel';
import { sendPrompt } from '../services/apiService';
import '../styles/App.css';

function ChatInterface() {
  const [promptType, setPromptType] = useState(3); // Default to Q&A
  const [message, setMessage] = useState('');
  const [response, setResponse] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [history, setHistory] = useState([]);
  const [darkMode, setDarkMode] = useState(false);

  useEffect(() => {
    document.body.className = darkMode ? 'dark-mode' : 'light-mode';
  }, [darkMode]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!message.trim()) return;
    
    setIsLoading(true);
    try {
      const result = await sendPrompt({
        selection: promptType,
        content: message,
        UID: Date.now() % 1000 // Simple temporary UID
      });
      
      const newEntry = {
        id: Date.now(),
        type: promptType,
        question: message,
        answer: result,
        timestamp: new Date().toLocaleString()
      };
      
      setResponse(result);
      setHistory(prev => [newEntry, ...prev]);
      setMessage('');
    } catch (error) {
      console.error('Error:', error);
      setResponse('Error occurred while processing your request');
    } finally {
      setIsLoading(false);
    }
  };

  const handleExampleClick = (example) => {
    setMessage(example);
  };

  return (
    <div className={`chat-container ${darkMode ? 'dark' : ''}`}>
      <div className="theme-toggle">
        <button onClick={() => setDarkMode(!darkMode)}>
          {darkMode ? '☀️ Light Mode' : '🌙 Dark Mode'}
        </button>
      </div>
      
      <div className="chat-layout">
        <div className="history-panel">
          <HistoryPanel 
            history={history} 
            onSelect={(item) => {
              setPromptType(item.type);
              setMessage(item.question);
              setResponse(item.answer);
            }} 
          />
        </div>
        
        <div className="main-chat">
          <PromptTypeSelector value={promptType} onChange={setPromptType} />
          
          <div className="examples">
            <h3>Try these examples:</h3>
            <div className="example-buttons">
              <button onClick={() => handleExampleClick("What is the capital of France?")}>
                General Question
              </button>
              <button onClick={() => handleExampleClick("for (let i = 0; i < 10; i++) { console.log(i); }")}>
                Code Suggestion
              </button>
              <button onClick={() => handleExampleClick("Write a Python function to reverse a string")}>
                Code Solution
              </button>
            </div>
          </div>
          
          <form onSubmit={handleSubmit} className="message-form">
            <textarea
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder={
                promptType === 3 
                  ? "Type your question here..." 
                  : promptType === 0 
                    ? "Paste your code for suggestions..." 
                    : "Describe the coding problem you need solved..."
              }
              rows={5}
            />
            <button type="submit" disabled={isLoading || !message.trim()}>
              {isLoading ? 'Sending...' : 'Send'}
            </button>
          </form>
          
          <ResponseDisplay response={response} promptType={promptType} />
        </div>
      </div>
    </div>
  );
}

export default ChatInterface;