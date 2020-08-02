import React, { Component } from 'react';
import fire, { db } from '../../Config/fire.js';
import GridList from '@material-ui/core/GridList';
import { GridListTile, ListSubheader } from '@material-ui/core';
import { GridListTileBar } from '@material-ui/core';
import { ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';
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
            const d = doc.data();
            console.log(d.cropsList)
            this.setState({
                data: doc.data().cropsList,
                isLoading: true,
                isError: false,
            });
            console.log(this.state.data)

        }).then((data) => {
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
        const { classes} = this.props;
        return (
            <div>
                <GridList cellHeight={180} >
                    <GridListTile key="Subheader" cols={2} style={{ height: 'auto' }}>
                        <ListSubheader component="div">Crops availability</ListSubheader>
                    </GridListTile>
                    {this.state.data.map((tile) => (
                        <GridListTile key={tile.imageURL}>
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