import React, { Component } from 'react'
import ArticleDataService from '../../service/ArticleDataService';
import LineComponent from '../LineComponent';
import CommentAreaComponent from '../commentArea/CommentAreaComponent';
import ArticleNavBar from '../articleNavBar/ArticleNavBarComponent';
import './ArticleStyle.css';

class ArticleComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            article: ''
        }
        this.refreshArticle = this.refreshArticle.bind(this)
        this.headerClicked = this.headerClicked.bind(this)
    }

    componentDidMount() {
        this.refreshArticle();
    }

    refreshArticle() {
        ArticleDataService.retrieveArticleById(this.state.id)
            .then(
                response => {
                    console.log(response);
                    this.setState({article: response.data})
                }
            )
    }

    headerClicked() {
        this.props.history.push(`/articles`); //return home
    }

    render() {

        return (
            <>
            <div onClick={this.headerClicked}>
                <ArticleNavBar />
            </div>

            <div className="container">
                <h1 className="articleTitle">{this.state.article.title}</h1>

                {/* article information */}
                <div className="row">
                    <div className="col-md-2" style={{marginLeft: '16px', borderLeft: '1px solid red', color: '#6c757d'}}>
                        <h6>{this.state.article.author}</h6>
                    </div>
                    <div className="col-md-3"style={{marginLeft: '16px', borderLeft: '1px solid red', color: "#6c757d"}}>
                        <h6>{this.state.article.date}</h6>
                    </div>
                    <div className="col-md-7"></div>
                </div>

                {/* image */}
                <div className="container" style={{borderTop: '2px solid #343a40', paddingTop: '16px', marginTop:'16px'}}>
                    <div className="row">
                        <div className="span4"></div>
                            <div className="span4">
                                <img className="center-block img-fluid" src={`${process.env.PUBLIC_URL}/assets/${this.state.article.imageLocation}`} alt='' />
                            </div>
                        <div className="span4"></div>
                    </div>
                </div>
                
                <div className="container">
                    <p className="text-muted">{this.state.article.imageCaption}</p>
                </div>
                <LineComponent/>

                <div className="container">
                    <pre>{this.state.article.text}</pre>
                </div>

                <h4 className="tags">Tags: {this.state.article.tags}</h4>
                <LineComponent/>

                <CommentAreaComponent articleId={this.state.id}/>
            </div>
            </>
        )
    }
}

export default ArticleComponent