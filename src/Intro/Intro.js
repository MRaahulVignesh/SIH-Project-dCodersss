import React, { Component } from 'react';
import './Intro.css'
import { Modal, Button } from 'antd';
import "antd/dist/antd.css";
import Ticker from 'react-ticker'
import farmerIntro from './farmerintro.png'
import businessintro from './businessintro.png'
import Carousel from 'react-material-ui-carousel'
import Item from './Item.js'

class Intro extends Component{
    state = {
        modalVisible: false
    }
    login = (e) => {
        this.props.history.push(`/login`)
    }
    register = (e) => {
        this.props.history.push(`/register`)
    }
    showDialog = (e) => {
        this.setState({modalVisible:true})
    }
    setModalVisible(modalVisible) {
        this.setState({ modalVisible });
    }
    
    render(){
        var items = [
            {
                name: "Welcome",
                description: "We are here to provide you with the Best Agri products at Best Rates",
                imgSrc: "./businessintro.png"
            },
            {
                name: "Click on the menu to know more",
                imgSrc: "./farmerintro.png"
            }
        ]
        return(
            <div id="intro">
                <Ticker direction="toRight" height="25">
                    {({ index }) => (
                        <>
                            <h3 style={{marginLeft:"7px"}}>{index+1}:300 more entries recently </h3>
                        </>
                    )}
                </Ticker>
                <div id="menu" style={{display:"inline-block",backgroundColor:"black",width:"9vw",height:"100vh"
                                        ,verticalAlign:"top"}}>
                            <Modal
                                title="Vertically centered modal dialog"
                                centered
                                visible={this.state.modalVisible}
                                onOk={() => this.setModalVisible(false)}
                                onCancel={() => this.setModalVisible(false)}
                                >
                                <Button target="_blank" rel="noopener noreferrer" href="https://github.com/rarsahki/SIH_FRONTEND">SCHEME 1</Button>
                                <p>some contents...</p>
                                <p>some contents...</p>
                            </Modal>
                        <Button className="antButton" type="text"  style={{color:"white",display:"block",margin:"auto",marginTop:"5vh",fontSize:"2vw",fontFamily:"newFont"}}
                                onClick={(e)=>{this.showDialog(e)}}>Aim</Button>
                        <Button className="antButton"type="text"  style={{color:"white",display:"block",margin:"auto",marginTop:"5vh",fontSize:"2vw",fontFamily:"newFont"}}
                                onClick={(e)=>{this.showDialog(e)}}>How?</Button>
                        <Button className="antButton" type="text"  style={{color:"white",display:"block",margin:"auto",marginTop:"5vh",fontSize:"2vw",fontFamily:"newFont"}}
                                onClick={(e)=>{this.showDialog(e)}}>Schemes</Button>
                </div>
                <div style={{display:"inline-block",width:"75vw",height:"100vh",padding:"8.5%"}}>
                    <div style={{width:"75vw",height:"50vh"}}>
                        <Carousel style={{margin:"auto",width:"75vw",height:"50vh"}} navButtonsAlwaysVisible="true">
                            {
                                items.map( (item, i) => <Item key={i} item={item} /> )
                            }
                        </Carousel>
                    </div>
                    <div style={{marginTop:"5vh",width:"75vw"}}>
                        <Button type="text" style={{float:"left"}} onClick={(e) => {this.login(e)}}>LOGIN</Button>
                        <Button type="text" style={{float:"right"}} onClick={(e) => {this.register(e)}}>REGISTER</Button>
                    </div>
                </div>
            </div>
        )
    }
}

export default Intro;