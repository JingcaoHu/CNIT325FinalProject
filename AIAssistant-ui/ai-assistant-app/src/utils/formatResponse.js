export function formatResponse(response, promptType) {
    if (promptType === 1) {
      // For code solutions, just return as-is
      return response;
    }
    
    // For other types, clean up the response
    let formatted = response;
    
    // Remove any remaining JSON artifacts
    if (formatted.includes('</think>')) {
      formatted = formatted.split('</think>')[1] || formatted;
    }
    
    if (formatted.includes('}')) {
      formatted = formatted.split('}')[0] || formatted;
    }
    
    // Remove any trailing characters
    formatted = formatted.trim();
    if (formatted.endsWith('"')) {
      formatted = formatted.slice(0, -1);
    }
    
    return formatted;
  }