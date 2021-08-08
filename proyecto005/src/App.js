import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

  constructor(props){
    super(props);
    this.state = { articulos: [
      {item: "naranja", precio: 11.2, codigo: 1},
      {item: "banana", precio: 8.5, codigo: 2},
      {item: "apple", precio: 5.3, codigo: 3}
    ]}
    this.borrarUltimo = this.borrarUltimo.bind(this);
    this.borrarElemento = this.borrarElemento.bind(this);
  }

  render() {
    return (
      <div className="App">

        <table>
          <tr>
            <th>item</th>
            <th>precio</th>
            <th>action</th>
          </tr>

          { 
            this.state.articulos.map( (o) => { 
              return ( 
                <tr> 
                  <td>{o.item}</td> 
                  <td>{o.precio}</td> 
                  <td> <input type="button" onClick={ () => this.borrarElemento(o.codigo) } value="borrarElem" /> </td> 
                </tr>
              ) 
            } 
            ) 
          }

        </table>

        <p><input type="button" value="borrarU" onClick={this.borrarUltimo} /></p>

      </div>
    );
  }

  borrarUltimo () {
    if ( this.state.articulos.length > 0 ) {
      var tempArticulos = this.state.articulos;      
      tempArticulos.pop();
      this.setState( { articulos: tempArticulos } );
    }
  };

  borrarElemento(codigo) {
    var tempArticulos = this.state.articulos.filter( (elem) => { return elem.codigo !== codigo } );
    this.setState({articulos : tempArticulos});
    
  };

}

export default App;
