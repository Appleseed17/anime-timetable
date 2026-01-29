import { Routes, Route } from "react-router-dom";

import { Schedule } from "./pages/schedule.js"
import { AnimeInfo } from "./pages/anime.js"
import { AnimeGenre } from "./pages/anime_genres.js";
import { AnimeGenres } from "./pages/genres.js";
import { PopularAnime } from "./pages/popular.js";


function App() {
    return (

        <Routes>
            <Route path="/schedule" element={<Schedule />} />
            <Route path="/anime/genres" element={<AnimeGenres />} />
            <Route path="/anime/genres/:genre" element={<AnimeGenre />} />
            <Route path="/anime/popular" element={<PopularAnime />} />  
            <Route path="/anime/:id" element={<AnimeInfo />} />
            
            {/* Ongoing anime rankings, possibly add sort by genre etc */}
        </Routes>



    )

}


export default App