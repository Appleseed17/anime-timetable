import { useEffect, useState } from "react";
import { Link } from "react-router-dom"

import { getGenres } from "../api/animeApi";

export function AnimeGenres() {
    const [genres, setGenres] = useState(null);
    const [expanded, setExpanded] = useState(false);


        useEffect(() => {
            getGenres()
                .then(res => setGenres(res.data))
                .catch(console.error)
        }, [])
        
        if (!genres){
            return <div>Loading...</div>;
        }

    const visibleGenres = expanded ? genres : genres.slice(0,8);

    return (
            <div className="grid grid-col-1 bg-blue-100 rounded-lg text-blue-900 font-semibold ">
            {visibleGenres.map(
                g => (
                    <div className="hover:bg-blue-300 transition inline-flex items-center justify-center">
                        <Link to={`/anime/genres/${g.name}`}>{g.name} ({g.count})</Link>
                    </div>
                )
            )}

         <button onClick={() => setExpanded(!expanded)} className="hover:bg-blue-300 transition">
            {expanded ? "Show Less" : "Show More"}
         </button>
            </div>
    )


}