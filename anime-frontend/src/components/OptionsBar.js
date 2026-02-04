import { NavLink } from "react-router-dom"

const options = [
  { label: "Discover", to: "/anime/discover/Popular" },
  { label: "Schedule", to: "/schedule" },
  { label: "Popular", to: "/anime/popular" },
]

const baseButton =
  "px-6 py-2 rounded-lg font-semibold transition"

export function Options( children ) {
  return (
    <div>
      <nav className="flex justify-center gap-6 mb-6">
        {options.map(({ label, to }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) =>
              `${baseButton} ${
                isActive
                  ? "bg-blue-600 text-white shadow"
                  : "bg-blue-500/20 text-blue-900 hover:bg-blue-500/30"
              }`
            }
          >
            {label}
          </NavLink>
        ))}
      </nav>

      {children}
    </div>
  )
}