import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getAnimeByGenre, getPopularAnime } from "../api/animeApi";

export function AnimeGenre({ genre }) {
  const [anime, setAnime] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const page_size = 8;
  const displayGenre = genre ?? "Popular";


  useEffect(() => {
    setPage(0); // reset page when genre changes
    }, [genre]);


  useEffect(() => {
    setLoading(true);

    const request = !genre
      ? getPopularAnime(page, page_size)
      : getAnimeByGenre(genre, page, page_size);

    request
      .then((res) => {
        console.log(res.data)
        setAnime(res.data.content || []);
        setTotalPages(res.data.totalPages || 1); // depends on your API
      })
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [genre, page]);

  if (loading) return <div>Loading...</div>;
  if (anime.length === 0) return <div>No anime found.</div>;

  return (
    <div className="bg-white/5 backdrop-blur-md rounded-lg p-4 shadow-lg">
        <h1 className="text-white text-3xl text-center mb-4 font-semibold">{displayGenre}</h1>

        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
        {anime.map((a) => (
            <div
            key={a.id}
            className="group relative rounded-lg overflow-hidden shadow-md bg-white/10 hover:shadow-2xl transition transform hover:-translate-y-1"
            >
            <Link to={`/anime/${a.id}`}>
                <img
                src={a.main_picture.medium}
                alt={a.alternative_titles.en ? a.alternative_titles.en : a.title}
                className="w-full h-44 object-cover rounded-t-lg"
                />
            </Link>

            {/* Hover Overlay */}
            <div className="absolute inset-0 bg-black/60 opacity-0 group-hover:opacity-100 flex flex-col items-center justify-center text-white text-center p-2 transition">
                <div className="font-semibold text-sm">{a.alternative_titles.en ? a.alternative_titles.en : a.title}</div>
                {a.num_episodes && <div className="text-xs mt-1">Episodes: {a.num_episodes}</div>}
                {a.mean && <div className="text-xs">Score: {a.mean}</div>}
            </div>

            <div className="p-2 text-center font-semibold text-white">
                {a.alternative_titles.en ? a.alternative_titles.en : a.title}
            </div>
            </div>
        ))}
        </div>

        {/* Pagination */}
        {totalPages > 1 && (
        <div className="flex justify-center gap-2 mt-4">
            <button
            className="px-3 py-1 bg-gradient-to-r from-purple-500 to-blue-500 text-white rounded hover:scale-105 shadow-md disabled:opacity-50 transition-all"
            disabled={page === 0}
            onClick={() => setPage((p) => Math.max(p - 1, 0))}
            >
            Previous
            </button>
            <span className="px-2 py-1 text-white font-semibold">
            Page {page + 1} / {totalPages}
            </span>
            <button
            className="px-3 py-1 bg-gradient-to-r from-purple-500 to-blue-500 text-white rounded hover:scale-105 shadow-md disabled:opacity-50 transition-all"
            disabled={page === totalPages - 1}
            onClick={() => setPage((p) => Math.min(p + 1, totalPages - 1))}
            >
            Next
            </button>
        </div>
        )}
    </div>
    );
}