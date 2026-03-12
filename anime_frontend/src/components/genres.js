import { useEffect, useState } from "react";
import { NavLink, useLocation } from "react-router-dom"

import { getGenres } from "../api/animeApi";

export function AnimeGenres() {
  const [genres, setGenres] = useState(null);
  const [expanded, setExpanded] = useState(false);
  const location = useLocation();

  useEffect(() => {
    getGenres()
      .then(res => setGenres(res.data))
      .catch(console.error);
  }, []);

  if (!genres) return <div>Loading...</div>;

  // For desktop: only show first 8 if not expanded
  const visibleGenres = expanded ? genres : genres.slice(0, 8);

  return (
    <>
      {/* Desktop sidebar */}
      <div className="hidden md:flex flex-col bg-white/5 backdrop-blur-md rounded-lg w-48 p-2 shadow-lg max-h-[80vh] overflow-y-auto gap-2">
        {/* Popular */}
        <NavLink
          to="/anime/discover/Popular"
          className={({ isActive }) => {
            const active = location.pathname.includes("Popular");
            return `
              px-3 py-2 rounded-full text-sm font-semibold tracking-wide transition-all duration-300
              border border-white/10
              ${active
                ? "bg-gradient-to-r from-purple-500 to-blue-500 shadow-lg shadow-purple-500/40 scale-105 text-white"
                : "text-white hover:bg-white/10 hover:scale-105"
              }
            `;
          }}
        >
          Popular
        </NavLink>

        {/* Genre list */}
        {visibleGenres.map((g) => {
          const isActive = location.pathname === `/anime/discover/${g.name}`;
          return (
            <NavLink
              key={g.name}
              to={`/anime/discover/${g.name}`}
              className={`
                px-3 py-2 rounded-full text-sm font-semibold tracking-wide transition-all duration-300
                border border-white/10
                ${isActive
                  ? "bg-gradient-to-r from-purple-500 to-blue-500 shadow-lg shadow-purple-500/40 scale-105 text-white"
                  : "text-white hover:bg-white/10 hover:scale-105"
                }
              `}
            >
              {g.name} ({g.count})
            </NavLink>
          );
        })}

        {/* Show More / Less */}
        {genres.length > 8 && (
          <button
            onClick={() => setExpanded(!expanded)}
            className="mt-auto w-full px-3 py-2 rounded-full text-sm font-semibold tracking-wide bg-white/5 hover:bg-white/10 transition-all duration-300 border border-white/10"
          >
            {expanded ? "Show Less" : "Show More"}
          </button>
        )}
      </div>

      {/* Mobile horizontal scroll */}
      <div className="flex md:hidden gap-2 overflow-x-auto p-2">
        <NavLink
          to="/anime/discover/Popular"
          className={({ isActive }) => {
            const active = location.pathname.includes("Popular");
            return `
              flex-shrink-0 px-3 py-2 rounded-full text-sm font-semibold tracking-wide transition-all duration-300
              border border-white/10
              ${active
                ? "bg-gradient-to-r from-purple-500 to-blue-500 shadow-lg shadow-purple-500/40 scale-105 text-white"
                : "text-white hover:bg-white/10 hover:scale-105"
              }
            `;
          }}
        >
          Popular
        </NavLink>

        {genres.map((g) => {
          const isActive = location.pathname === `/anime/discover/${g.name}`;
          return (
            <NavLink
              key={g.name}
              to={`/anime/discover/${g.name}`}
              className={`
                flex-shrink-0 px-3 py-2 rounded-full text-sm font-semibold tracking-wide transition-all duration-300
                border border-white/10
                ${isActive
                  ? "bg-gradient-to-r from-purple-500 to-blue-500 shadow-lg shadow-purple-500/40 scale-105 text-white"
                  : "text-white hover:bg-white/10 hover:scale-105"
                }
              `}
            >
              {g.name} ({g.count})
            </NavLink>
          );
        })}
      </div>
    </>
  );
}