import React, {useEffect, useState} from "react";

import axios from "axios"
import {Image} from "@material-ui/icons";
export default function MyQRCode(props) {
    const sourceURL="http://localhost:8081/"+props.eventName+"/qrcode?name="+props.name+"&surname="+props.surname+"&tcKimlikNo="+props.tcKimlikNo;
    return(
        <img src={sourceURL}/>
    )
}
