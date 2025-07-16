import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PattayaNews from "../pages/pattayaNews";

export type PattayaArticle={
    title: string;
    description: string;
    link: string;
    imageUrl?: string;
    publishedDate?: string;
};

const PattayaNewsService: React.FC=()=>{
    const [articles, setArticles]=useState<PattayaArticle[]>([]);
    const [page, setPage]=useState<number>(0);
    const [totalPages, setTotalPages]=useState<number>(1);

    const fetchArticles=async(pageNumber: number)=>{
        try{
            const response=await axios.get(
                `http://localhost:8080/pattaya-news?page=${pageNumber}&size=500`
            );
            const data=response.data;
            setArticles(data.content||data);
            setTotalPages(data.totalPages||1);
        } catch(error){
            console.error("Error fetching Pattaya news: ", error);
        }
    };

    useEffect(()=>{
        fetchArticles(page);
    }, [page]);

    const handleNext=()=>{
        if(page<totalPages-1) setPage((prev)=>prev+1);
    };

    const handlePrevious=()=>{
        if(page>0) setPage((prev)=>prev-1);
    };

    return (
        <PattayaNews
            articles={articles}
            onNext={handleNext}
            onPrevious={handlePrevious}
            currentPage={page}
            totalPages={totalPages}
        />
    );
};

export default PattayaNewsService;