import React from "react";
import Button from "@material-ui/core/Button";

export default function LogoutButton(props) {
    return(
        <Button variant={"outlined"} color="#0A1616" onClick={() => {
            props.setToken("");
            props.setLoggedIn(false);
        }}>Logout</Button>
    )
}