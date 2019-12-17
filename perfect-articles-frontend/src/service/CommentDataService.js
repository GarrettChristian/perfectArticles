import axios from 'axios'

const PERFECT_COMMENT_API_URL = 'http://localhost:8080'
const COMMENT = 'comment'

class CommentDataService {

    getCommentsFromArticleId(idArticle) {
        return axios.get(`${PERFECT_COMMENT_API_URL}/${COMMENT}/${idArticle}/id_article`)
    }

    getTopCommentFromArticleId(idArticle) {
        return axios.get(`${PERFECT_COMMENT_API_URL}/${COMMENT}/top/${idArticle}/id_article`)
    }

    getCommentById(id) {
        return axios.get(`${PERFECT_COMMENT_API_URL}/${COMMENT}/${id}/id`)
    }

    addNewCommentToArticle(idArticle, comment) {
        return axios.post(`${PERFECT_COMMENT_API_URL}/${COMMENT}/comment/${idArticle}/id_article/`, comment)
    }

    updateLikesDislikesComment(id, likeDislikes, value) {
        return axios.post(`${PERFECT_COMMENT_API_URL}/${COMMENT}/${id}/id/${likeDislikes}/like_dislikes/${value}/value`)
    }
}

export default new CommentDataService()