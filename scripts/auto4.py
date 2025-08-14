import os

# Template for the service TSX file
service_template = '''
import React, {{useEffect, useState}} from 'react';
import axios from 'axios';
import Home from '../pages/{lower}Home';

export type Article = {{
    title: string;
    source: string;
    link: string;
    image?: string;
}}

const {name}Service: React.FC = () => {{
    const [articles, setArticles] = useState<Article[]>([]);
    const [page, setPage] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(1);

    const fetchArticles = async (pageNumber: number) => {{
        try {{
            const response = await axios.get(`http://localhost:8080/{lower}-news?page=${{pageNumber}}&size=500`);
            const data = response.data;
            setArticles(data.content || data);
            setTotalPages(data.totalPages || 1);
        }} catch (error) {{
            console.error("Error fetching articles: ", error);
        }}
    }};

    useEffect(() => {{
        fetchArticles(page);
    }}, [page]);

    const handleNext = () => {{
        if (page < totalPages - 1) setPage(prev => prev + 1);
    }};

    const handlePrevious = () => {{
        if (page > 0) setPage(prev => prev - 1);
    }};

    return (
        <Home
            articles={{articles}}
            onNext={{handleNext}}
            onPrevious={{handlePrevious}}
            currentPage={{page}}
            totalPages={{totalPages}}
        />
    );
}};

export default {name}Service;
'''

# Template for the home TSX file
home_template = '''
import React from "react";
import type {{ Article }} from "../services/{lower}Service";
import BreakingNews from "../components/breakingNews";
import Navbar from "../components/navbar";
import Footer from "../components/footer";
import "../resources/home.css";

interface HomeProps {{
  articles: Article[];
  onNext: () => void;
  onPrevious: () => void;
  currentPage: number;
  totalPages: number;
}}

const {name}Home: React.FC<HomeProps> = ({{
  articles,
  onNext,
  onPrevious,
  currentPage,
  totalPages,
}}) => {{
  return (
    <div className="container">
      <Navbar />
      <h1 className="header">THAI PULSE</h1>
      <BreakingNews articles={{articles}} />
      <div className="grid">
        {{articles.map((article, index) => (
          <div className="card" key={{index}}>
            <p className="source">{{article.source}}</p>
            <a
              href={{article.link}}
              target="_blank"
              rel="noopener noreferrer"
              className="article-link"
            >
              {{article.image && (
                <img
                  src={{article.image}}
                  alt={{article.title}}
                  className="article-image"
                />
              )}}
              <h3 className="article-title">{{article.title}}</h3>
            </a>
          </div>
        ))}}
      </div>
      <div className="pagination">
        <button onClick={{onPrevious}} disabled={{currentPage === 0}}>
          Previous
        </button>
        <span>
          Page&nbsp;{{currentPage + 1}} of {{totalPages}}
        </span>
        <button onClick={{onNext}} disabled={{currentPage === totalPages - 1}}>
          Next
        </button>
      </div>
      <Footer />
    </div>
  );
}};

export default {name}Home;
'''

services = [
    "BicycleThailand",
    "ThaiCapitalist",
    "AboutThailandLiving",
    "DaveTheRavesThailand",
    "Thaifoodmaster",
    "ThailandBail",
    "TheSilomer",
    "PattayaPI",
    "BudgetCatcher",
    "ThaiLawyers",
    "MeanderingTales",
    "LifestyleInThailand",
    "ThatBangkokLife",
    "ThinglishLifestyle",
    "FashionGalleria",
]

output_dir_services = "./services"
output_dir_pages = "./pages"

os.makedirs(output_dir_services, exist_ok=True)
os.makedirs(output_dir_pages, exist_ok=True)

for service_name in services:
    lower_name = service_name[0].lower() + service_name[1:]

    # Generate service file
    service_code = service_template.format(name=service_name, lower=lower_name)
    service_path = os.path.join(output_dir_services, f"{lower_name}Service.tsx")
    with open(service_path, "w", encoding="utf-8") as f:
        f.write(service_code.strip())

    # Generate home file
    home_code = home_template.format(name=service_name, lower=lower_name)
    home_path = os.path.join(output_dir_pages, f"{lower_name}Home.tsx")
    with open(home_path, "w", encoding="utf-8") as f:
        f.write(home_code.strip())

print("TSX files generated successfully!")
