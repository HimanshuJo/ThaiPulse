import React, { useEffect, useState } from "react";
import Home from "../pages/pattayaSoi6Home";
import articlesData from "../../public/pattayaSoi6/articles.json";

export type Article = {
  title: string;
  image: string;
  source: string;
  link?: string;
  description?: string;
};

const PattayaSoi6Service: React.FC = () => {
  const [articles, setArticles] = useState<Article[]>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(1);

  useEffect(() => {
    setArticles(articlesData);
    setTotalPages(1);
  }, []);

  const handleNext = () => {
    if (page < totalPages - 1) setPage((prev) => prev + 1);
  };

  const handlePrevious = () => {
    if (page > 0) setPage((prev) => prev - 1);
  };

  return (
    <Home
      articles={articles}
      onNext={handleNext}
      onPrevious={handlePrevious}
      currentPage={page}
      totalPages={totalPages}
    />
  );
};

export default PattayaSoi6Service;