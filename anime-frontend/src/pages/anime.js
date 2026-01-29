import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom"

import { getAnimeByID } from "../api/animeApi";


export function AnimeInfo() {
    const [anime, setAnime] = useState(null);

    var { id } = useParams();
    console.log(id)
    id = Number(id)
    console.log("ID:" + id)

    useEffect(() => {
        getAnimeByID(id)
            .then(res =>  {
                 console.log("API RESPONSE:", res.data);
                 setAnime(res.data)
    })
            .catch(console.error)
    }, [id] )

    console.log(anime)



     if (!anime) {
        return <div>Loading...</div>;
    }


    return ( 
        <div>
        <h1>{anime.title}</h1> 
            <img src = {anime.main_picture.medium} alt={id}/>
            <Link to="/schedule">Home</Link>
            {anime.genres.map(g => (
                <p>
                <Link to={`/anime/genres/${g.name}`} key={g.id}>{g.name}</Link>
                </p>
            )
            )}
            </div>

    )

}
