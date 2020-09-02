import React, {useEffect,useState} from 'react';
import Paper from '@material-ui/core/Paper';
import {
    Chart,
    ArgumentAxis,
    ValueAxis,
    BarSeries,
} from '@devexpress/dx-react-chart-material-ui';
import axios from "axios"
import { scaleBand } from '@devexpress/dx-chart-core';
import { ArgumentScale, Stack } from '@devexpress/dx-react-chart';

export default function CustomBarChartApplicantCount(props) {
    const [data,setData]=useState([])

    useEffect(() => {
        axios.get("/getEvents/applicantsCount").then(response => {
            setData(response.data);
        })
    },[])
    return (
            <Paper>
                <Chart
                    data={data}
                >
                    <ArgumentScale factory={scaleBand} />
                    <ArgumentAxis />
                    <ValueAxis />

                    <BarSeries
                        valueField="applicantsCount"
                        argumentField="eventName"
                        name="Count"
                    />
                    <Stack />
                </Chart>
            </Paper>
        );
}
