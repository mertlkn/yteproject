import React from "react";
import {Map, GoogleApiWrapper, Marker} from 'google-maps-react';

function MapContainer() {
    const mapStyles = {
        width: '100%',
        height: '100%',
    };
        return (
            <Map
                zoom={8}
                style={mapStyles}
                initialCenter={{ lat: 47.444, lng: -122.176}}
            >
                <Marker position={{ lat: 48.00, lng: -122.00}} />
            </Map>
        );
}

export default GoogleApiWrapper({
    apiKey: "AIzaSyDtTORyLt46pIarrOxnjXlhIJyZvIAtBu0"
})(MapContainer);