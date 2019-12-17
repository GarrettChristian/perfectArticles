import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ArticleComponent from './article/ArticleComponent';
import HomeComponent from './home/HomeComponent';

class ArticleApp extends Component {

    render() {
        return (
            <Router>
                <>
                    <Switch>
                        <Route path="/" exact component={HomeComponent} />
                        <Route path="/articles" exact component={HomeComponent} /> 
                        <Route path="/articles/:id" component={ArticleComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default ArticleApp