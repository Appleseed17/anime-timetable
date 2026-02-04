import { useParams } from "react-router-dom"
import { useEffect, useState } from "react"

import { Options } from "../components/OptionsBar";
import { AnimeGenres } from "../components/genres";
import { AnimeGenre } from "./anime_genres";

export function AnimeDiscover() {


    const { genre } = useParams() // undefined OR "action"
   

    const html = AnimeGenres()
    
    return (Options(
        
        <div className="flex gap-20">
            
            <div className="flex content-start">{html}</div>
            <div className="flex self-end">{AnimeGenre(genre)}</div>
             </div>))
}