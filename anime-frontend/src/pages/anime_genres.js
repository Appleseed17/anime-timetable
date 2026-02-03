import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom"

import { getAnimeByGenre } from "../api/animeApi";
import { Options } from "../components/OptionsBar";

export function AnimeGenre(){
    const [anime, setAnime] = useState(null);

    var { genre } = useParams();

    useEffect(() => {
        getAnimeByGenre(genre)
            .then(res => setAnime(res.data.content))
            .catch(console.error)
    }, [genre])
    console.log(genre)
    if (!anime){
        return <div>Loading...</div>;
    }

    return (
        Options(
            <>
            <h1>{genre}</h1>
            <div>
                {anime
                .map(a => (
                <div key={a.id} className="anime-card">
                    <div>{a.title}</div>
                    <p>{a.broadcast.start_time}</p>
                    <Link to={`/anime/${a.id}`}> <img src={a.main_picture.medium} alt={a.title} width={120} /></Link>  
                </div>
                ))}
                </div>
            </>
        )
        )
        

}