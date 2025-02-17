import React from "react";
import EventsList from "./components/EventList";
import styles from './style/EventsListStyle.module.css'; 

const App = () => {
  return (
    <div>
      <h1>Transactions</h1>
      <div className={styles.eventsContainer}>
        <EventsList 
          url="ws://localhost:8081/ws/transactions-high-value" 
          headerText="List High-Value Transactions" 
        />
        <EventsList 
          url="ws://localhost:8081/ws/transactions-all" 
          headerText="List All Transactions" 
        />
      </div>
    </div>
  );
};

export default App;
