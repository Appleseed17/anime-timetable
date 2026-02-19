import { useParams } from "react-router-dom";
import { Options } from "../components/OptionsBar";
import { AnimeGenres } from "../components/genres";
import { AnimeGenre } from "./anime_genres";

export function AnimeDiscover() {
  const { genre } = useParams(); // undefined or genre name

  return (
    <Options>
      <div className="flex flex-col md:flex-row gap-4 md:gap-8">
        {/* Genre bar */}
        <div className="flex md:flex-none md:h-[80vh] overflow-x-auto md:overflow-y-auto">
          <AnimeGenres />
        </div>

        {/* Main content */}
        <div className="flex-1">
          <AnimeGenre genre={genre} />
        </div>
      </div>
    </Options>
  );
}