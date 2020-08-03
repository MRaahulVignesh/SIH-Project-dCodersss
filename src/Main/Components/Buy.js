//build a form to buy individual crop and update quanity
//to be done Ark:http://localhost:5000/api/addBlock add content

import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import { Button } from '@material-ui/core';

class Buy extends Component {
    constructor(props) {
        super(props);
        this.state = {
            quantity: 0,
            paymentMethod: "",
            installments: 0
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
        console.log(this.state.quantity + this.state.paymentMethod + this.state.installments);
    }
    render() {
        return (
            <div><br></br>
                <br></br>
                <br></br>
                <div style={styles.root}>
                    <Typography variant="h5" >Submit the form to proceed</Typography>
                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Quantity" variant="outlined" onChange={this.onQuantityChanged} />

                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Payment Method" variant="outlined" onChange={this.onPaymentChanged} />
                </div>
                <br></br>
                <div style={styles.root}>
                    <TextField style={styles.text} id="outlined-basic" label="Installments" variant="outlined" onChange={this.onInstallmentChanged} />
                </div>
                <br></br>
                <div style={styles.root}>
                    <Button variant="contained" color="primary" onClick={this.onButtonClick}>
                        Submit
                    </Button>
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
export default Buy;