import React, { Component } from 'react';
import { GridList } from 'material-ui';
import { GridListTile, ListSubheader } from '@material-ui/core';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import CircularProgress from '@material-ui/core/CircularProgress';
import fire, { db } from '../../Config/fire.js';
import Typography from 'material-ui/styles/typography';

class Listed extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            cropId: "",
            cropName: "",
            delivered: false,
            expectedDate: '',
            imageURL: "",
            organic: false,
            price: 0,
            remainingQuantity: 0,
            sellerId: "",
            totalQuantity: 0,
            isLoading: false,
            isError: false
        }
    }
    componentDidMount() {
        db.collection('Agri').doc('Crops').get().then(doc => {
            this.setState({
                data: doc.data(),
                isLoading: true,
                isError: false,
            });


        }).then(() => {
            console.log("Document successfully written!");
            this.setState({
                isLoading: false,
                isError: false
            });
        }).catch(function (error) {
            console.log("Error writing document: ", error);
            this.setState({
                isLoading: false,
                isError: true,
            });
        });
    }

    render() {
        return (
            <div>
                <GridList cellHeight={180} >
                    <GridListTile key="Subheader" cols={2} style={{ height: 'auto' }}>
                        <ListSubheader component="div">Crops availability</ListSubheader>
                    </GridListTile>
                    {this.state.data.map((tile) => (
                        <GridListTile key={tile.cropId}>
                            <img src={tile.imageURL} alt={tile.cropName} />
                            <GridListTileBar
                                title={tile.cropName}
                                subtitle={<span>by: {tile.price}</span>}
                                
                            />
                        </GridListTile>
                    ))}
                </GridList>
            </div>

        )
    }
}
export default Listed;