import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import DateEntry from "../OtherComponents/DateEntry";
import axios from "axios";
import SuccessDialog from "../OtherComponents/SuccessDialog";
import ErrorDialog from "../OtherComponents/ErrorDialog";
import CustomAlerts from "../OtherComponents/CustomAlerts";

export default function NewEventWithButton() {
    const [open, setOpen] = React.useState(false);
    const [eventName,setEventName] = React.useState("");
    const [eventStartTime,setEventStartTime] =React.useState("2000-01-01T00:00");
    const [eventEndTime,setEventEndTime] =React.useState("2000-01-01T00:00");
    const [quota,setQuota] =React.useState(0);
    const [latitude,setLatitude] =React.useState("");
    const [longitude,setLongitude] =React.useState("");
    const [failed,setFailed] = React.useState(false);
    const [message,setMessage] = React.useState("");
    const [clicked,setClicked] = useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    function handleSubmit() {
        axios.post("/addEvent", {
            eventName:eventName,
            eventStartTime:eventStartTime,
            eventEndTime:eventEndTime,
            quota:quota,
            latitude:latitude,
            longitude:longitude
        }).then(response => {
            if(response.data["messageType"]=="ERROR")
                setFailed(true);
            else setFailed(false);
            setMessage(response.data["message"])
        })
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                New Event
            </Button>
            <Dialog open={open} onClose={handleClose}  aria-labelledby="form-dialog-title" onSubmit={<SuccessDialog/>}>
                <DialogTitle id="form-dialog-title">Information</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Event's information
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="eventName"
                        label="Event Name"
                        fullWidth
                        value={eventName}
                        onChange={event => setEventName(event.target.value)}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="latitude"
                        label="Latitude"
                        fullWidth
                        value={latitude}
                        onChange={event => setLatitude(event.target.value)}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="longitude"
                        label="Longitude"
                        fullWidth
                        value={longitude}
                        onChange={event => setLongitude(event.target.value)}
                    />
                    <DateEntry name={"Event Start Date"} time={eventStartTime} setTime={setEventStartTime}/>
                    <DateEntry name={"Event End Date"} time={eventEndTime} setTime={setEventEndTime}/>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="quota"
                        label="Quota"
                        type="number"
                        fullWidth
                        value={quota}
                        onChange={event => setQuota(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    {
                    <Button onClick={() =>{
                        setClicked(true);
                        handleSubmit();
                    }}
                    color="primary"
                    failed={failed}
                    >
                        Add
                    </Button>
                    }
                </DialogActions>
                {
                    clicked?<CustomAlerts messageType={failed?"error":"success"} message={message}/>:null
                }
            </Dialog>
        </div>
    );
}
