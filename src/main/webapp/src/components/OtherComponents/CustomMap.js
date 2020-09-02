import React, { useState, useEffect } from "react";
import {
    withGoogleMap,
    withScriptjs,
    GoogleMap,
    Marker,
} from "react-google-maps";

function Map(props) {

    return (
        <GoogleMap
            defaultZoom={15}
            defaultCenter={{ lat: props.lat, lng: props.lng }}
        >
                <Marker
                    key={"mykey"}
                    position={{
                        lat: props.lat,
                        lng: props.lng
                    }}
                />
            )
        </GoogleMap>
    );
}

const MapWrapped = withScriptjs(withGoogleMap(Map));

export default function CustomMap(props) {
    return (
        <div style={{ width: "30vw", height: "100vh" }}>
            <MapWrapped
                googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyBgWtdKyE6P74GDEb1f9eeETgbzIwVXuRw`}
                loadingElement={<div style={{ height: `100%` }} />}
                containerElement={<div style={{ height: `100%` }} />}
                mapElement={<div style={{ height: `100%` }} />}
                lat={props.lat}
                lng={props.lng}
            />
        </div>
    );
}