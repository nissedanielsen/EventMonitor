import { useState, useEffect } from "react";

const useWebSocket = (url) => {
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    const ws = new WebSocket(url); // Create the WebSocket connection

    // Handle incoming messages
    ws.onmessage = (event) => {
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    // Handle WebSocket connection errors
    ws.onerror = (error) => {
      console.error("WebSocket error:", error);
    };

    // Handle WebSocket connection close
    ws.onclose = () => {
      console.log("WebSocket connection closed. Attempting to reconnect...");
      setTimeout(() => {
        setSocket(null); // Recreate WebSocket connection
      }, 3000);
    };

    // Set the WebSocket object in the state
    setSocket(ws);

    // Cleanup WebSocket connection when the component is unmounted
    return () => {
      if (ws) {
        ws.close();
      }
    };
  }, [url]);

  return { messages, socket };
};

export default useWebSocket;
