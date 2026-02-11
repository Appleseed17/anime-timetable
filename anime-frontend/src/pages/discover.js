import { useParams } from "react-router-dom";
import { Options } from "../components/OptionsBar";
import { AnimeGenres } from "../components/genres";
import { AnimeGenre } from "./anime_genres";

export function AnimeDiscover() {
  const { genre } = useParams(); // undefined or genre name

  return (
    <Options>
      <div className="flex gap-8">
        {/* Side genre column */}
        <div className="flex-none h-[80vh] overflow-y-auto">
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