import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom"

import { getAnimeByID } from "../api/animeApi";
import { Options } from "../components/OptionsBar";
import { convertJSTToLocal } from "../utils/timezone";

function Info({ label, value }) {
  if (value === null || value === undefined) return null;

  const formatted =
    typeof value === "string"
      ? value.replace(/_/g, " ").replace(/\b\w/g, c => c.toUpperCase())
      : value;

  return (
    <div className="bg-white/5 p-3 rounded-lg border border-white/10">
      <div className="text-white/60 text-xs uppercase tracking-wide">
        {label}
      </div>
      <div className="font-semibold break-all">
        {formatted}
      </div>
    </div>
  );
}

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


     if (!anime) {
        return <div>Loading...</div>;
    }
    const date = convertJSTToLocal(anime.broadcast.day_of_the_week, anime.broadcast.start_time);

    return ( 
        <Options>
    <div className="bg-white/5 backdrop-blur-md rounded-xl p-8 shadow-xl text-white">

      {/* Title */}
      <h1 className="text-4xl font-bold text-center mb-8">
        {anime.alternative_titles?.en ?? anime.title}
      </h1>

      {/* Top Section */}
      <div className="flex flex-col md:flex-row gap-10">

        {/* Poster */}
        <div className="flex-shrink-0">
          <img
            src={anime.main_picture?.medium}
            alt={anime.title}
            className="rounded-lg shadow-lg w-64 object-cover"
          />
        </div>

        {/* Info Grid */}
        <div className="flex-1">

          <h2 className="text-xl font-semibold mb-4 border-b border-white/10 pb-2">
            Details
          </h2>

          <div className="grid grid-cols-2 md:grid-cols-3 gap-4 text-sm">

            <Info label="Score" value={anime.mean ?? "?"} />
            <Info label="Rank" value={anime.rank ?? "?"} />
            <Info label="Popularity" value={anime.popularity ?? "?"} />
            <Info label="Users" value={anime.num_list_users ?? "?"} />
            <Info label="Scorers" value={anime.num_scoring_users ?? "?"} />
            <Info label="Episodes" value={anime.num_episodes === 0
                ? "?"
                : anime.num_episodes ?? "?"} 
                />
            <Info label="Media Type" value={anime.media_type ?? "?"} />
            <Info label="Status" value={anime.status ?? "?"} />
            <Info label="Source" value={anime.source ?? "?"} />
            <Info label="Rating" value={anime.rating ?? "?"} />
            <Info label="Start Date" value={anime.start_date ?? "?"} />
            <Info label="End Date" value={anime.end_date ?? "?"} />
            <Info label="NSFW" value={anime.nsfw ?? "?"} />
            <Info
              label="Broadcast"
              value={
                date
                  ? date.toLocaleString()
                  : "N/A"
              }
            />

          </div>
        </div>
      </div>

      {/* Genres + Studios */}
      <div className="mt-10 space-y-6">

        {/* Genres */}
        <div>
          <h2 className="text-xl font-semibold mb-3">Genres</h2>
          <div className="flex flex-wrap gap-3">
            {anime.genres.map((g) => (
              <Link
                key={g.id}
                to={`/anime/discover/${g.name}`}
                className="
                  px-4 py-2 rounded-full text-sm font-semibold tracking-wide
                  border border-white/10
                  bg-white/5 hover:bg-gradient-to-r from-purple-500 to-blue-500
                  hover:scale-105 transition-all duration-300
                "
              >
                {g.name}
              </Link>
            ))}
          </div>
        </div>

        {/* Studios */}
        {anime.studios && anime.studios.length > 0 && (
          <div>
            <h2 className="text-xl font-semibold mb-3">Studios</h2>
            <div className="flex flex-wrap gap-3">
              {anime.studios.map((studio) => (
                <span
                  key={studio.id}
                  className="
                    px-4 py-2 rounded-full text-sm font-semibold
                    bg-gradient-to-r from-purple-500 to-blue-500
                    shadow-md
                  "
                >
                  {studio.name}
                </span>
              ))}
            </div>
          </div>
        )}

      </div>

      {/* Synopsis */}
      <div className="mt-10">
        <h2 className="text-xl font-semibold mb-3">Synopsis</h2>
        <p className="text-sm text-white/80 leading-relaxed whitespace-pre-line">
          {anime.synopsis}
        </p>
      </div>

    </div>
  </Options>
);
    

}
