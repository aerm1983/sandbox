import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  render() {
    const siglo = 21;
    const persona = {nombre:'Juan', edad:34};
    const buscadores=['Google','Bing'];
    return (
      <div>
        <h1>Titulo nivel 1</h1>
        <hr />
        <p>Estamos en el siglo {siglo}</p>
        <h3>Acceso a un objeto</h3>
        <p>Persona {persona.nombre} tiene {persona.edad} agnos</p>
        <h3>Llamada a metodo</h3>
        <p>{this.retornarAleatorio()}</p>
        <h3>Calculo inmediato de expresiones</h3>
        <p>3 + 3 = {3+3}</p>
        <h3>Buscadores</h3>
        <p>{buscadores[0]}, {buscadores[1]}</p>
        <h3>Insercion de html desde metodo</h3>
        {this.mostrarTitulo('insertado')}
      </div>
    );
  };

  retornarAleatorio () {
    return Math.trunc(Math.random() * 10);
  };

  mostrarTitulo(titulo) {
    return(
      <h5>{titulo}</h5>
    )
  }

}

export default App;
