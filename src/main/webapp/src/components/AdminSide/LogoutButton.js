import React from "react";
import Button from "@material-ui/core/Button";

export default function LogoutButton(props) {
    return(
        <Button variant={"contained"} color={"primary"} onClick={() => {
            props.setToken("");
            props.setLoggedIn(false);
        }}>Logout</Button>
    )
}