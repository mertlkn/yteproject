import React, {useEffect,useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import Button from "@material-ui/core/Button";
import axios from "axios";
import ApplyPeopleDialogWithButton from "../ApplyPeopleDialogWithButton";
import CustomMapWithButton from "../../OtherComponents/CustomMapWithButton";


const columns = [
    { id: 'eventName', label: 'Name', minWidth: 170 },
    { id: 'eventStartTime', label: 'Start Date', minWidth: 100 },
    { id: 'eventEndTime', label: 'End Date', minWidth: 100 },
    { id: 'quota', label: 'Quota', minWidth: 100 }

];


const useStyles = makeStyles({
    root: {
        width: '100%',
    },
    container: {
        maxHeight: 440,
    },
});

export default function ListAllEventsPeople(props) {
    const classes = useStyles();
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const [data,setData] = useState([]);

    useEffect(() => {
        axios.get("/getEventsUser").then(response => {
            setData(response.data);
        })
    },[data])


    return (
        <Paper className={classes.root}>
            <TableContainer className={classes.container}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.id}
                                    style={{ minWidth: column.minWidth }}
                                >
                                    {column.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return (
                                <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                                    {columns.map((column) => {
                                        const value = row[column.id];
                                        return (
                                            <TableCell key={column.id}>
                                                {value}
                                            </TableCell>
                                        );
                                    })}
                                    <ApplyPeopleDialogWithButton stompClient={props.stompClient} dataFromServer={props.dataFromServer} setDataFromServer={props.setDataFromServer} eventName={row["eventName"]}/>
                                    <CustomMapWithButton lat={row["latitude"]} lng={row["longitude"]}/>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={data.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onChangePage={handleChangePage}
                onChangeRowsPerPage={handleChangeRowsPerPage}
            />
        </Paper>
    );
}
