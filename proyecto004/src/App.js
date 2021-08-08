import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

  constructor (props) {
    super(props);
    this.numeroAleatorio = this.numeroAleatorio.bind(this);
    this.state = { vector: [0, 0, 0, 0, 0] };
  }

  render() {
    return (
      <div>
        <p>Numero Aleatorio: </p>
        {this.state.vector.map( (num) => { return( <p>{num}</p> ) } )}
        <input type="button" value="generar" onClick={this.numeroAleatorio} />
      </div>
    );
  };

  numeroAleatorio() {
    const nVector = [];
    for (let x = 0 ; x<this.state.vector.length ; x++) {
      nVector[x] = Math.trunc(Math.random() * 10);
    }

    this.setState( { vector: nVector } );
  }
}

export default App;
