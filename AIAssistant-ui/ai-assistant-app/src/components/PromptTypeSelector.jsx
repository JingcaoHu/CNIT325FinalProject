import React from 'react';

function PromptTypeSelector({ value, onChange }) {
  const options = [
    { value: 0, label: 'Code Suggestions' },
    { value: 1, label: 'Code Solution' },
    { value: 3, label: 'General Q&A' }
  ];

  return (
    <div className="prompt-type-selector">
      <label>Select prompt type:</label>
      <div className="radio-group">
        {options.map(option => (
          <label key={option.value} className="radio-option">
            <input
              type="radio"
              value={option.value}
              checked={value === option.value}
              onChange={() => onChange(option.value)}
            />
            {option.label}
          </label>
        ))}
      </div>
    </div>
  );
}

export default PromptTypeSelector;