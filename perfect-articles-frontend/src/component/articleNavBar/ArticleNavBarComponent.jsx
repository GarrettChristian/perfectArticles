import React, { Component } from 'react';
import './ArticleNavStyle.css';

class ArticleNavBar extends Component {

    render() {
        return (
            <div >
                <nav className="navbar navbar-dark bg-dark" >
                    <p className="navbar-brand mb-0 h1 pa" >

                        <img src={`${process.env.PUBLIC_URL}/assets/logoBP.jpg`} width="60" height="35" 
                        className="d-inline-block align-top" style={{marginRight: '30px'}} alt=''/>
                        Blazing Point Articles
                    </p>

                </nav>
            </div>
        )
    }
}

export default ArticleNavBar