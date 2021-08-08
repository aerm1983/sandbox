import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      articulos : [] ,
      ready : false
    }
  }


  render() {
    if ( !this.state.ready ) {
      return ( <div>recuperando datos ...</div> );
    } else {
      return (
      <div className="App">
        <table>

          <thead>
            <tr> <th>Codigo</th> <th>Descripcion</th> <th>Precio</th> </tr>
          </thead>

          <tbody>
            { this.state.articulos.map( (art) => { return( <tr> <td>{art.codigo}</td> <td>{art.descripcion}</td> <td>{art.precio}</td> </tr> ) } ) }
          </tbody> 

        </table>

      </div>
      );

    }
  }

  componentWillMount () {
    setTimeout( () => {
      const url = 'http://scratchya.com.ar/vue/datos.php';
      fetch(url)
        .then( (response) => { return response.json() } )
        .then( (response) => { 
          this.setState( { articulos : response, ready : true } );
      })
    }, 3000 );
  }

}

export default App;
