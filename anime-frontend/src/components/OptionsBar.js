import { Link } from "react-router-dom"

const button = "flex items-center justify-center border border-4 rounded-md lg:border-blue-900 w-24 bg-blue-300 bg-opacity-50"

export function Options(html) {

    return (
        <div>

            <div className="grid grid-cols-3 text-blue-800 underline font-bold">
                <div className={button}><Link to="/anime/genres">Genres</Link></div>
                <div className={button}><Link to="/schedule">Schedule</Link></div>
                <div className={button}><Link to="/anime/popular">Popular</Link></div>
            </div>
            {html}
        </div>
    )



}