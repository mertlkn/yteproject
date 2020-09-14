import React, {useEffect,useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import axios from "axios";

export default function LoginWithButton(props) {
    const [open, setOpen] = useState(false);
    const [username,setUsername] = useState("");
    const [password,setPassword] = useState("");
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    function handleSubmit(eventName) {
        axios.post("/login", {
            username:username,
            password:password
        })
            .then(response => {
                props.setToken(response.data["token"])
                if(response.data["messageResponse"]["messageType"]=="SUCCESS")
                props.setLoggedIn(true);
            })
    }

    return (
        <div>
            <Button variant={"outlined"} color="#0A1616"  onClick={handleClickOpen}>
                Login
            </Button>
            <Dialog open={open} onClose={handleClose} onSubmit={handleSubmit} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Information</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Please enter your username and password to log in.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="username"
                        label="Username"
                        fullWidth
                        value={username}
                        onChange={event => setUsername(event.target.value)}
                    />
                    <TextField
                        margin="dense"
                        id="password"
                        label="Password"
                        type={"password"}
                        fullWidth
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button type="submit" onClick={() => {
                        handleSubmit();
                        handleClose();
                    }} color="primary">
                        Login
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
