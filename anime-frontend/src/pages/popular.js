import { useEffect, useState } from "react";
import { Link } from "react-router-dom"

import { getPopularAnime } from "../api/animeApi";
import { Options } from "../components/OptionsBar";

export function PopularAnime() {

    const [anime, setAnime] = useState(null);

    useEffect(() => {
        getPopularAnime(2)
            .then(res => setAnime(res.data.content))
            .catch(console.error)
    }, [])
    
    if (!anime){
        return <div>Loading...</div>;
    }

    return (
        Options(
            <div>
                
                {anime.map(
                    (a, index) => (
                        <div key={a.id}>
                            <p>rank {index + 1}</p>
                        <div>{a.title}</div>
                        <img src={a.main_picture.medium} alt={a.title} width={120} />
                        </div>
                    )
                )}

            </div>
        )
    )



}