import React, {Component} from "react";
import SockJS from 'sockjs-client';
import Stomp from "stompjs"
export default class Ws extends Component {

    ws = new SockJS("http://localhost:8081/gs-guide-websocket");
    stompClient = Stomp.over(this.ws);

    constructor(props) {
        super(props);
        this.state = {
            dataFromServer:"",
            msg:"",
            eventName:props.eventName,
            name:props.name,
            surname:props.name,
            tcKimlikNo:props.tcKimlikNo
        }
        this.handleClick = this.handleClick.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }


    componentDidMount() {
        const outerThis=this;
        this.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            outerThis.stompClient.subscribe('/topic/greetings', function (greeting) {
                outerThis.setState({
                    dataFromServer: JSON.parse(greeting.body).content
                })
            });
            outerThis.stompClient.send("/app/hello", {}, JSON.stringify({
                'eventName': outerThis.state.eventName,
                'name': outerThis.state.name,
                'surname': outerThis.state.surname,
                'tcKimlikNo': outerThis.state.tcKimlikNo
            }));
        });

    }
    handleClick() {
        this.stompClient.send("/app/hello",{},this.state.msg);
        console.log("sent")
    }

    handleChange(event) {
        this.setState({
            msg:event.target.value
        })
    }

    render(){
        return (
            <div>
                <textarea value={this.state.msg} onChange={this.handleChange}/>
                <button onClick={this.handleClick}/>
                <br/>
                <textarea value={JSON.stringify(this.state.dataFromServer)}/>
            </div>
        )
    }
}
