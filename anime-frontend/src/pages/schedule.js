import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { getSeasonalAnime } from "../api/animeApi";
import { Options } from "../components/OptionsBar";


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
    Options(
      <div className="flex p-10"> 
        <div className="grid grid-cols-7">
      {Object.entries(DAY_TO_COLUMN).map(([day, col]) => (
        <div key={day} className="flex flex-col">
          <h3 className="flex justify-center items-center">{day}</h3>
          {anime
            .filter(a => a.broadcast.day_of_the_week === day)
            .map(a => (
              <div key={a.id} className="flex flex-wrap flex-col justify-center items-center background bg-black bg-opacity-20">
                <div>{a.title}</div>
                <p>{a.broadcast.start_time}</p>
                <Link to={`/anime/${a.id}`}> <img className="w-52 h-60" src={a.main_picture.medium} alt={a.title} /></Link>
                <p>{a.broadcast.day_of_the_week}</p>

                
                  </div>
                ))}
            </div>
          ))}
        </div>
      </div>
      )
  );

}
