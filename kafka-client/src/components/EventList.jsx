import { useEffect } from "react";
import useWebSocket from "../hooks/useWebSocket";
import styles from "../style/EventsListStyle.module.css"; 

// Accept URL and header text as props
const EventsList = ({ url, headerText }) => {
  const { messages, socket } = useWebSocket(url);

  useEffect(() => {
    // Log WebSocket connection status
    if (socket) {
      console.log("WebSocket connection established.");
    }
  }, [socket]);

  return (
    <div className={styles.eventsList}>
      <h2 className={styles.eventsHeader}>{headerText}</h2> 
      <ul className={styles.eventsMessages}>
        {messages.length === 0 ? (
          <li className={styles.noMessages}>No messages yet..</li>
        ) : (
          messages
            .filter((msg) => !msg.startsWith("Connected: ")) // Filter out connection message
            .map((msg, index) => {
              try {
                const message = JSON.parse(msg); // Parse JSON
                return (
                  <li key={index} className={styles.eventMessage}>
                    <div><strong>Transaction ID:</strong> {message.transactionId}</div>
                    <div><strong>Sender ID:</strong> {message.senderId}</div>
                    <div><strong>Receiver ID:</strong> {message.receiverId}</div>
                    <div><strong>Amount:</strong> {message.amount.toFixed(2)}</div>
                  </li>
                );
              } catch (error) {
                console.error("Error parsing message:", error);
                return null; // In case of invalid JSON
              }
            })
        )}
      </ul>
    </div>
  );
};

export default EventsList;
