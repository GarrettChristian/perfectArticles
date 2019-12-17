import React, { Component } from 'react';
import './ArticleNavStyle.css';

class ArticleNavBar extends Component {

    render() {
        return (
            <div >
                <nav className="navbar navbar-dark bg-dark" >
                    <p className="navbar-brand mb-0 h1 pa" >

                        <img src={`${process.env.PUBLIC_URL}/assets/pa.png`} width="30" height="30" 
                        className="d-inline-block align-top" style={{marginRight: '30px'}} alt=''/>
                        Perfect Articles
                    </p>

                </nav>
            </div>
        )
    }
}

export default ArticleNavBar