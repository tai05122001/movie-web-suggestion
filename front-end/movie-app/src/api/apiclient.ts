import axios from "axios";
import { GenreDTO, MovieDTO, UserLoginDto } from "../dto/response";

export async function getMovieBrands(): Promise<MovieDTO[]> {
  try {
    const response = await axios.get<MovieDTO[]>(
      "http://localhost:8080/api/common/movie/movie-brand"
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie brands:", error);
    throw error;
  }
}

export async function getMoviePoster(): Promise<MovieDTO[]> {
  try {
    const response = await axios.get<MovieDTO[]>(
      "http://localhost:8080/api/common/movie/movie-brand"
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie poster:", error);
    throw error;
  }
}

export async function getMovieGenre(nameGenre: string): Promise<MovieDTO[]> {
  try {
    const response = await axios.get<MovieDTO[]>(
      `http://localhost:8080/api/common/movie/genre/${nameGenre}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie genre:", error);
    throw error;
  }
}

export async function getGenresList(): Promise<GenreDTO[]> {
  try {
    const response = await axios.get<GenreDTO[]>(
      "http://localhost:8080/api/common/genre"
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching genre list :", error);
    throw error;
  }
}

export async function getMovieDetail(
  id: string = "3009f113-a072-4e9a-afa7-b050f15fcb6e"
): Promise<MovieDTO> {
  try {
    const response = await axios.get<MovieDTO>(
      `http://localhost:8080/api/common/movie/${id}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie detail:", error);
    throw error;
  }
}

export async function getMovieRelated(
  id: string = "3009f113-a072-4e9a-afa7-b050f15fcb6e"
): Promise<MovieDTO[]> {
  try {
    const response = await axios.get<MovieDTO[]>(
      `http://localhost:8080/api/common/content-based/${id}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie related:", error);
    throw error;
  }
}

export async function getMovieRelatedLogin(
  id: string = "3009f113-a072-4e9a-afa7-b050f15fcb6e",
  username:string
): Promise<MovieDTO[]> {
  try {
    const response = await axios.post<MovieDTO[]>(
      `http://localhost:8080/api/common/content-based`,
      {
        idMovie: id,
        username: username,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie related:", error);
    throw error;
  }
}

export async function handleLogin(
  email: string,
  password: string
): Promise<UserLoginDto> {
  try {
    const response = await axios.post<UserLoginDto>(
      `http://localhost:8080/api/common/user/login`,
      {
        email: email,
        password: password,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Login fail", error);
    throw error;
  }
}

export async function createHistory(
  username: string,
  idMovie: string,
  rating: number,
  viewAt: string
) {
  try {
    const response = await axios.post<null>(
      `http://localhost:8080/api/common/history`,
      {
        username: username,
        idMovie: idMovie,
        rating: rating,
        viewAt: viewAt,
      }
    );
    return response.data;
  } catch (error) {
    console.error("create history error ", error);
    throw error;
  }
}

export async function getMovieSuggestion(
  username: string,
  numUserSimilar: number,
  idMovieCurrent: string
): Promise<MovieDTO[]> {
  try {
    const response = await axios.post<MovieDTO[]>(
      `http://localhost:8080/api/common/similar`,
      {
        username: username,
        numUserSimilar: numUserSimilar,
        idMovieCurrent: idMovieCurrent,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie related:", error);
    throw error;
  }
}


export async function searchMovieByKeyword(
  keyword: string
): Promise<MovieDTO[]> {
  try {
    const response = await axios.get<MovieDTO[]>(
      `http://localhost:8080/api/common/movie/search?keyword=${keyword}`,
     
    );
    return response.data;
  } catch (error) {
    console.error("Search fail", error);
    throw error;
  }
}
