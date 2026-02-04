import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom"

import { getAnimeByGenre, getPopularAnime } from "../api/animeApi";
import { Options } from "../components/OptionsBar";

export function AnimeGenre() {
    const { genre } = useParams();
    const [anime, setAnime] = useState([]);
    const [loading, setLoading] = useState(true);

    const displayGenre = genre ?? "Popular";
    const page_size = 6;
    useEffect(() => {
        setLoading(true);

        const request = !genre
            ? getPopularAnime(page_size)
            : getAnimeByGenre(genre, page_size);

        request
            .then(res => setAnime(res.data.content))
            .catch(console.error)
            .finally(() => setLoading(false))
        }, [genre]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (anime.length === 0) {
        return <div>No anime found.</div>;
    }
    return (
        
            <div className="background bg-blue-100 rounded-lg">
                <h1 className="text-blue-900 text-3xl text-center ">{displayGenre}</h1>
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