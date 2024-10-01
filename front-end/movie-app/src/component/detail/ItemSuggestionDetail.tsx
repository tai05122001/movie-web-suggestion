import { URL_IMAGE_STORAGE } from "../../env/contant";
import { FaPlay } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { MovieDTO } from "../../dto/response";
import { AppContext } from "../provider/AppProvider";
import { useContext } from "react";
interface ItemSuggestionDetailProps {
  movie: MovieDTO;
}

const ItemSuggestionDetail: React.FC<ItemSuggestionDetailProps> = ({
  movie,
}) => {
  const navigate = useNavigate();
  const { setBgUrl } = useContext(AppContext);
  const styles = {
    backgroundImage: `url(${URL_IMAGE_STORAGE}${movie.pathImagePoster})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center",
    borderRadius: "8px",
    overflow: "hidden",
    boxShadow: "0 0 10px rgba(0, 0, 0, 0.2)",
  };
  return (
    <div
      onClick={() => {
        navigate(`/detail/${movie.idMovie}`);
      }}
    >
      <div
        className="h-60 item-poster relative item-poster-suggestion cursor-pointer"
        style={styles}
        onMouseOver={() =>
          setBgUrl(`${URL_IMAGE_STORAGE}${movie.pathImagePoster}`)
        }
      >
        <div className="play-button">
          <FaPlay size={24} color="white" />
        </div>
      </div>
      <div className="pr-8 pl-8 pt-2 ">
        <span className="text-xs text-white">{movie.title}</span>
      </div>
    </div>
  );
};

export default ItemSuggestionDetail;
