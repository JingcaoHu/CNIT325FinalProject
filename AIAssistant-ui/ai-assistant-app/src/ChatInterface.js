import React, { useState, useEffect, useRef } from 'react';
import './ChatInterface.css';

function ChatInterface({ onLogout }) {
  const [input, setInput] = useState('');
  const [messages, setMessages] = useState([
    { text: 'Hello! I\'m your local AI assistant. How can I help you today?', sender: 'ai' }
  ]);
  const messagesEndRef = useRef(null);

  // Auto-scroll to bottom when messages change
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!input.trim()) return;

    // Add user message
    const newUserMessage = { text: input, sender: 'user' };
    setMessages(prev => [...prev, newUserMessage]);
    setInput('');

    // Simulate AI thinking
    setTimeout(() => {
      generateAIResponse(input);
    }, 800);
  };

  const generateAIResponse = (userInput) => {
    const responses = [
      `I understand you're asking about "${userInput}". This is a simulated response.`,
      `Interesting point about ${userInput}. What else would you like to know?`,
      `I'm a basic AI assistant running locally. You said: "${userInput}"`,
      `Thanks for your message about ${userInput}. How can I help further?`,
      `${userInput}? That's an interesting topic to discuss.`
    ];
    
    const aiResponse = responses[Math.floor(Math.random() * responses.length)];
    setMessages(prev => [...prev, { text: aiResponse, sender: 'ai' }]);
  };

  return (
    <div className="chat-container">
      <header className="chat-header">
        <h2>AI Assistant</h2>
        <button onClick={onLogout} className="logout-button">Logout</button>
      </header>
      
      <div className="messages-container">
        {messages.map((message, index) => (
          <div key={index} className={`message ${message.sender}`}>
            {message.text}
          </div>
        ))}
        <div ref={messagesEndRef} />
      </div>
      
      <form onSubmit={handleSubmit} className="input-area">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Type your message..."
          autoFocus
        />
        <button type="submit">Send</button>
      </form>
    </div>
  );
}

export default ChatInterface;