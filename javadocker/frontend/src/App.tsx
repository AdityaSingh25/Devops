import React, { useEffect, useState } from 'react';
import './App.css';

interface ApiData {
  message: string;
  time: string;
}

function App() {
  const [data, setData] = useState<ApiData | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Use environment variable for backend URL or default to localhost
    const backendUrl = process.env.REACT_APP_BACKEND_URL || 'http://localhost:8080';
    
    fetch(`${backendUrl}/api/hello`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data: ApiData) => setData(data))
      .catch((err) => {
        console.error('Error fetching data:', err);
        setError('Failed to connect to Java Backend. Make sure it is running!');
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>React + Java Docker Demo</h1>
        {error ? (
          <div style={{ color: '#ff6b6b', backgroundColor: '#2d3436', padding: '10px', borderRadius: '5px' }}>
            {error}
          </div>
        ) : data ? (
          <div>
            <p style={{ fontSize: '1.5rem', color: '#55efc4' }}>{data.message}</p>
            <p>Backend Time: {data.time}</p>
          </div>
        ) : (
          <p>Loading data from Java Backend...</p>
        )}
      </header>
    </div>
  );
}

export default App;
