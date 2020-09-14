import React, {useState} from 'react';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogContent from '@material-ui/core/DialogContent';
import MuiDialogActions from '@material-ui/core/DialogActions';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';
import axios from "axios";

const styles = (theme) => ({
    root: {
        margin: 0,
        padding: theme.spacing(2),
    },
    closeButton: {
        position: 'absolute',
        right: theme.spacing(1),
        top: theme.spacing(1),
        color: theme.palette.grey[500],
    },
});

const DialogTitle = withStyles(styles)((props) => {
    const { children, classes, onClose, ...other } = props;
    return (
        <MuiDialogTitle disableTypography className={classes.root} {...other}>
            <Typography variant="h6">{children}</Typography>
            {onClose ? (
                <IconButton aria-label="close" className={classes.closeButton} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
            ) : null}
        </MuiDialogTitle>
    );
});

const DialogContent = withStyles((theme) => ({
    root: {
        padding: theme.spacing(2),
    },
}))(MuiDialogContent);

const DialogActions = withStyles((theme) => ({
    root: {
        margin: 0,
        padding: theme.spacing(1),
    },
}))(MuiDialogActions);

export default function GiveawayWithButton(props) {
    const [open, setOpen] = React.useState(false);
    const [name,setName] = useState("");
    const [surname,setSurname] = useState("");
    const [tcKimlikNo,setTcKimlikNo] = useState("");
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };
    React.useEffect(() => {
        axios.get("http://localhost:8081/"+props.eventName+"/giveaway").then(response => {
            setName(response.data["name"]);
            setSurname(response.data["surname"]);
            setTcKimlikNo(response.data["tcKimlikNo"]);
            console.log(name,surname,tcKimlikNo)
        })
    },[])
    return (
        <div>
            <Button variant="outlined" style={{backgroundColor:"#E74344"}} onClick={handleClickOpen}>
                Giveaway
            </Button>
            <Dialog onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
                <DialogTitle id="customized-dialog-title" onClose={handleClose}>
                    Giveaway
                </DialogTitle>
                <DialogContent dividers>
                    <Typography gutterBottom>
                        The winner of the {props.eventName} giveaway is
                    </Typography>
                    <Typography gutterBottom>
                        Name: {name}
                    </Typography>
                    <Typography gutterBottom>
                        Surname: {surname}
                    </Typography>
                    <Typography gutterBottom>
                        TC Kimlik No: {tcKimlikNo}
                    </Typography>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={handleClose} color="primary">
                        OK
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
