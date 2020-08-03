import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import { Button } from '@material-ui/core';

class Reports extends Component {
    constructor(props) {
        super(props);
        this.state = {
            quantity: 0,
            paymentMethod: "",
            installments: 0, 
            click: false,
            i: 0,
            data: [139.841, 138.241, 139.231, 140.43, 139.89]
        }
        this.onQuantityChanged = this.onQuantityChanged.bind(this);
        this.onInstallmentChanged = this.onInstallmentChanged.bind(this);
        this.onPaymentChanged = this.onPaymentChanged.bind(this);
        this.onButtonClick = this.onButtonClick.bind(this);
    }
    onQuantityChanged(event) {
        this.setState({
            quantity: event.target.value
        });
        // console.log(this.state.quantity);
    }
    onPaymentChanged(event) {
        this.setState({
            paymentMethod: event.target.value
        });
        // console.log(this.state.quantity);
    }
    onInstallmentChanged(event) {
        this.setState({
            installments: event.target.value
        });
        // console.log(this.state.quantity);
    }
    onButtonClick(){
        this.setState({
            click: true,
            i: this.state.i+1
        })
        console.log(this.state.quantity + this.state.paymentMethod + this.state.installments);
    }
    render() {
        return (
            <div><br></br>
                <br></br>
                <br></br>
                <br></br>
                <br></br>
                <div style={styles.root}>
                    <Typography variant="h5" >Check the Price</Typography>
                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Year" variant="outlined" onChange={this.onQuantityChanged} />

                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Month" variant="outlined" onChange={this.onPaymentChanged} />
                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Rainfall" variant="outlined" onChange={this.onInstallmentChanged} />
                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Mandi" variant="outlined" onChange={this.onInstallmentChanged} />
                </div>
                <br></br>
                <div style={styles.root}>
                    <Button variant="contained" color="primary" onClick={this.onButtonClick}>
                        Submit
                    </Button>
                </div>
                <br></br>
                <div style = {styles.root}>
        {this.state.click?<Typography>{this.state.data[this.state.i]}</Typography> : <div></div>}
                </div>
            </div>
        )
    };
}

const styles = {
    root: {

        textAlign: 'center'
    },
    text: {
        padding: '10px',
        width: '80%',
    }
}

export default Reports;