import axios from "axios";


const url = "http://localhost:8080"

export async function getSeasonalAnime() {
  const response = await axios.get(`${url}/api/anime/seasonal/weekly`);
  return response; 
}

export async function getAnimeByID(id) {
  const response = await axios.get(`${url}/api/anime/${id}`);
  return response;
}

export async function getGenres() {
  const response = await axios.get(`${url}/api/anime/seasonal/genre`);
  return response;
}
export async function getAnimeByGenre(genre) {
  const response = await axios.get(`${url}/api/anime/seasonal/genre/${genre}`, {params: {
    size: 5
  }});
  return response;
}

export async function getPopularAnime() {
  const response = await axios.get(`${url}/api/anime/seasonal/popular`,
     {params: {
      page: 0,
      size: 3
     }});
     return response
}

