import { Routes, Route } from "react-router-dom";

import { Schedule } from "./pages/schedule.js"
import { AnimeInfo } from "./pages/anime.js"

import { AnimeDiscover } from "./pages/discover.js";
import { PopularAnime } from "./pages/popular.js";



function App() {
    return (
        <Routes>
            <Route path="/anime/schedule" element={<Schedule />} />
            <Route path="/anime/discover/Popular" element={<AnimeDiscover />} />
            <Route path="/anime/discover/:genre" element={<AnimeDiscover />} />
            <Route path="/anime/popular" element={<PopularAnime />} />  
            <Route path="/anime/:id" element={<AnimeInfo />} />
            
            {/* Ongoing anime rankings, possibly add sort by genre etc */}
        </Routes>
    )
}


export default App