import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Lista from './Lista';
import Formulario from './Formulario';

class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      operaciones : []
    }
    this.sumar = this.sumar.bind(this);
  }

  render() {
    return (
      <div className="App">
        <Formulario onSubmit={ (event) => this.sumar(event) } />
        <Lista operaciones={this.state.operaciones} />
      </div>
    );
  }

  sumar(event) {
    event.preventDefault();
    const valor1 = parseInt(event.target.valor1.value);
    const valor2 = parseInt(event.target.valor2.value);
    const resultado = valor1 + valor2;
    
    const tempOperaciones = this.state.operaciones;

    tempOperaciones.unshift({ 
      valor1: valor1,
      valor2: valor2,
      resultado: resultado
    });

    this.setState( { operaciones: tempOperaciones } );


  }
}

export default App;
