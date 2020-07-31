import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { BrowserRouter, Route, Switch, Redirect,Router } from "react-router-dom";
import App from './App';
import * as serviceWorker from './serviceWorker';
import Intro from './Intro/Intro';
import Main from './Main/Main';
import Crop from './Main/Components/Crop';

ReactDOM.render(
  <React.StrictMode>
     <BrowserRouter>
              <Switch>
                <Route path="/intro" component={Intro} />
                <Route path="/crop" component={Crop} />
                <Route path="/main" component={Main} />
                <Route path="/" component={Intro} />
              </Switch>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
