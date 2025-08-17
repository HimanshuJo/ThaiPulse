import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Home from '../pages/aboutThailandLivingHome';

export type Article = {
    title: string;
    source: string;
    link: string;
    image?: string;
}

const AboutThailandLivingService: React.FC = () => {
    const [articles, setArticles] = useState<Article[]>([]);
    const [page, setPage] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(1);

    const fetchArticles = async (pageNumber: number) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/aboutThailandLiving?page=${pageNumber}&size=500`);
            const data = response.data;
            setArticles(data.content || data);
            setTotalPages(data.totalPages || 1);
        } catch (error) {
            console.error("Error fetching articles: ", error);
        }
    };

    useEffect(() => {
        fetchArticles(page);
    }, [page]);

    const handleNext = () => {
        if (page < totalPages - 1) setPage(prev => prev + 1);
    };

    const handlePrevious = () => {
        if (page > 0) setPage(prev => prev - 1);
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

export default AboutThailandLivingService;