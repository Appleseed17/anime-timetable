import { NavLink, useLocation  } from "react-router-dom"
import { Title } from "./Title";

export function Options({ children }) {
  const options = [
    { label: "Discover", to: "/anime/discover/Popular" },
    { label: "Schedule", to: "/anime/schedule" },
    { label: "Popular", to: "/anime/popular" },
  ]
  const location = useLocation(); 
  
  return (
    <Title>
    <div className="min-h-screen bg-gradient-to-b from-slate-900 via-slate-800 to-slate-900 text-white">
      
      <nav className="
                    flex
              justify-center
              gap-2 sm:gap-6
              py-4 sm:py-6
              px-2
        ">
        {options.map(({ label, to }) => {
          // Custom active logic:
          let active = false;
          if (label === "Discover" && location.pathname.startsWith("/anime/discover")) {
            active = true; // any genre page counts as Discover
          } else if (location.pathname === to) {
            active = true; // exact match for other buttons
          }

          return (
            <NavLink
              key={to}
              to={to}
              className={`
                  flex-1 sm:flex-none
                  text-center
                  px-2 py-2 sm:px-6 sm:py-2
                  rounded-full
                  text-xs sm:text-sm
                  font-semibold tracking-wide
                  transition-colors duration-300
                  backdrop-blur-md
                  border border-white/10
                  ${active
                    ? "bg-gradient-to-r from-purple-500 to-blue-500 shadow-md"
                    : "bg-white/5 hover:bg-white/10"
                  }
                `}
            >
              {label}
            </NavLink>
          );
        })}
      </nav>

      <div className="px-3 sm:px-6 max-w-7xl mx-auto">
        {children}
      </div>
    </div>
    </Title>
  );
}