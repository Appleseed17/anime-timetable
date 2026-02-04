import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom"

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
            <div className="grid grid-cols-1 bg-blue-100 rounded-lg text-blue-900 ml-4 font-semibold ">
                <div className="hover:bg-blue-200 transition inline-flex items-center justify-center">
                        <NavLink to="/anime/discover/Popular" className={({ isActive }) =>
              ` ${
                isActive
                  ? "bg-blue-600 text-white border-blue-900 rounded-lg"
                  : ""
              }`
            }>Popular</NavLink>
                    </div>
            
            {visibleGenres.map(
                g => (
                    <div className="hover:bg-blue-200 transition inline-flex items-center justify-center">
                        <NavLink to={`/anime/discover/${g.name}`} className={({ isActive }) =>
              ` ${
                isActive
                  ? "bg-blue-600 text-white border-blue-900 rounded-lg"
                  : ""
              }`
            }>{g.name} ({g.count})</NavLink>
                    </div>
                )
            )}

         <button onClick={() => setExpanded(!expanded)} className="hover:bg-blue-300 transition">
            {expanded ? "Show Less" : "Show More"}
         </button>
            </div>
    )


}