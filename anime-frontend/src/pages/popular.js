import { useEffect, useState } from "react";
import { Link } from "react-router-dom"

import { getPopularPage } from "../api/animeApi";
import { Options } from "../components/OptionsBar";

function PodiumCard({ anime, place }) {
  const isFirst = place === 1;

  const medal =
    place === 1 ? "🥇"
    : place === 2 ? "🥈"
    : "🥉";

  return (
    <Link
      to={`/anime/${anime.id}`}
      className={`group relative transition-transform hover:scale-105 ${
        isFirst ? "w-80" : "w-64"
      }`}
    >
      <div className="bg-white/5 backdrop-blur-md rounded-xl shadow-xl border border-white/10 overflow-hidden">

        {/* Image */}
        <img
          src={anime.main_picture?.large || anime.main_picture?.medium}
          alt={anime.title}
          className={`w-full object-cover ${
            isFirst ? "h-96" : "h-80"
          }`}
        />

        {/* Content */}
        <div className="p-4 text-center space-y-3">

          <div className="text-3xl">{medal}</div>

          <div className="font-bold text-lg leading-snug break-words">
            {anime.alternative_titles?.en ?? anime.title}
          </div>

          <div className="inline-block px-4 py-1 rounded-full bg-gradient-to-r from-purple-500 to-blue-500 text-sm font-semibold shadow-md">
            ⭐ {anime.mean ?? "N/A"}
          </div>

        </div>
      </div>
    </Link>
  );
}

export function PopularAnime() {

    const [anime, setAnime] = useState(null);

    useEffect(() => {
        getPopularPage()
            .then(res => setAnime(res.data))
            .catch(console.error)
    }, [])
    
    if (!anime){
        return <div>Loading...</div>;
    }
 const [first, second, third] = anime
    return (
        <Options>
        <div className="text-white p-8">

            <h1 className="text-4xl font-bold text-center mb-16">
            🏆 Top Rated Anime
            </h1>

            <div className="flex flex-col md:flex-row justify-center items-end gap-8">

            {/* 🥈 Second Place */}
            {second && (
                <PodiumCard anime={second} place={2} />
            )}

            {/* 🥇 First Place */}
            {first && (
                <PodiumCard anime={first} place={1} />
            )}

            {/* 🥉 Third Place */}
            {third && (
                <PodiumCard anime={third} place={3} />
            )}

            </div>
        </div>
        </Options>
    );
    }
   