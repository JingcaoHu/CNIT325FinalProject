import React from 'react';

function HistoryPanel({ history, onSelect }) {
  return (
    <div className="history-panel">
      <h3>History</h3>
      {history.length === 0 ? (
        <p>No history yet</p>
      ) : (
        <ul>
          {history.map(item => (
            <li key={item.id} onClick={() => onSelect(item)}>
              <div className="history-item">
                <small>{item.timestamp}</small>
                <p className="history-question">
                  {item.question.length > 50 
                    ? `${item.question.substring(0, 50)}...` 
                    : item.question}
                </p>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default HistoryPanel;