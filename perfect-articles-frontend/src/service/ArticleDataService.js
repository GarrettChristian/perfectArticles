import axios from 'axios'

const PERFECT_ARTICLE_API_URL = 'http://localhost:8080'
const ARTICLE = 'article'

class ArticleDataService {
    
    retrieveArticleById(id) {
        return axios.get(`${PERFECT_ARTICLE_API_URL}/${ARTICLE}/${id}/id`)
    }

    retrieveTitles() {
        return axios.get(`${PERFECT_ARTICLE_API_URL}/${ARTICLE}/titles`)
    }

    retrieveArticlesByDate(amount) {
        return axios.get(`${PERFECT_ARTICLE_API_URL}/${ARTICLE}/${amount}/amount`)
    }

    retrieveAllArticles() {
        return axios.get(`${PERFECT_ARTICLE_API_URL}/${ARTICLE}/articles/full`)
    }

    addExampleArticles() {
        return axios.post(`${PERFECT_ARTICLE_API_URL}/${ARTICLE}/articles`)
    }
}

export default new ArticleDataService()