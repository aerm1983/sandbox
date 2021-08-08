import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import Reloj from './Reloj.js';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Reloj />
      </div>
    );
  }
}

export default App;
