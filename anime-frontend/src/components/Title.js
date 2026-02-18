import { Link } from "react-router-dom";

export function Title({ children }) {
  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-900 via-slate-800 to-slate-900 text-white">

      
      <header className="sticky top-0 z-50 backdrop-blur-md bg-slate-900/70 border-b border-white/10">
        <div className="max-w-7xl mx-auto px-6 py-4 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-2">
         
          <Link
            to="/"
            className="text-2xl font-bold tracking-wide bg-gradient-to-r from-purple-400 to-blue-400 bg-clip-text text-transparent hover:opacity-90"
          >
            Anime Calendar
          </Link>

          <div className="text-xs text-white/50">
            Powered by{" "}
            <a
              href="https://myanimelist.net"
              target="_blank"
              rel="noreferrer"
              className="hover:text-white underline"
            >
              MyAnimeList
            </a>
          </div>
        </div>
      </header>

      <main className="px-3 sm:px-4 md:px-6 max-w-7xl mx-auto py-4 md:py-6">
        {children}
      </main>
    </div>
  );
}