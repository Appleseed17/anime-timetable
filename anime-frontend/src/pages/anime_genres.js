import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom"

import { getAnimeByGenre } from "../api/animeApi";
import { Options } from "../components/OptionsBar";

export function AnimeGenre(genre){
    const [anime, setAnime] = useState(null);

    // var { genre } = useParams();

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
        
            <div className="background bg-blue-100 rounded-lg">
                <h1 className="text-blue-900 text-3xl text-center ">{genre}</h1>
                <div className="grid grid-cols-2 gap-4 p-4 ">
                    {anime
                    .map(a => (
                    <div key={a.id} className="background bg-blue-200 rounded-lg">
                        <div className="flex justify-center">{a.title}</div>
                        <Link to={`/anime/${a.id}`} className="flex justify-center"> <img src={a.main_picture.medium} alt={a.title} width={120} /></Link>  
                    </div>
                    ))}
                </div>
            </div>

        )
        

}