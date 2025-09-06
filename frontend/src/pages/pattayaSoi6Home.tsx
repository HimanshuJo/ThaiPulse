import React, { useState } from "react";
import type { Article } from "../services/pattayaSoi6Service";
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

const PattayaSoi6Home: React.FC<HomeProps> = ({
  articles,
  onNext,
  onPrevious,
  currentPage,
  totalPages,
}) => {
  const [selectedArticle, setSelectedArticle] = useState<Article | null>(null);

  return (
    <div className="container">
      <Navbar />
      <h1 className="header">THAI PULSE</h1>
      <div className="grid">
        {articles.map((article, index) => (
          <div
            className="card"
            key={index}
            onClick={() => setSelectedArticle(article)}
          >
            <p className="source">{article.source}</p>
            {article.image && (
              <img
                src={article.image}
                alt={article.title}
                className="article-image"
              />
            )}
            <h3 className="article-title">{article.title}</h3>
          </div>
        ))}
      </div>

      {selectedArticle && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100vw",
            height: "100vh",
            background: "rgba(0,0,0,0.6)",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            zIndex: 1000,
          }}
          onClick={() => setSelectedArticle(null)}
        >
          <div
            style={{
              background: "white",
              padding: "20px",
              borderRadius: "10px",
              maxWidth: "600px",
              width: "90%",
              maxHeight: "80vh",
              overflowY: "auto",
            }}
            onClick={(e) => e.stopPropagation()}
          >
            <h2>{selectedArticle.title}</h2>
            {selectedArticle.image && (
              <img
                src={selectedArticle.image}
                alt={selectedArticle.title}
                style={{
                  width: "100%",
                  borderRadius: "8px",
                  marginBottom: "10px",
                }}
              />
            )}
            <p>{selectedArticle.description}</p>
            <button
              onClick={() => setSelectedArticle(null)}
              style={{
                marginTop: "10px",
                padding: "8px 16px",
                background: "#002147",
                color: "white",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer",
              }}
            >
              Close
            </button>
          </div>
        </div>
      )}

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

export default PattayaSoi6Home;