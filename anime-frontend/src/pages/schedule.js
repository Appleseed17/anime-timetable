import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { getSeasonalAnime } from "../api/animeApi";

import "../static/grid.css"


export function Schedule() {
  const [anime, setAnime] = useState([]);

  useEffect(() => {
    getSeasonalAnime()
      .then(res => setAnime(res.data))
      .catch(console.error);
  }, []);

  const DAY_TO_COLUMN = {
    SUNDAY: 1,
    MONDAY: 2,
    TUESDAY: 3,
    WEDNESDAY: 4,
    THURSDAY: 5,
    FRIDAY: 6,
    SATURDAY: 7,
    
};
if (anime) {
console.log(anime)
}

  return (
    <div className="schedule-grid">
  {Object.entries(DAY_TO_COLUMN).map(([day, col]) => (
    <div key={day} className="day-column">
      <h3>{day}</h3>
      {anime
        .filter(a => a.broadcast.day_of_the_week === day)
        .map(a => (
          <div key={a.id} className="anime-card">
            <div>{a.title}</div>
            <p>{a.broadcast.start_time}</p>
            <Link to={`/anime/${a.id}`}> <img src={a.main_picture.medium} alt={a.title} width={120} /></Link>

            
          </div>
        ))}
    </div>
  ))}
</div>
  );
}
