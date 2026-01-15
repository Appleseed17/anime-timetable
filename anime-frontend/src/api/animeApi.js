import axios from "axios";

export async function getSeasonalAnime() {
  const response = await axios.get("http://localhost:8080/api/anime/seasonal/weekly");

  return response; // IMPORTANT
}