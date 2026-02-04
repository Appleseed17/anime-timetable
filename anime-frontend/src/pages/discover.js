import { Options } from "../components/OptionsBar";
import { AnimeGenres } from "../components/genres";
import { AnimeGenre } from "./anime_genres";

export function AnimeDiscover() {
    const html = AnimeGenres()
    var genre = AnimeGenre("Action")
    return (Options(
        
        <div className="flex gap-20">
            <div className="flex content-start">{html}</div>
            <div className="flex self-end">{genre}</div>
             </div>))
}