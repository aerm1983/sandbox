import React, { Component } from 'react';
import './App.css';
import Dado from './Dado';

class App extends Component {
    
  constructor(props) {
    super(props);
    this.state = {
      valor1: this.generarValor(),
      valor2: this.generarValor(),
      valor3: this.generarValor()
    };
    this.lanzar = this.lanzar.bind(this);
  }

  render() {

    const valor2 = Math.trunc(Math.random() * 6) + 1;
    const valor3 = Math.trunc(Math.random() * 6) + 1;
    return (
      <div className="App">
        <Dado valor={this.state.valor1} />
        <Dado valor={this.state.valor2} />
        <Dado valor={this.state.valor3} />
        <p><input type="button" onClick={ () => this.lanzar() } value="lanzar" /></p>
      </div>
    );
  }

  generarValor(){
    const valor = Math.trunc(Math.random() * 6) + 1;
    return valor;
  }

  lanzar() {
    this.setState({
      valor1: this.generarValor(),
      valor2: this.generarValor(),
      valor3: this.generarValor()
    })
  }
}

export default App;
