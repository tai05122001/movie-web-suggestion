import React from "react";
import { FaStar } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
interface ItemPosterMovieProps {
  image: string;
  title: string;
  id: string;
}
const ItemPosterMovie: React.FC<ItemPosterMovieProps> = (props) => {
  const navigate = useNavigate();
  const styles = {
    backgroundImage: `url(${props.image})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center",
    borderRadius: "8px",
    overflow: "hidden",
    boxShadow: "0 0 10px rgba(0, 0, 0, 0.2)",
  };
  return (
    <div onClick={() => {navigate(`/detail/${props.id}`)}}>
      <div className="h-72 item-poster" style={styles}></div>
      <div className="pr-8 pl-8">
        <span className="text-xs text-white">{props.title}</span>
        <div className="flex justify-start pt-1">
          <div className="flex w-10 justify-around mr-4 review-tab pr-2">
            <FaStar size={16} color="yellow" />
            <span className="text-white text-xs">60</span>
          </div>
          <span className="text-white text-xs">2023</span>
        </div>
      </div>
    </div>
  );
};
export default ItemPosterMovie;
