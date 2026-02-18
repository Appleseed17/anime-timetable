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
      className={`
        group relative transition-transform hover:scale-105
        w-full max-w-xs sm:max-w-sm
        ${isFirst ? "md:max-w-md" : ""}
        w-full max-w-xs sm:max-w-sm md:max-w-sm
      `}
    >
      <div className="bg-white/5 backdrop-blur-md rounded-xl shadow-xl border border-white/10 overflow-hidden">

        {/* Image */}
        <div className="flex justify-center md:justify-start"> 
        <img
          src={anime.main_picture?.large || anime.main_picture?.medium}
          alt={anime.title}
          className={`
            w-full
            aspect-[2/3]
            object-cover
            object-center
            rounded-t-xl
            transition-transform duration-300
            group-hover:scale-105
            
            w-full object-cover
            ${isFirst ? "max-w-sm md:max-w-md" : "max-w-sm"}
          `}
        />
        </div>

        {/* Content */}
        <div className="p-3 sm:p-4 text-center space-y-2 sm:space-y-3">

          <div className="text-2xl sm:text-3xl">{medal}</div>

          <div className="font-bold text-base sm:text-lg leading-snug break-words">
            {anime.alternative_titles?.en ?? anime.title}
          </div>

          <div className="inline-block px-3 py-1 rounded-full bg-gradient-to-r from-purple-500 to-blue-500 text-sm font-semibold shadow-md">
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
      .catch(console.error);
  }, []);

  if (!anime) return <div>Loading...</div>;

  const [first, second, third] = anime;

  return (
    <Options>
      <div className="text-white px-4 sm:px-6 py-8 max-w-6xl mx-auto">

        <h1 className="text-3xl sm:text-4xl font-bold text-center mb-10 sm:mb-16">
          🏆 Top Rated Anime
        </h1>

        <div
          className="
            flex flex-col gap-10
            md:flex-row md:items-end md:justify-center md:gap-8
          "
        >
          {/* On mobile: first → second → third */}
          {/* On desktop: second → first → third */}

          <div className="order-2 md:order-1">
            {second && <PodiumCard anime={second} place={2} />}
          </div>

          <div className="order-1 md:order-2">
            {first && <PodiumCard anime={first} place={1} />}
          </div>

          <div className="order-3">
            {third && <PodiumCard anime={third} place={3} />}
          </div>
        </div>
      </div>
    </Options>
  );
}
   