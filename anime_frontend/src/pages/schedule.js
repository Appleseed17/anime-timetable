import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { getSeasonalAnime } from "../api/animeApi";
import { Options } from "../components/OptionsBar";
import { convertJSTToLocal } from "../utils/timezone";


export function Schedule() {
  const [anime, setAnime] = useState([]);
  const DAYS = ["SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"];

  const today = new Date().toLocaleDateString(undefined, {
    weekday: "long",
  }).toUpperCase();

  const [selectedDay, setSelectedDay] = useState(today);

  useEffect(() => {
    getSeasonalAnime()
      .then((res) => setAnime(res.data.content || res.data))
      .catch(console.error);
  }, []);

  const processedAnime = anime
    .map((a) => {
      if (!a.broadcast?.day_of_the_week || !a.broadcast?.start_time) return null;

      const localDate = convertJSTToLocal(
        a.broadcast.day_of_the_week,
        a.broadcast.start_time
      );
      if (!localDate) return null;

      return {
        ...a,
        localDate,
        localDay: localDate
          .toLocaleDateString(undefined, { weekday: "long" })
          .toUpperCase(),
        localTime: localDate.toLocaleTimeString([], {
          hour: "2-digit",
          minute: "2-digit",
        }),
      };
    })
    .filter(Boolean);

  const animeByDay = DAYS.reduce((acc, day) => {
    acc[day] = processedAnime
      .filter((a) => a.localDay === day)
      .sort((a, b) => a.localDate - b.localDate);
    return acc;
  }, {});

  return (
    <Options>
      <div className="w-full py-4 lg:py-6">

        {/* ================= MOBILE ================= */}
        <div className="lg:hidden">

          {/* Day Selector */}
          <div className="relative -mx-3 px-3 overflow-x-auto pb-3 mb-4">
            <div className="flex gap-2">
              {DAYS.map((day) => (
                <button
                  key={day}
                  onClick={() => setSelectedDay(day)}
                  className={`shrink-0 px-3 py-1.5 rounded-full text-xs font-semibold tracking-wide transition
                    ${
                      selectedDay === day
                        ? "bg-gradient-to-r from-purple-500 to-blue-500 text-white"
                        : "bg-white/10 text-white/70 hover:bg-white/20"
                    }`}
                >
                  {day.slice(0, 3)}
                </button>
              ))}
            </div>
          </div>

          {/* Anime List */}
          <div className="space-y-4">
            {animeByDay[selectedDay]?.map((a) => (
              <Link
                key={a.id}
                to={`/anime/${a.id}`}
                className="flex gap-4 bg-white/5 backdrop-blur-md rounded-lg p-3 hover:bg-white/10 transition"
              >
                <img
                    src={a.main_picture.medium}
                    alt={a.title}
                    className="w-16 h-24 object-cover rounded-md flex-shrink-0"
                />
                <div className="flex flex-col justify-between min-w-0">
                  <div className="font-semibold leading-tight text-sm sm:line-clamp-2">
                    {a.alternative_titles.en ? a.alternative_titles.en : a.title}
                  </div>
                  <div className="text-xs text-white/60">
                    {a.localTime}
                  </div>
                </div>
              </Link>
            ))}

            {animeByDay[selectedDay]?.length === 0 && (
              <div className="text-center text-white/50 py-8">
                No anime scheduled
              </div>
            )}
          </div>
        </div>

        {/* ================= DESKTOP ================= */}
        <div className="hidden lg:block">
          <div className="grid grid-cols-7 gap-6">

            {DAYS.map((day) => (
              <div key={day} className="text-center font-bold text-lg mb-4">
                {day}
              </div>
            ))}

            {(() => {
              const maxRows = Math.max(
                ...Object.values(animeByDay).map((arr) => arr.length)
              );

              const rows = [];
              for (let rowIndex = 0; rowIndex < maxRows; rowIndex++) {
                DAYS.forEach((day) => {
                  const animeEntry = animeByDay[day][rowIndex];
                  rows.push(
                    <div key={`${day}-${rowIndex}`} className="flex justify-center">
                      {animeEntry ? (
                        <Link
                          to={`/anime/${animeEntry.id}`}
                          className="group w-40 mb-4"
                        >
                          <div className="relative rounded-lg overflow-hidden shadow-lg">
                            <img
                              src={animeEntry.main_picture.medium}
                              alt={animeEntry.title}
                              className="w-full h-56 object-cover transition group-hover:scale-110"
                            />
                            <div className="absolute inset-0 bg-black/60 opacity-0 group-hover:opacity-100 transition flex items-center justify-center text-sm text-white p-2 text-center">
                              {animeEntry.alternative_titles.en ? animeEntry.alternative_titles.en : animeEntry.title}
                              <br />
                              {animeEntry.localTime}
                            </div>
                          </div>
                        </Link>
                      ) : (
                        <div className="w-40 h-56" />
                      )}
                    </div>
                  );
                });
              }
              return rows;
            })()}
          </div>
        </div>
      </div>
    </Options>
  );
}