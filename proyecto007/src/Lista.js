import React, { Component } from 'react';
// import './Lista.css';

class Lista extends Component {
  render() {
    return (
      <div className="Lista">
        <ul>
          { this.props.operaciones.map( (op) => ( <li> {op.valor1} ; {op.valor2} ; {op.resultado} </li> ) ) }
        </ul>
      </div>
    );
  }
}

export default Lista;
