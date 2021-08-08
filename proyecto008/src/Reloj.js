import React, { Component } from 'react';
// import logo from './logo.svg';
// import './App.css';

class Reloj extends Component {
  constructor(props) {
    super(props);
    this.state = {
      date : new Date()
    }
  }

  render() {
    return (
      <div className="Reloj">
        <p>{ this.state.date.toLocaleTimeString() }</p>
      </div>
    );
  }


  componentDidMount () {
    this.intervalId = setInterval( () => { this.nuevaHora(); }, 1000 );
  }

  componentWillUnmount() {
    clearInterval(this.intervalId);
  }

  nuevaHora () {
    this.setState({
      date : new Date()
    })
  }
}

export default Reloj;
