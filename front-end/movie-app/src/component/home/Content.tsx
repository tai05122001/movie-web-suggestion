import React, { useEffect, useState, useContext, CSSProperties } from "react";
import Brand from "./Brand";
import { CSSTransition, TransitionGroup } from "react-transition-group";

import ItemGenreSlider from "../common/ItemGenreSlider";
import ItemPosterMovie from "../common/ItemPosterMovie";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import { Pagination } from "swiper/modules";
import { GiSwordsEmblem, GiBearFace } from "react-icons/gi";
import { MdOutlineSort, MdFilterAlt, MdSmartToy } from "react-icons/md";
import { RiGhostFill } from "react-icons/ri";
import { FaFire, FaHeart } from "react-icons/fa";
import { FaRankingStar } from "react-icons/fa6";
import MoonLoader from "react-spinners/MoonLoader";
// Import Swiper styles
import {
  getMovieBrands,
  getGenresList,
  getMovieGenre,
} from "../../api/apiclient";
import { GenreDTO, MovieDTO } from "../../dto/response";
import { URL_IMAGE_STORAGE } from "../../env/contant";
import { AppContext } from "../provider/AppProvider";
const Content: React.FC = () => {
  const { setBgUrl, genreSelected, setGenreSelected } = useContext(AppContext);
  const [data, setData] = useState<MovieDTO[] | null>(null);
  const [genres, setGenres] = useState<GenreDTO[] | null>(null);
  const [dataPoster, setDataPoster] = useState<MovieDTO[] | null>(null);
  const [loading, setLoading] = useState(false); // State kiểm soát loading
  const handleIndexGenre = (item: any, index: number) => {
    setGenreSelected(index);
  };

  useEffect(() => {
    setLoading(true);
    try {
      const fetchDataPosterGenres = async () => {
        const valueGenreSelected = genres?.[genreSelected].name || "Action";
        const response = await getMovieGenre(valueGenreSelected);
        setDataPoster(response);
      };

      setTimeout(() => {
        fetchDataPosterGenres();
        setLoading(false);
      }, 500);
    } catch (error) {
      console.log(error);
    }
  }, [genreSelected]);

  useEffect(() => {
    try {
      const fetchDataBrands = async () => {
        const response = await getMovieBrands();
        setData(response);
      };
      const fetchDataGenres = async () => {
        const response = await getGenresList();
        setGenres(response);
      };
      fetchDataBrands();
      fetchDataGenres();
      return () => {
        setData(null);
        setGenres(null);
      };
    } catch (err) {}
  }, []);

  const list_item = [
    { icon: <FaFire color="white" size={20} /> },
    { icon: <GiSwordsEmblem color="white" size={20} /> },
    { icon: <FaHeart color="white" size={20} /> },
    { icon: <GiBearFace color="white" size={20} /> },
    { icon: <RiGhostFill color="white" size={20} /> },
    { icon: <MdSmartToy color="white" size={20} /> },
    { icon: <FaRankingStar color="white" size={20} /> },
    { icon: <FaFire color="white" size={20} /> },
    { icon: <FaFire color="white" size={20} /> },
    { icon: <FaFire color="white" size={20} /> },
  ];
  const override: CSSProperties = {
    display: "block",
    margin: "0 auto",
    borderColor: "red",
  };

  const style_container: CSSProperties = {
    display: "absolute",
    top: "50%",
    left: "50%",
    position: "absolute",
    transform: "translate(-50%, -50%)",
    
  };
  return (
    <div className="pt-4 items-center z-10">
      <div className="brand flex justify-between">
        <div className="size-full grid grid-cols-7 mr-2 ml-2">
          {data?.map((item, index) =>
            index === 0 ? (
              <div className="pr-2 pl-2 col-span-3" key={index}>
                <Brand
                  id={item.idMovie}
                  content={item.title}
                  image={`${URL_IMAGE_STORAGE}${item.pathImageBrand}`}
                />
              </div>
            ) : (
              <div className="pr-2 pl-2 col-span-4 " key={index}>
                <Brand
                  id={item.idMovie}
                  content={item.title}
                  image={`${URL_IMAGE_STORAGE}${item.pathImageBrand}`}
                />
              </div>
            )
          )}
        </div>
      </div>
      <div className="flex mr-4 ml-4 mt-4">
        <Swiper
          key={`slider1`}
          slidesPerView={7}
          spaceBetween={20}
          pagination={{
            clickable: true,
          }}
          modules={[Pagination]}
          className="mySwiper"
        >
          {genres?.map((item, index) => (
            <SwiperSlide
              className={`rounded-lg bg-transparent item-movie ${
                genreSelected === index ? "bg-black" : "text-textNav"
              }`}
              key={`movie-item-${index}`}
              onClick={() => handleIndexGenre(item, index)}
            >
              <ItemGenreSlider
                icon={list_item[index]?.icon}
                title={item.name}
              />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
      <div className="pl-4 pr-4 mt-4 size-full flex justify-between">
        <span className="text-white text-2xl">Trending in Animation</span>
        <div className="flex bg-black items-center pl-6 pr-6 pt-3 pb-3 rounded-full">
          <MdOutlineSort size={16} color="white" className="mr-8" />
          <MdFilterAlt size={16} color="white" />
        </div>
      </div>
      <div className="flex mr-4 ml-4 mt-4">
        <TransitionGroup component={null}>
          <Swiper
            key={`slider2`}
            slidesPerView={6}
            spaceBetween={20}
            pagination={{
              clickable: true,
            }}
            modules={[Pagination]}
            className="mySwiper"
          >
            {loading ? (
              <div className="h-72 relative">
                <div style={style_container}>
                  <MoonLoader
                    color={"#ffffff"}
                    loading={loading}
                    cssOverride={override}
                    size={100}
                    aria-label="Loading Spinner"
                    data-testid="loader"
                  />
                </div>
              </div>
            ) : (
              dataPoster?.map((item, index) => (
                <CSSTransition
                  key={index}
                  timeout={500}
                  classNames="fade" // Class CSS để tạo hiệu ứng
                >
                  <SwiperSlide
                    className="rounded-lg bg-transparent  "
                    key={`movie-poster-${index}`}
                    onMouseOver={() => {
                      setBgUrl(`${URL_IMAGE_STORAGE}${item.pathImageBrand}`);
                    }}
                  >
                    <ItemPosterMovie
                      image={`${URL_IMAGE_STORAGE}${item.pathImagePoster}`}
                      title={item.title}
                      key={`${index}`}
                      id={item.idMovie}
                    />
                  </SwiperSlide>{" "}
                </CSSTransition>
              ))
            )}
          </Swiper>
        </TransitionGroup>
      </div>
    </div>
  );
};
export default Content;
