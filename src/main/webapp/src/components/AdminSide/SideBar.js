import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import MailIcon from '@material-ui/icons/Mail';
import ListAllEventsAdminWithButton from "./ListAllEventsAdmin/ListAllEventsAdminWithButton";
import ListApplicantsWithButton from "./ListApplicants/ListApplicantsWithButton";
import CustomBarChartApplicantCountWithButton from "./CustomBarChartApplicantCountWithButton";
import NewEventWithButton from "./NewEventWithButton";
import LoginWithButton from "./LoginWithButton";
import LogoutButton from "./LogoutButton";

const useStyles = makeStyles({
    list: {
        width: 250,
    },
    fullList: {
        width: 'auto',
    },
});

export default function SideBar(props) {
    const classes = useStyles();
    const [state, setState] = React.useState({
        top: false,
        left: false,
        bottom: false,
        right: false,
    });

    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }

        setState({ ...state, [anchor]: open });
    };

    const list = (anchor) => (
        <div style={{
            backgroundColor:"#E74344",
            backgroundSize:"cover",
            backgroundRepeat:"no-repeat",
            backgroundPosition:"center",
            paddingTop:"20px",
            margin:"-10px",
            height:"800px",
            resize:"cover"
        }}
            className={clsx(classes.list, {
                [classes.fullList]: anchor === 'top' || anchor === 'bottom',
            })}
            role="presentation"
        >
            {
                props.loggedIn ?
                <List>
                    <ListItem>
                        <NewEventWithButton/>
                    </ListItem>
                    <ListItem>
                        <ListAllEventsAdminWithButton token={props.token}/>
                    </ListItem>
                    <ListItem>
                        <CustomBarChartApplicantCountWithButton/>
                    </ListItem>
                </List> : null
            }
            <Divider />
            <List>
                {
                    props.loggedIn ?
                        <ListItem>
                            <LogoutButton setToken={props.setToken} setLoggedIn={props.setLoggedIn}/>
                        </ListItem>
                            :
                        <ListItem>
                            <LoginWithButton
                            setToken={props.setToken} token={props.token}
                            setLoggedIn={props.setLoggedIn} loggedIn={props.loggedIn}
                            />
                        </ListItem>
                }
            </List>
        </div>
    );

    return (
        <div>
            {['right'].map((anchor) => (
                <React.Fragment>
                    <Button variant="contained" style={{float:"right",backgroundColor:"#E74344"}} onClick={toggleDrawer(anchor, true)}>ADMIN CONTROLS</Button>
                    <Drawer anchor={anchor} open={state[anchor]} onClose={toggleDrawer(anchor, false)}>
                        {list(anchor)}
                    </Drawer>
                </React.Fragment>
            ))}
        </div>
    );
}
