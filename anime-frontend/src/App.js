import { Routes, Route } from "react-router-dom";

import { Schedule } from "./pages/schedule.js"
import { AnimeInfo } from "./pages/anime.js"
import { AnimeGenres } from "./pages/anime_genres.js";


function App() {
    return (

        <Routes>
            <Route path="/schedule" element={<Schedule />} />
            <Route path="/anime/:id" element={<AnimeInfo />} />
            <Route path="/anime/genre/:genre" element={<AnimeGenres />} />

            {/* Ongoing anime rankings, possibly add sort by genre etc */}
        </Routes>



    )

}


export default App