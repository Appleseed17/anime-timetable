import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { getSeasonalAnime } from "../api/animeApi";
import { Options } from "../components/OptionsBar";
import { convertJSTToLocal } from "../utils/timezone";


export function Schedule() {
  const [anime, setAnime] = useState([]);

  // Fetch seasonal anime once
  useEffect(() => {
    getSeasonalAnime()
      .then((res) => setAnime(res.data.content || res.data))
      .catch(console.error);
  }, []);

  const DAYS = ["SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"];

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
        localDay: localDate.toLocaleDateString(undefined, { weekday: "long" }).toUpperCase(),
        localTime: localDate.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" }),
      };
    })
    .filter(Boolean); // remove nulls

    console.log(processedAnime)
    
  
  const animeByDay = DAYS.reduce((acc, day) => {
    acc[day] = processedAnime
      .filter((a) => a.localDay === day)
      .sort((a, b) => a.localDate - b.localDate); 
    return acc;
  }, {});
  console.log(animeByDay)

  return (
    <Options>
      <div className="py-8 px-6 max-w-7xl mx-auto">
        <div className="grid grid-cols-7 gap-6">
          {DAYS.map((day) => (
            <div key={day} className="text-center font-bold text-lg mb-4">
              {day}
            </div>
          ))}

          {(() => {
            const maxRows = Math.max(...Object.values(animeByDay).map((arr) => arr.length));

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
    </Options>
  );
}