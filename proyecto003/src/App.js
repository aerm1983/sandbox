import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

  constructor(props) {
    super(props);

  }

  render() {
    return (
      <div>
        <form onSubmit={this.presion}>
          <input type="text" name="valor1" />
          <input type="text" name="valor2" />
          <input type="submit" value="Calcular" />
        </form>
      </div>
    );
  }
  
  presion(e) {
    e.preventDefault();
    const v1 = parseInt(e.target.valor1.value, 10);
    const v2 = parseInt(e.target.valor2.value, 10);
    const suma = v1 + v2;
    console.log('La suma es: ' + suma );
    alert('La suma es: ' + suma );
  };

}

export default App;
