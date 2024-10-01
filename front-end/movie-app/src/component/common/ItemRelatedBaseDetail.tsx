import { URL_IMAGE_STORAGE } from "../../env/contant";
import { FaPlay } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { MovieDTO } from "../../dto/response";
import { AppContext } from "../provider/AppProvider";
import { useContext } from "react";
interface ItemRelatedBaseDetailProps {
  movie: MovieDTO;
}

const ItemRelatedBaseDetail: React.FC<ItemRelatedBaseDetailProps> = ({
  movie,
}) => {
  const navigate = useNavigate();
  const { setBgUrl } = useContext(AppContext);
  const styles = {
    backgroundImage: `url(${URL_IMAGE_STORAGE}${movie.pathImagePoster})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center",
    display: "inline-block",
  };
  const handleClick = () => {
    navigate(`/detail/${movie.idMovie}`);
  };
  return (
    <div
      className="flex justify-between content-related cursor-pointer"
      onClick={() => handleClick()}
      onMouseOver={() =>
        setBgUrl(`${URL_IMAGE_STORAGE}${movie.pathImageBrand}`)
      }
    >
      <div
        className="w-[110px] h-[150px] min-w-[110px] min-h-[150px] item-related "
        style={styles}
      >
        <div className=" play-button">
          <FaPlay size={20} className="text-white" />
        </div>
      </div>
      <div className="pl-4 pr-4 pt-2">
        <h4 className="text-sm font-semibold text-white">{movie.title}</h4>
        <p className="text-xs text-gray-400 pt-1  limited-lines overflow-hidden line-clamp-5">
          {movie.description}
        </p>
      </div>
    </div>
  );
};

export default ItemRelatedBaseDetail;
