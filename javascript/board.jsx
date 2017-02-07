import React from 'react';
import ReactDOM from 'react-dom';

const blackTile = {
    background: "black",
    width: "50px",
    height: "50px",
    display: "inline-block"
}

const whiteTile = {
    background: "grey",
    width: "50px",
    height: "50px",
    display: "inline-block"
}

class Piece extends React.Component {
    render() {
        if (this.props.piece == null) {
            return <div></div>
        }
        switch(this.props.piece.type) {
            case "King":
              return <div style={{float: "left", color: this.props.piece.color}} >K</div>
            case "Queen":
              return <div style={{float: "left", color: this.props.piece.color}} >Q</div>
            case "Knight":
              return <div style={{float: "left", color: this.props.piece.color}} >Kn</div>
            case "Rook":
              return <div style={{float: "left", color: this.props.piece.color}} >R</div>
            case "Pawn":
              return <div style={{float: "left", color: this.props.piece.color}} >P</div>
            case "Bishop":
              return <div style={{float: "left", color: this.props.piece.color}} >B</div>

            default:
                return <div></div>
        }
    }
}

class Tile extends React.Component {
    render() {
        if (this.props.color == "black") {
            return <div style={blackTile}><Piece piece={this.props.piece}/></div>
        }
        else if (this.props.color == "white") {
            return <div style={whiteTile}><Piece piece={this.props.piece}/></div>
        }
    }
}

class Row extends React.Component {
    pieceForCol(col) {
        var cl = col;
        return this.props.tiles.filter(function(elem) { return elem.position.column == cl })[0].piece
    }
    render() {
        if (this.props.alt == true) {
            return (
                <div className="row" >
                    <Tile row={this.props.row} col="A" piece={this.pieceForCol("A")} color="black"/>
                    <Tile row={this.props.row} col="B" piece={this.pieceForCol("B")} color="white"/>
                    <Tile row={this.props.row} col="C" piece={this.pieceForCol("C")} color="black"/>
                    <Tile row={this.props.row} col="D" piece={this.pieceForCol("D")} color="white"/>
                    <Tile row={this.props.row} col="E" piece={this.pieceForCol("E")} color="black"/>
                    <Tile row={this.props.row} col="F" piece={this.pieceForCol("F")} color="white"/>
                    <Tile row={this.props.row} col="G" piece={this.pieceForCol("G")} color="black"/>
                    <Tile row={this.props.row} col="H" piece={this.pieceForCol("H")} color="white"/>
                </div>)
        } else {
            return (
                <div className="row" >
                    <Tile row={this.props.row} col="A" piece={this.pieceForCol("A")} color="white"/>
                    <Tile row={this.props.row} col="B" piece={this.pieceForCol("B")} color="black"/>
                    <Tile row={this.props.row} col="C" piece={this.pieceForCol("C")} color="white"/>
                    <Tile row={this.props.row} col="D" piece={this.pieceForCol("D")} color="black"/>
                    <Tile row={this.props.row} col="E" piece={this.pieceForCol("E")} color="white"/>
                    <Tile row={this.props.row} col="F" piece={this.pieceForCol("F")} color="black"/>
                    <Tile row={this.props.row} col="G" piece={this.pieceForCol("G")} color="white"/>
                    <Tile row={this.props.row} col="H" piece={this.pieceForCol("H")} color="black"/>
                </div>)
        }
    }
}
class Board extends React.Component {
    tilesForRow(row) {
        var rw = row;
        return this.props.tiles.filter(function(elem) { return elem.position.row == rw })
    }

  render() {
      return (<div className="container">
    <Row row="One" tiles={this.tilesForRow("One")}/>
    <Row row="Two" tiles={this.tilesForRow("Two")} alt={true}/>
    <Row row="Three" tiles={this.tilesForRow("Three")} />
    <Row row="Four" tiles={this.tilesForRow("Four")}alt={true}/>
    <Row row="Five" tiles={this.tilesForRow("Five")} />
    <Row row="Six" tiles={this.tilesForRow("Six")} alt={true}/>
    <Row row="Seven" tiles={this.tilesForRow("Seven")} />
    <Row row="Eight" tiles={this.tilesForRow("Eight")} alt={true}/>
      </div>)
  }
}

var req = new XMLHttpRequest();
req.open("GET", "board")
req.addEventListener("load", function(evt, req) {
    var tiles = JSON.parse(evt.currentTarget.response);
    ReactDOM.render(<Board tiles={tiles.tiles}/>, document.getElementById('board'));
});

req.send()
