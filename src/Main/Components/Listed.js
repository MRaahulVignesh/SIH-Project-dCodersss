import React, { Component } from 'react';
import fire, { db } from '../../Config/fire.js';
import GridList from '@material-ui/core/GridList';
import { GridListTile, ListSubheader, Grid, Paper, ButtonBase } from '@material-ui/core';
import { GridListTileBar } from '@material-ui/core';
import { ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';
// import Typography from 'material-ui/styles/typography';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
// import Card from '@material-ui/core/Card';


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
    componentWillMount() {
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
        }).catch((error) => {
            console.log("Error writing document: ", error);
            this.setState({
                isLoading: false,
                isError: true,
            });
        });
    }

    render() {
        const { classes } = this.props;
        return (
            <div style={styles.root}>
                <br></br><br></br><br></br>
                <br></br><br></br>
                {this.state.data.map((crop) => (
                    <div>
                        {/* <Card style={styles.root}>
                            <CardActionArea>
                                <CardMedia
                                    style={styles.media}
                                    image={crop.imageURL}
                                    title="Contemplative Reptile"
                                />
                                <CardContent>
                                    <Typography gutterBottom variant="h5" component="h2">
                                        {crop.cropName}
                                    </Typography>
                                    
                                </CardContent>
                            </CardActionArea>
                            <CardActions>
                                <Button size="small" color="primary">
                                    Buy Crop
                                </Button>
                                
                            </CardActions>
                        </Card> */}
                        <Paper style={styles.paper}>
                            <Grid container spacing={2}>
                                <Grid item>
                                    <ButtonBase style={styles.image}>
                                        <img style={styles.img} alt="complex" src={crop.imageURL} />
                                    </ButtonBase>
                                </Grid>
                                <Grid item xs={12} sm container>
                                    <Grid item xs container direction="column" spacing={2}>
                                        <Grid item xs>
                                            <Typography gutterBottom variant="h5">
                                                {crop.cropName}
                                            </Typography>
                                            <Typography variant="body2" gutterBottom>
                                                Expected Date: {crop.expectedDate}
                                            </Typography>
                                            <Typography variant="body2" color="subtitle1">
                                                Organic: {crop.organic ? 'Yes' : 'No'}
                                            </Typography>

                                            <Typography variant="body2" color="subtitle1">
                                                Total Quantity: {crop.totalQuantity}
                                            </Typography>
                                            <Typography variant="body2" color="subtitle1">
                                                Remaining Quantity: {crop.remainingQuantity}
                                            </Typography>

                                        </Grid>
                                        <Grid item>
                                            {crop.remainingQuantity? <Button size="small" color="primary" onClick={()=>this.props.history.push('/buythecrop')}>
                                                Buy Crop
                                            </Button>: <Button size="small" color="primary" disabled>
                                            Buy Crop
                                        </Button> }
                                            
                                        </Grid>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="subtitle1">{"Rs. " + crop.price + ' /quintal'}</Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Paper>
                        <br></br>
                    </div>
                ))}

            </div>

        )
    }
}



const styles = {
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: '10px',
        margin: 'auto',
        maxWidth: 500,
    },
    image: {
        width: '',
        height: '100%',
    },
    img: {
        margin: 'auto',
        display: 'block',
        maxWidth: '100%',
        maxHeight: '100%',
    },
}
export default Listed;