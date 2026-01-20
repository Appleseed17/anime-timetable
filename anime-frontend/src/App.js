import { Routes, Route } from "react-router-dom";

import { Schedule } from "./pages/schedule.js"
import { AnimeInfo } from "./pages/anime.js"


function App() {
    return (

        <Routes>
            <Route path="/schedule" element={<Schedule />} />
            <Route path="/anime/:id" element={<AnimeInfo />} />
        </Routes>



    )

}


export default App