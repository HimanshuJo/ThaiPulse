import React, {useEffect, useState} from 'react';
import axios from 'axios';
import PhuketNews from "../pages/phuketNews";

export type PhuketArticle={
    title: string;
    source: string;
    link: string;
    image?: string;
    publishedDate?: string;
};

const API_BASE =
  window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1"
    ? "http://localhost:8080"
    : "https://thaipulsetimes.com";

const PhuketNewsService:React.FC=()=>{
    const [articles, setArticles]=useState<PhuketArticle[]>([]);
    const [page, setPage]=useState<number>(0);
    const [totalPages, setTotalPages]=useState<number>(1);
    const fetchArticles=async(pageNumber:number)=>{
        try{
            const response=await axios.get(
                `${API_BASE}/phuket-news?page=${pageNumber}&size=500`
            );
            const data=response.data;
            setArticles(data.content||data);
            setTotalPages(data.totalPages||1);
        } catch(error){
            console.error("Error fetching Pattaya news: ", error);
        }
    }

    useEffect(()=>{
        fetchArticles(page);
    }, [page]);

    const handleNext=()=>{
        if(page<totalPages-1) setPage((prev)=>prev+1);
    };

    const handlePrevious=()=>{
        if(page>0) setPage((prev)=>prev-1);
    };

    return(
        <PhuketNews
            articles={articles}
            onNext={handleNext}
            onPrevious={handlePrevious}
            currentPage={page}
            totalPages={totalPages}
        />
    );
};

export default PhuketNewsService;