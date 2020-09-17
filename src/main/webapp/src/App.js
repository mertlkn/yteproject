import React, {useEffect} from 'react';
import ListAllEventsPeopleWithButton from "./components/PeopleSide/ListAllEventsPeople/ListAllEventsPeopleWithButton";
import Button from "@material-ui/core/Button";
import SideBar from "./components/AdminSide/SideBar";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Grid from "@material-ui/core/Grid";
import backgroundImg from '../src/background.png';

function App() {
    const [token, setToken] = React.useState("");
    const [loggedIn, setLoggedIn] = React.useState(false);
    const ws = new SockJS("http://localhost:8081/gs-guide-websocket");
    const stompClient = Stomp.over(ws);
    const [dataFromServer, setDataFromServer] = React.useState("");
    const [newPeoples, setNewPeoples] = React.useState([]);
    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };


    useEffect(() => {
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                setDataFromServer(JSON.parse(greeting.body).content);
            });
        });
    }, [])

    useEffect(() => {
        setNewPeoples(prevState => {
            let temp = [...prevState];
            temp.push(dataFromServer);
            if (dataFromServer != "" && loggedIn)
                handleClickOpen();
            return temp;
        })
    }, [dataFromServer])

    return (
        <body className="App" style={{
          //  backgroundImage: "url('https://cdn.hipwallpaper.com/i/8/48/RJFBiH.png')",
            backgroundImage: "url('../src/background.png')",
            backgroundSize: "cover",
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center",
            paddingTop: "20px",
            margin: "-10px",
            height: "800px",
            resize: "cover"
        }}>
        <Grid container spacing={3}>
            <Grid item xs={6}>
                <ListAllEventsPeopleWithButton stompClient={stompClient} dataFromServer={dataFromServer}
                                               setDataFromServer={setDataFromServer}/>
            </Grid>
            <Grid item xs={6}>
                <SideBar
                    setToken={setToken} token={token}
                    setLoggedIn={setLoggedIn} loggedIn={loggedIn}
                />
            </Grid>
        </Grid>


        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">New applicant!</DialogTitle>
            <DialogContent>
                <List>
                    {newPeoples.map(people => <ListItem key={people}>{people}</ListItem>)}
                </List>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancel
                </Button>
            </DialogActions>
        </Dialog>
        </body>
    );
}

export default App;
