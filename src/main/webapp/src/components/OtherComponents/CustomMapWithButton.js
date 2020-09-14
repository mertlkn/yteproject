import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import CustomMap from "./CustomMap";

export default function CustomMapWithButton(props) {

    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    return (
        <div>
            <Button variant="outlined" style={{backgroundColor:"#E74344"}} onClick={handleClickOpen}>
                Map
            </Button>
            <Dialog open={open} onClose={handleClose}  aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Location</DialogTitle>
                <DialogContent>
                    <CustomMap lat={props.lat} lng={props.lng}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}