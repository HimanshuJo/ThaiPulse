import React, { useEffect, useState } from 'react';
import axios from 'axios';

type Article = {
  title: string;
  source: string;
  link: string;
  image?: string;
};

const NewsList: React.FC = () => {
  const [articles, setArticles] = useState<Article[]>([]);

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const response = await axios.get<Article[]>('http://localhost:8080/news');
        setArticles(response.data);
      } catch (error) {
        console.error('Error fetching articles:', error);
      }
    };
    fetchArticles();
  }, []);

return (
    <div style={{ padding: '20px' }}>
        <h1
          style={{
            fontFamily: `'Playfair Display', 'Times New Roman', serif`,
            fontSize: '72px',
            fontWeight: 900,
            background: 'linear-gradient(to right, #8B0000, #002147, #8B0000)',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent',
            letterSpacing: '-1.5px',
            lineHeight: '1.1',
            marginBottom: '16px',
            textAlign: 'center',
            textShadow: '2px 2px 4px rgba(0,0,0,0.25)',
          }}
        >
          THAI PULSE
        </h1>
      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(3, 1fr)',
          gap: '24px',
        }}
      >
        {articles.map((article, index) => (
          <div
            key={index}
            style={{
              border: '1px solid #ccc',
              padding: '12px',
              borderRadius: '8px',
              textAlign: 'center',
              boxShadow: '0 2px 6px rgba(0,0,0,0.1)',
              transition: 'all 0.3s ease',
              backgroundColor: 'white',
              cursor: 'pointer',
            }}
            onMouseEnter={(e) => {
              const target = e.currentTarget;
              target.style.backgroundColor = '#e6f4ff'; // light blue
              target.style.boxShadow = '0 4px 12px rgba(0, 0, 0, 0.15)';
              target.style.transform = 'translateY(-4px)';
            }}
            onMouseLeave={(e) => {
              const target = e.currentTarget;
              target.style.backgroundColor = 'white';
              target.style.boxShadow = '0 2px 6px rgba(0,0,0,0.1)';
              target.style.transform = 'translateY(0)';
            }}
          >
            <p style={{ fontSize: '12px', color: '#666', margin: '0 0 8px 0' }}>
              {article.source}
            </p>
            <a
              href={article.link}
              target="_blank"
              rel="noopener noreferrer"
              style={{ textDecoration: 'none', color: 'inherit' }}
            >
              <img
                src={article.image}
                alt={article.title}
                style={{
                  width: '100%',
                  height: 'auto',
                  objectFit: 'cover',
                  borderRadius: '4px',
                  marginBottom: '10px',
                }}
              />
              <h3 style={{ fontSize: '16px', fontWeight: 'bold' }}>{article.title}</h3>
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NewsList;
