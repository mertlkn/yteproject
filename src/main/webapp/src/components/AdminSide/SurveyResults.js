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

export default function SurveyResults(props) {
    const [data,setData]=useState([])

    useEffect(() => {
        axios.get("/"+props.eventName+"/surveyAverages").then(response => {
            setData(prevState => {
                let temp = [...prevState]
                temp.push(response.data);
                return temp;
            });
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
                    valueField="date"
                    argumentField="eventName"
                    name="Date"
                />
                <BarSeries
                    valueField="content"
                    argumentField="eventName"
                    name="Content"
                />
                <Stack />
            </Chart>
        </Paper>
    );
}
