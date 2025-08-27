import React from "react";
import type { Article } from "../services/pattayaNewsService";
import BreakingNews from "../components/breakingNews";
import Navbar from "../components/navbar";
import Footer from "../components/footer";
import "../resources/home.css";

interface HomeProps {
  articles: Article[];
  onNext: () => void;
  onPrevious: () => void;
  currentPage: number;
  totalPages: number;
}

const PattayaNewsHome: React.FC<HomeProps> = ({
  articles,
  onNext,
  onPrevious,
  currentPage,
  totalPages,
}) => {
  return (
    <div className="container">
      <Navbar />
      <h1 className="header">THAI PULSE</h1>
      <BreakingNews articles={articles} />
      <div className="grid">
        {articles.map((article, index) => (
          <div className="card" key={index}>
            <p className="source">{article.source}</p>
            <a
              href={article.link}
              target="_blank"
              rel="noopener noreferrer"
              className="article-link"
            >
              <img
                src={article.image}
                alt={article.title}
                className="article-image"
                loading="lazy"
              />
              <h3 className="article-title">{article.title}</h3>
            </a>
          </div>
        ))}
      </div>
      <div className="pagination">
        <button onClick={onPrevious} disabled={currentPage === 0}>
          Previous
        </button>
        <span>
          Page&nbsp;{currentPage + 1} of {totalPages}
        </span>
        <button onClick={onNext} disabled={currentPage === totalPages - 1}>
          Next
        </button>
      </div>
      <Footer />
    </div>
  );
};

export default PattayaNewsHome;