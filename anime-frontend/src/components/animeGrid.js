import { useEffect, useState } from "react";
import { getSeasonalAnime } from "../api/animeApi";

export function GridView() {
    const [anime, setAnime] = useState([]);
    
      useEffect(() => {
        getSeasonalAnime()
          .then(res => setAnime(res.data))
          .catch(console.error);
      }, []);

      return 


}






