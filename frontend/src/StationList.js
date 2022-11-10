import React, { useEffect, useState } from 'react';
import { Container, Table } from 'reactstrap';

const StationList = () => {

    const [stations, setStations] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('/api/stations')
            .then(response => response.json())
            .then(data => {
                setStations(data);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    const stationsList = stations.map(station => {
        const name = `${station.name}`;
        const availableBikes = `${station.num_bikes_available}`;
        const availableDocks = `${station.num_docks_available}`;

        return <tr>
            <td>{name}</td>
            <td>{availableBikes}</td>
            <td>{availableDocks}</td>
        </tr>
    });

    return (
        <div>
            <Container fluid>
                <Table>
                    <thead>
                    <tr>
                        <th>Stasjon</th>
                        <th width="25%">Ledige sykler</th>
                        <th width="25%">Tilgjengelige plasser</th>
                    </tr>
                    </thead>
                    <tbody>
                    {stationsList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default StationList;