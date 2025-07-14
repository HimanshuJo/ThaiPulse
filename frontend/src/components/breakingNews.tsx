import React, { useMemo } from "react";
import type { Article } from "../services/newsService";
import "../resources/breakingNews.css";

interface BreakingNewsProps {
  articles: Article[];
}

const BreakingNews: React.FC<BreakingNewsProps> = ({ articles }) => {
  const breakingArticle = useMemo(() => {
    if (!articles || articles.length === 0) return null;
    const randomIndex = Math.floor(Math.random() * articles.length - 1);
    return articles[randomIndex];
  }, [articles]);

  if (!breakingArticle) return null;

  return (
    <div className="breaking-news-container">
      <a
        href={breakingArticle.link}
        target="_blank"
        rel="noopener noreferrer"
        className="breaking-news-link"
      >
        <img
          src={breakingArticle.image}
          alt={breakingArticle.title}
          className="breaking-news-image"
        />
        <div className="breaking-news-text">
          <h2 className="breaking-news-title">{breakingArticle.title}</h2>
          <p className="breaking-news-source">{breakingArticle.source}</p>
        </div>
      </a>
    </div>
  );
};

export default BreakingNews;
