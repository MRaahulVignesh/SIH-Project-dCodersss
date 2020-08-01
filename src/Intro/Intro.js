import React, { Component } from 'react';
import './Intro.css'
import { Modal, Button } from 'antd';
import "antd/dist/antd.css";
import Ticker from 'react-ticker'
import farmerIntro from './farmerintro.png'
import businessintro from './businessintro.png'
class Intro extends Component{
    state = {
        modalVisible: false
    }
    login = (e) => {
        alert("hey")
    }
    register = (e) => {
        alert("hey")
    }
    showDialog = (e) => {
        this.setState({modalVisible:true})
    }
    setModalVisible(modalVisible) {
        this.setState({ modalVisible });
    }
    render(){
        return(
            <div>
                <Ticker direction="toRight" height="30">
                    {({ index }) => (
                        <>
                            <h2>This is the Headline of element #{index}!</h2>
                            <img src="www.my-image-source.com/" alt=""/>
                        </>
                    )}
                </Ticker>
                <div id="farmer" style={{display:"inline-block",backgroundColor:"lightgoldenrodyellow",width:"45vw",height:"100vh"
                                        ,verticalAlign:"top"}}>
                    <Button type="dashed" shape="round" style={{display:"flex",position:"absolute",left:"18vw",top:"15.5vh"}}
                            onClick={(e)=>{this.login(e)}}>Login</Button>
                    <img src={farmerIntro} 
                        style={{maxHeight:"25vh",maxWidth:"25vw",display:"flex",position:"absolute",left:"15vw",top:"35.5vh"}}/>
                    <Button type="dashed" shape="round" style={{display:"flex",position:"absolute",left:"17.75vw",bottom:"15.5vh"}}
                            onClick={(e)=>{this.register(e)}}>Register</Button>
                </div>
                <div id="buyer" style={{display:"inline-block",backgroundColor:"lightblue",width:"45vw",height:"100vh"
                                        ,verticalAlign:"top"}}>
                    <Button type="dashed" shape="round" style={{display:"flex",position:"absolute",left:"64vw",top:"15.5vh"}}
                            onClick={(e)=>{this.login(e)}}>Login</Button>
                    <img src={businessintro} 
                            style={{maxHeight:"25vh",maxWidth:"25vw",display:"flex",position:"absolute",left:"62.5vw",top:"35.5vh"}}/>
                    <Button type="dashed" shape="round" style={{display:"flex",position:"absolute",left:"63.5vw",bottom:"15.5vh"}}
                            onClick={(e)=>{this.register(e)}}>Register</Button>
                </div>
                <div id="menu" style={{display:"inline-block",backgroundColor:"lightgreen",width:"8.75vw",height:"100vh"
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
                        <Button className="antButton" type="text"  style={{display:"block",margin:"auto",marginTop:"5vh",fontSize:"3vh",fontFamily:"'Courier New', Courier, monospace"}}
                                onClick={(e)=>{this.showDialog(e)}}>Aim</Button>
                        <Button className="antButton"type="text"  style={{display:"block",margin:"auto",marginTop:"5vh",fontSize:"3vh",fontFamily:"'Courier New', Courier, monospace"}}
                                onClick={(e)=>{this.showDialog(e)}}>How?</Button>
                        <Button className="antButton" type="text"  style={{display:"block",margin:"auto",marginTop:"5vh",fontSize:"3vh",fontFamily:"'Courier New', Courier, monospace",overflow:"hidden"}}
                                onClick={(e)=>{this.showDialog(e)}}>Schemes</Button>
                </div>
            </div>
        )
    }
}

export default Intro;