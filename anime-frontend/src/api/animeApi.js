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