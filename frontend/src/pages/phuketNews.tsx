import React from "react";
import type { PhuketArticle } from "../services/phuketNewsService";
import Navbar from "../components/navbar";
import Footer from "../components/footer";
import "../resources/phuketNews.css";

interface PhuketNewsProps{
    articles: PhuketArticle[];
    onNext:() => void;
    onPrevious:() => void;
    currentPage: number;
    totalPages: number;
}

const PhuketNews: React.FC<PhuketNewsProps>=({
    articles,
    onNext,
    onPrevious,
    currentPage,
    totalPages,
})=>{
    const breaking=articles[0];
    return(
        <div className="phuket-container">
        <Navbar />
            <h1 className="phuket-header">Phuket News</h1>
            {breaking &&(
                <div className="breaking-news_phuket">
                    <img src={breaking.imageUrl} alt={breaking.title} />
                    <div>
                        <h2>{breaking.title}</h2>
                        <p>{breaking.description}</p>
                    </div>
                </div>
            )}

            <div className="news-list">
            {
                articles.slice(1).map
                (
                    (article, index)=>
                    {
                        const wordCount=article.description?.split(/\s+/).length||0;
                        const formattedDate=article.publishedDate
                        ? new Date(article.publishedDate).toLocaleDateString("en-GB")
                        :"";

                        return(
                            <React.Fragment key={index}>
                                <div className="news-card">
                                    <img src={article.imageUrl} alt={article.title} />
                                    <div>
                                        <a href={article.link} target="_blank" rel="noopener noreferrer">
                                            <h3>{article.title}</h3>
                                        </a>
                                        {wordCount>=10?(
                                            <>
                                            <p>{article.description}</p>
                                            <p className="source">{formattedDate}</p>
                                            </>
                                        ):(
                                            <p className="source">{formattedDate}</p>
                                        )}
                                    </div>
                                </div>
                                {index<articles.length-2&&<hr className="news-divider" />}
                            </React.Fragment>
                        );
                    }
                )
            }
            </div>
            <div className="pagination">
                <button onClick={onPrevious} disabled={currentPage===0}>
                    Previous
                </button>
                <span>
                    Page{currentPage+1} of {totalPages}
                </span>
                <button onClick={onNext} disabled={currentPage===totalPages-1}>
                    Next
                </button>
            </div>
            <Footer />
        </div>
    );
};

export default PhuketNews;