export async function sendPrompt(promptData) {
    try {
      const response = await fetch('http://localhost:8189', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          UID: promptData.UID,
          selection: promptData.selection,
          content: promptData.content,
          response: null,
          timeStamp: null
        })
      });
  
      if (!response.ok) {
        throw new Error(`Server responded with status ${response.status}`);
      }
  
      return await response.text();
    } catch (error) {
      console.error('Error sending prompt:', error);
      throw error;
    }
  }