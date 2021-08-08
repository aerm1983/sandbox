import React, { Component } from 'react';
// import logo from './logo.svg';
// import './App.css';
// import Lista from './Lista';

class Formulario extends Component {
  /*
  constructor(props){
    super(props);
    console.log(props);
    this.state = {
      operaciones : []
    }
    this.sumar = this.sumar.bind(this);
  }
  */
  
  render() {
    return (
      <div className="Formulario">
        <form onSubmit={ this.props.onSubmit } >
          <input type="text" name="valor1" />
          <input type="text" name="valor2" />
          <input type="submit" />
        </form>
      </div>
    );
  }

}

export default Formulario;
