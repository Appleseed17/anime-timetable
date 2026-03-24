import axios from "axios";

//used for accessing front end endpoints

const url = process.env.REACT_APP_API_URL || ""

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
export async function getAnimeByGenre(genre, page, size) {
  const response = await axios.get(`${url}/api/anime/seasonal/genre/${genre}`, {params: {
    page: page,
    size: size
  }});
  return response;
}

export async function getPopularPage() {
  const response = await axios.get(`${url}/api/anime/seasonal/popular/preview`);
  return response;
}

export async function getDiscoverPopular(page, size) {
  var response;
  if (page === 0){
    response = axios.get(`${url}/api/anime/seasonal/discover`)
  }
  else{
    response = axios.get(`${url}/api/anime/seasonal/popular`, {params: {
      page: page,
      size: size
    }})
  }
  return response
}
