import { useEffect, useState } from "react";
import { listMessages, type MessageDto } from "./api/generated";

export function App() {
  const [messages, setMessages] = useState<MessageDto[]>([]);
  const [error, setError] = useState<string>();

  useEffect(() => {
    listMessages().then((result) => {
      if (!result.data) {
        setError("Could not load messages");
        return;
      }
      setMessages(result.data);
    });
  }, []);

  return (
    <main>
      <h1>Messages</h1>
      {error && <p>{error}</p>}
      <ul>
        {messages.map((message) => (
          <li key={message.id}>{message.text}</li>
        ))}
      </ul>
    </main>
  );
}
