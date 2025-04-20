import React from 'react';
import CodeBlock from './CodeBlock';
import { formatResponse } from '../utils/formatResponse';

function ResponseDisplay({ response, promptType }) {
  if (!response) return null;

  const formattedResponse = formatResponse(response, promptType);

  return (
    <div className="response-display">
      <h3>Response:</h3>
      {promptType !== 1 ? (
        <div className="response-content">
          {formattedResponse.split('\n').map((paragraph, i) => (
            <p key={i}>{paragraph}</p>
          ))}
        </div>
      ) : (
        <CodeBlock code={formattedResponse} />
      )}
    </div>
  );
}

export default ResponseDisplay;