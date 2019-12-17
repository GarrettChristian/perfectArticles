import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import LineComponent from '../LineComponent';
import CommentComponent from '../comment/CommentComponent';
import CommentDataService from '../../service/CommentDataService';
import "./CommentAreaStyle.css"

class CommentAreaComponent extends Component {

  constructor(props) {
      super(props);
      this.state = {
        comment: '',
        comments: [],
        commentUserName: '',
        commentText: '',
        isOpened: false,
        articleId: this.props.articleId
      };
      this.toggleBox = this.toggleBox.bind(this);
      this.refreshComment = this.refreshComment.bind(this);
      this.onSubmit = this.onSubmit.bind(this);
      this.validate = this.validate.bind(this);
  }
    
  componentDidMount() {
    this.refreshComment();
  }

  refreshComment() {
    this.setState({isOpened: false}); //reclose the full comment box
    CommentDataService.getTopCommentFromArticleId(this.state.articleId) //get the most recent comment
    .then(
        response => {
            console.log(response);
            this.setState({comment: response.data})
        }
    )
  }

  toggleBox() {
    this.setState({isOpened: true}); //open the full comment box
    CommentDataService.getCommentsFromArticleId(this.state.articleId) //get all the comments
        .then(
          response => {
                console.log(response);
                this.setState({comments: response.data})
            }
        )
  }

  validate(values) {
    let errors = {}
    if (!values.commentUserName) { //check that there is a value for username
        errors.commentUserName = 'Enter a UserName'
    } else if (!values.commentText) { //check that there is a value for text
      errors.commentText = 'Enter a Comment'
    }
    return errors
  }

  onSubmit(values) {

    let articleId = this.state.articleId
    let comment = {
        userName: values.commentUserName,
        text: values.commentText,
        likes: 0,
        dislikes: 0
    }

    //Add the comment then refresh the component to show the new comment you've added
    CommentDataService.addNewCommentToArticle(articleId, comment)
      .then(
        response => {
          console.log(response);
          values.commentUserName = '';
          values.commentText = '';
          this.refreshComment();
      })
  }
    
  render() {        
    const { isOpened, commentUserName, commentText} = this.state;

    return (
      <>
      <h5 className="subHeading">Add Comment</h5>
      <Formik //Form for comment submission
        initialValues={{ commentUserName, commentText}}
        onSubmit={this.onSubmit}
        validateOnChange={false}
        validateOnBlur={false}
        validate={this.validate}
        enableReinitialize={true}>
      {
          (props) => (
              <Form className="addComment">
                  <ErrorMessage name="description" component="div"
                      className="alert alert-warning" />
                  <fieldset className="form-group">
                    <label >Display Name</label>
                    <Field className="form-control" type="text" name="commentUserName"  />
                  </fieldset>
                  <fieldset className="form-group">
                    <label >Comment</label>
                    <Field className="form-control" type="text" name="commentText"  />
                  </fieldset>
                  <button className="btn btn-dark" type="submit">Post</button>
              </Form>
          )
      }
      </Formik>
      <LineComponent/>

      <div className="box">
        { !isOpened && ( //Disapears when Display all comments is pressed
          <>
          
          { this.state.comment.id > -100 && ( //Back end returns negative id if there are no comments for an article
            <CommentComponent commentId={this.state.comment.id} comment={this.state.comment}/>
          )}

        <button className="btn btn-block viewCommentsButton" onClick={this.toggleBox}>Display All Comments</button>
        <LineComponent/>
        </>)}
        
        {isOpened && ( //Opens when Display all comments is pressed

          <div className="boxContent">
            <h4 className="subHeading">Comments</h4>
            <LineComponent/>

            { this.state.comments.length !== 0 && //If there are not comments dont display this section
              this.state.comments.map(
                    comment =>
                    <CommentComponent key={comment.id} commentId={comment.id} comment={comment}/>
                  )
            } {this.state.comments.length === 0 && ( //If there are not comments instead display this section
                <div>
                  <p>No Comments Currrently...</p>
                  <p>Add a Comment to Begin the Conversation!</p>
                  <LineComponent/>
                </div>
            )}
          </div>
        )}

      </div>
      </>
    );
  }

}

export default CommentAreaComponent