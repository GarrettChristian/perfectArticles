import React, { Component } from 'react'
import "./HomeStyle.css"
import LineComponent from '../LineComponent';
import ArticleDataService from '../../service/ArticleDataService';
import ArticleNavBar from '../articleNavBar/ArticleNavBarComponent';

class HomeComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            articles: [],
            allArticles: [],
            isOpened: false
        }
        this.refreshHome = this.refreshHome.bind(this)
        this.loadArticleClicked = this.loadArticleClicked.bind(this)
        this.headerClicked = this.headerClicked.bind(this)
        this.toggleBox = this.toggleBox.bind(this)
    }

    componentDidMount() {
        this.refreshHome();
    }

    refreshHome() {
        ArticleDataService.retrieveArticlesByDate(3)
            .then(
                response => {
                    console.log(response);
                    this.setState({articles: response.data})
                }
            )
    }

    titleLogoClicked() {
        ArticleDataService.addExampleArticles(); //add the example articles to the database
        this.refreshHome();
    }

    loadArticleClicked(id) {
        this.props.history.push(`/articles/${id}`) //change to selected article
    }

    headerClicked() {
        this.props.history.push(`/articles`); //return to home
    }

    toggleBox() {
        this.setState({isOpened: true});
        ArticleDataService.retrieveAllArticles() //Get all the articles
            .then(
                response => {
                    console.log(response);
                    this.setState({allArticles: response.data})
                }
            )
    }

    render() {
        const { isOpened} = this.state;

        return (
            <>
            <div onClick={this.headerClicked}>
                <ArticleNavBar />
            </div>

            <div className="container">
                <div className="container">
                    <div className="title">
                        <img src={`${process.env.PUBLIC_URL}/assets/logoBP.jpg`} onClick={() => this.titleLogoClicked()} width="160" height="85" alt=''/>
                        <h1 style={{marginLeft: '70px', marginRight: '150px', }}>Blazing Point Articles</h1>
                    </div>

                    <h3 className="subHeaderFeatured">Featured Articles</h3> 
                    <LineComponent/>
                    <div className="row" style={{paddingLeft: "15px", paddingRight:"15px"}}>
                        {
                            this.state.articles.map( //Most reccent three articles
                                article =>
                                    <div className="col-md-4 innerText" key={article.id}>
                                        <button className="btn " onClick={() => this.loadArticleClicked(article.id)}>{article.title}</button>
                                        <div className="span4">
                                            <img className="center-block img-fluid" onClick={() => this.loadArticleClicked(article.id)} 
                                            src={`${process.env.PUBLIC_URL}/assets/${article.imageLocation}`} alt='' />
                                        </div>
                                    </div>
                                )
                        }
                            
                    </div>
                    <LineComponent/>

                    { !isOpened && ( //Get all the articles
                        <button className="btn btn-block viewArticlesButton" onClick={this.toggleBox}>Display All Articles</button>)}
                        <div className="box tableArticles">
                            { isOpened && (
                            <div>
                                <h3 className="subHeader">All Articles</h3>
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th>Title</th>
                                            <th>Author</th>
                                            <th>Posted</th>
                                            <th>Tags</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    {this.state.allArticles.map(article =>
                                        <tr onClick={() => this.loadArticleClicked(article.id)} key={article.id}>
                                            <td>{article.title}</td>
                                            <td>{article.author}</td>
                                            <td>{article.date}</td>
                                            <td>{article.tags}</td>
                                        </tr>
                                    )}
                                    </tbody>
                                </table>
                            </div>)}
                        </div>
                        <LineComponent/>
                </div>
            </div>
            </>
        )
    }
}

export default HomeComponent