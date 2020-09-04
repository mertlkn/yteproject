import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import axios from "axios";

export default function SurveyWithButton(props) {
    const [open, setOpen] = React.useState(false);
    const [date, setDate] = React.useState(3);
    const [content, setContent] = React.useState(3);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleChange = (event) => {
        setDate(event.target.value);
    };
    const handleChange2 = (event) => {
        setContent(event.target.value);
    };

    const handleSubmit = () => {
        axios.post("http://localhost:8081/"+props.eventName+"/surveyAnswer",{
            date:date,
            content:content
        }).then(console.log("sent"));
    }
    return (
        <div>
            <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                Survey
            </Button>
            <Dialog open={open} onClose={handleClose} onSubmit={handleSubmit} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Survey</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Please answer the survey
                    </DialogContentText>
                    <FormControl component="fieldset">
                        <FormLabel component="legend">Is date appropriate?</FormLabel>
                        <RadioGroup aria-label="date" name="date1" value={date} onChange={handleChange}>
                            <FormControlLabel value="5" control={<Radio />} label="5" />
                            <FormControlLabel value="4" control={<Radio />} label="4" />
                            <FormControlLabel value="3" control={<Radio />} label="3" />
                            <FormControlLabel value="2" control={<Radio />} label="2" />
                            <FormControlLabel value="1" control={<Radio />} label="1" />
                        </RadioGroup>
                        <FormLabel component="legend">Is content appropriate?</FormLabel>
                        <RadioGroup aria-label="content" name="content1" value={content} onChange={handleChange2}>
                            <FormControlLabel value="5" control={<Radio />} label="5" />
                            <FormControlLabel value="4" control={<Radio />} label="4" />
                            <FormControlLabel value="3" control={<Radio />} label="3" />
                            <FormControlLabel value="2" control={<Radio />} label="2" />
                            <FormControlLabel value="1" control={<Radio />} label="1" />
                        </RadioGroup>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={() =>{
                        handleSubmit();
                        handleClose();
                    }} color="primary">
                        Send
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
