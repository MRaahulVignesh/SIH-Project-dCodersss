import React, { Component } from 'react';
import Dashboard from './Dashboard.js'
import Alert from '@material-ui/lab/Alert';
class GovtDashboard extends Component{
    state = {
        welcome: false
    }
    componentDidMount(){
        if(typeof this.props.location.state !== "undefined"){
            this.setState({welcome:true})
        }
    }
    render(){
        return(
            <div style={{overflowY:"hidden"}}>
                {this.state.welcome?
                    <Alert variant="filled" severity="success">
                            Welcome
                    </Alert>:
                    <div></div>
                }
                <Dashboard/>
            </div>
        )
    }
}
export default GovtDashboard