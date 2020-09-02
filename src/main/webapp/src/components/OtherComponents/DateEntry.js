import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';


export default function DateEntry(props) {

    return (
        <form noValidate>
            <TextField
                id="datetime-local"
                label={props.name}
                type="datetime-local"
                defaultValue={"2000-01-01T00:00"}
                InputLabelProps={{
                    shrink: true,
                }}
                value={props.time}
                onChange={event => props.setTime(event.target.value)}
            />
        </form>
    );
}
