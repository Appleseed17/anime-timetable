import { useEffect, useState } from "react";
import { Link } from "react-router-dom"

import { getGenres } from "../api/animeApi";


export function AnimeGenres() {
     const [genre, setGenres] = useState(null);
    
        useEffect(() => {
            getGenres()
                .then(res => setGenres(res.data))
                .catch(console.error)
        }, [])
        
        if (!genre){
            return <div>Loading...</div>;
        }

    return (
        <div>
        {genre.map(
            g => (
                <div>
                    <Link to={`/anime/genres/${g.name}`}>{g.name} ({g.count})</Link>
                </div>
            )
        )}
        </div>
    )


}