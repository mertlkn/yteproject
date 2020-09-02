import React, {useEffect,useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import axios from "axios";
import CustomAlerts from "../OtherComponents/CustomAlerts";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import SurveyWithButton from "./SurveyWithButton";

export default function ApplyPeopleDialogWithButton(props) {
    const [open, setOpen] = useState(false);
    const [name,setName] = useState("");
    const [surname,setSurname] = useState("");
    const [tcKimlikNo,setTcKimlikNo] = useState("");
    const [email,setEmail] = useState("");
    const [message,setMessage] = useState("");
    const [failed,setFailed] = useState(false);
    const [clicked,setClicked] = useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    function handleSubmit(eventName) {
        axios.post("/applyUser/"+eventName,{
            name:name,
            surname:surname,
            tcKimlikNo:tcKimlikNo
        }).then(response => {
            if(response.data["messageType"]=="ERROR")
                setFailed(true);
            else {
                setFailed(false);
                props.stompClient.send("/app/hello", {}, JSON.stringify({
                    'eventName': eventName,
                    'name': name,
                    'surname': surname,
                    'tcKimlikNo': tcKimlikNo
                }))
            }
            setMessage(response.data["message"])
        });
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                Apply for this event
            </Button>
            <Dialog open={open} onClose={handleClose} onSubmit={handleSubmit} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Information</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        To register for this event plese enter your information:
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Name"
                        fullWidth
                        value={name}
                        onChange={event => setName(event.target.value)}
                    />
                    <TextField
                        margin="dense"
                        id="surname"
                        label="Surname"
                        fullWidth
                        value={surname}
                        onChange={event => setSurname(event.target.value)}
                    />
                    <TextField
                        margin="dense"
                        id="tcKimlikNo"
                        label="TC Kimlik No"
                        fullWidth
                        value={tcKimlikNo}
                        onChange={event => setTcKimlikNo(event.target.value)}
                    />
                    <TextField
                        margin="dense"
                        id="email"
                        label="Email Address"
                        type="email"
                        fullWidth
                        value={email}
                        onChange={event => setEmail(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button type="submit" onClick={() => {
                        setClicked(true);
                        handleSubmit(props.eventName);
                    }} color="primary">
                        Apply
                    </Button>
                </DialogActions>
                {
                clicked?<CustomAlerts messageType={failed?"error":"success"} message={message}/>:null
                }
                {
                clicked?(failed?null:<SurveyWithButton/>):null
                }
            </Dialog>
        </div>
    );
}
