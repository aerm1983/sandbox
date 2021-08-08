import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      nombre: "alberto",
      edad: 10,
      estudios: false
    };
    this.nombreCambio = this.nombreCambio.bind(this);
    this.edadCambio = this.edadCambio.bind(this);
    this.estudiosCambio = this.estudiosCambio.bind(this);
  }

  render() {
    return (
      <div className="App">
        <div>
          <form>
            <input type="text" name="Nombre" value={this.state.nombre} onChange={ (event) => { this.nombreCambio(event) } } />
            <input type="number" name="Edad" value={this.state.edad} onChange={ (event) => { this.edadCambio(event) } } />
            <input type="checkbox" name="Estudios"  checked={this.state.estudios} onChange={ (event) => { this.estudiosCambio(event) } } id="c" />
          </form>
        </div>

        <hr />

        <div>
          <p>Nombre: {this.state.nombre}</p>
          <p>Edad: {this.state.edad}</p>
          <p>Estudios: {this.state.estudios.toString()}</p>
        </div>

      </div>
    )
  }

  nombreCambio(event) {
    this.setState( { nombre : event.target.value } );
  }

  edadCambio(event) {
    this.setState( { edad : event.target.value } );
  }

  estudiosCambio(event) {
    this.setState( { estudios : event.target.checked } );
  }

}

export default App;
