import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom"

import { getAnimeByID } from "../api/animeApi";


export function AnimeInfo() {
    const [anime, setAnime] = useState(null);

    var { animeID } = useParams();
    console.log(animeID)
    animeID = Number(animeID)
    console.log("ID:" + animeID)

    useEffect(() => {
        getAnimeByID(animeID)
            .then(res => setAnime(res.data))
            .catch(console.error)
    }, [animeID] )

    console.log(animeID)

    return ( 
        <div>
        <h1>{anime.title}</h1> 
            <img src = {anime.main_picture.medium} alt={animeID}/>
            <Link to="/schedule">Home</Link>
            </div>

    )

}
