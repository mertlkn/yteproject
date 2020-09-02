import React, {useEffect} from 'react';
import Ws from "./components/OtherComponents/Ws"
import ApplyPeopleDialogWithButton from "./components/PeopleSide/ApplyPeopleDialogWithButton";
import NewEventWithButton from "./components/AdminSide/NewEventWithButton";
import ListAllEventsAdmin from "./components/AdminSide/ListAllEventsAdmin/ListAllEventsAdmin";
import ListAllEventsAdminWithButton from "./components/AdminSide/ListAllEventsAdmin/ListAllEventsAdminWithButton";
import ListAllEventsPeople from "./components/PeopleSide/ListAllEventsPeople/ListAllEventsPeople";
import ListAllEventsPeopleWithButton from "./components/PeopleSide/ListAllEventsPeople/ListAllEventsPeopleWithButton";
import ListApplicantsWithButton from "./components/AdminSide/ListApplicants/ListApplicantsWithButton";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import Button from "@material-ui/core/Button";
import LoginWithButton from "./components/AdminSide/LoginWithButton";
import {BarChart} from "@material-ui/icons";
import CustomBarChartApplicantCount from "./components/AdminSide/CustomBarChartApplicantCount";
import CustomBarChartApplicantCountWithButton from "./components/AdminSide/CustomBarChartApplicantCountWithButton";
import SideBar from "./components/AdminSide/SideBar";
import MyQRCode from "./components/PeopleSide/MyQRCode";
import GoogleApiWrapper from "./components/OtherComponents/GoogleApiWrapper";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import SurveyWithButton from "./components/PeopleSide/SurveyWithButton";
import CustomMapWithButton from "./components/OtherComponents/CustomMapWithButton";


function App() {
    const [token,setToken]=React.useState("");
    const [loggedIn,setLoggedIn]=React.useState(false);
    const ws = new SockJS("http://localhost:8081/gs-guide-websocket");
    const stompClient = Stomp.over(ws);
    const [dataFromServer,setDataFromServer] = React.useState("");
    const [newPeoples,setNewPeoples] = React.useState([]);

    useEffect(() => {
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                setDataFromServer(JSON.parse(greeting.body).content);
            });
        });
    },[])

    useEffect(() => {
        setNewPeoples(prevState => {
            let temp = [...prevState];
            temp.push(dataFromServer);
            return temp;
        })
    },[dataFromServer])

    return (
    <body className="App" style={{
        backgroundImage:"url('https://www.expenseanywhere.com/wp-content/uploads/2016/09/website-backgrounds-E280AB1E280AC-E280ABE280AC.jpg')",
        backgroundSize:"cover",
        backgroundRepeat:"no-repeat",
        backgroundPosition:"center",
        paddingTop:"20px",
        margin:"-10px",
        height:"800px",
        resize:"cover"
    }}>
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/">
                                <Button variant={"contained"} color={"primary"}>Home</Button>
                            </Link>
                        </li>
                        <li>
                            <Link to="/people">
                                <Button variant={"contained"} color={"primary"}>People</Button>
                            </Link>
                        </li>
                    </ul>
                </nav>
                <Switch>
                    <Route path="/people">
                        <ListAllEventsPeopleWithButton stompClient={stompClient} dataFromServer={dataFromServer} setDataFromServer={setDataFromServer}/>
                    </Route>
                    <Route path="/">
                    </Route>
                </Switch>
                <SideBar
                    setToken={setToken} token={token}
                    setLoggedIn={setLoggedIn} loggedIn={loggedIn}
                />
                <List>
                    {newPeoples.map(people => <ListItem key={people}>{people}</ListItem>)}
                </List>
            </div>
        </Router>




        {/*<CustomMapWithButton lat={45.4211} lng={-75.6903}/>*/}
        {/*/!*<ListAllEventsAdminWithButton token={token}/>*!/*/}
        {/*<MyQRCode eventName={"etkinlik 1"} name={"Mert"} surname={"Alkan"} tcKimlikNo={"11111111110"}/>*/}
        {/*<GoogleApiWrapper/>*/}
        {/*{<Ws/>}*/}
        {/*<SurveyWithButton/>*/}
    </body>
  );
}

export default App;
