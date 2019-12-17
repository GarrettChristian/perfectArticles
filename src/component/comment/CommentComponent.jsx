import React, { Component } from 'react';
import LineComponent from '../LineComponent';
import CommentDataService from '../../service/CommentDataService';
import "./CommentStyle.css"

class CommentComponent extends Component {

  constructor(props) {
      super(props);
      this.state = {
        comment: this.props.comment,
        commentId: this.props.commentId,
        liked: false,
        disliked: false,
        likedColor: 'black',
        dislikedColor: 'black'
      };
      this.refreshComment = this.refreshComment.bind(this);
      this.refreshCommentAfterLike = this.refreshCommentAfterLike.bind(this);
  }
    
  componentDidMount() {
    this.refreshComment()
  }

  componentDidUpdate(prevProps) {
    if (this.props.commentId !== prevProps.commentId) { //refresh for an added comment
      this.refreshComment()
    }
  }

  refreshComment() {
    this.setState({comment: this.props.comment});
    this.setState({commentId: this.props.commentId})
  }

  refreshCommentAfterLike() {
    CommentDataService.getCommentById(this.state.commentId) //Gets new comment for live reload when liked
    .then(
        response => {
            console.log(response);
            this.setState({comment: response.data})
        }
    )
  }

  likedComment(comment) {

    let likes = "LIKES" // option for likes

    if (!this.state.disliked) { //cannot select dislike if like is selected
      if (this.state.liked) { //cannot double like something
        CommentDataService.updateLikesDislikesComment(comment.id, likes, -1) //unlike
          .then(() => {
            this.setState({liked: false});
            this.setState({likedColor: 'black'});
            this.refreshCommentAfterLike();
          })
      } else {
        CommentDataService.updateLikesDislikesComment(comment.id, likes, 1)
          .then(() => {
            this.setState({liked: true});
            this.setState({likedColor: 'red'});
            this.refreshCommentAfterLike();
          })
      }
    }
  }

  dislikedComment(comment) {

    let likes = "DISLIKES" // option for dislikes

    if (!this.state.liked) { //cannot select like if dislike is selected
      if (this.state.disliked) { //cannot double dislike something
        CommentDataService.updateLikesDislikesComment(comment.id, likes, -1) //undislike
          .then(() => {
            this.setState({disliked: false});
            this.setState({dislikedColor: 'black'});
            this.refreshCommentAfterLike();
            })
      } else {
        CommentDataService.updateLikesDislikesComment(comment.id, likes, 1)
          .then(() => {
            this.setState({disliked: true});
            this.setState({dislikedColor: 'red'});
            this.refreshCommentAfterLike();
          })
      }
    }
  }
    
  render() {
    return (
        <div className="contianer displayComment">
          <div className="commentHeader">
              <p className="commentUserName">{this.state.comment.userName}</p>
              <p className="commentDate">posted: {this.state.comment.date}</p>
          </div>
          <p className="commentText">{this.state.comment.text}</p>
          <div className="commentButtons">
              <p className="likeDislikeButton" style={{color: this.state.likedColor}} onClick={
                () => this.likedComment(this.state.comment)}>Like</p> 
              <p className="likeDislike"> {this.state.comment.likes}</p>
              <p className="likeDislikeButton" style={{color: this.state.dislikedColor}} onClick={
                () => this.dislikedComment(this.state.comment)}>Dislike</p>
              <p className="likeDislike"> {this.state.comment.dislikes}</p>
          </div>
          <LineComponent/>
        </div>
    );
  }

}

export default CommentComponent