
export interface GenreDTO {
  idType: string;
  name: string;
  status: string;
}

export interface ActorDTO {
  idActor: string;
  fullName: string;
  birthDate: string;
  status: string;
}
export interface MovieDTO {
  idMovie: string;
  title: string;
  description: string;
  genreList: GenreDTO[];
  actorList: ActorDTO[];
  year: number | null;
  runtime: number | null;
  rating: number | null;
  numVotes: number | null;
  revenue: number;
  metaScore: number;
  status: string;
  views: number;
  pathImagePoster: string;
  pathImageBrand: string;
  urlMovie: string;
}

export interface UserLoginDto {
  username: string;
  email: string;
}
