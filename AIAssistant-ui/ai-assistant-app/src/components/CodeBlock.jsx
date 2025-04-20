import React, { useEffect } from 'react';
import hljs from 'highlight.js';
import 'highlight.js/styles/github.css'; // or any other style

function CodeBlock({ code }) {
  useEffect(() => {
    document.querySelectorAll('pre code').forEach(block => {
      hljs.highlightBlock(block);
    });
  }, [code]);

  return (
    <pre className="code-block">
      <code>{code}</code>
    </pre>
  );
}

export default CodeBlock;