import { useEffect, useState } from "react";
import { getSeasonalAnime } from "./api/animeApi";

function App() {
  const [anime, setAnime] = useState([]);

  useEffect(() => {
    getSeasonalAnime()
      .then(res => setAnime(res.data))
      .catch(console.error);
  }, []);

  return (
    <div>
      <h1>Seasonal Anime</h1>
      {anime.map(a => (
        <div key={a.id}>{a.title}
        <img src = {a.main_picture.medium} alt = {a.title}></img></div>
      ))}
    </div>
  );
}

export default App;
