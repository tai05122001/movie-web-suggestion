import React, { useContext, useState, CSSProperties } from "react";
import videojs from "video.js";
import "video.js/dist/video-js.css";
import { useParams } from "react-router-dom";
import { MovieDTO } from "../../dto/response";
import {
  getMovieRelated,
  createHistory,
  getMovieSuggestion,
  getMovieRelatedLogin,
} from "../../api/apiclient";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import { Pagination } from "swiper/modules";
import ItemSuggestionDetail from "./ItemSuggestionDetail";
import { URL_AVATAR_STORAGE } from "../../env/contant";
import Modal from "react-modal";
import { IoMdClose } from "react-icons/io";
import { FaStar, FaGrinHearts } from "react-icons/fa";
import { IoSadSharp, IoHappySharp } from "react-icons/io5";
import { RiEmotionNormalFill } from "react-icons/ri";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { AppContext } from "../provider/AppProvider";
import ItemRelatedBaseDetail from "../common/ItemRelatedBaseDetail";
import { URL_IMAGE_STORAGE } from "../../env/contant";
import MoonLoader from "react-spinners/MoonLoader";
import PacmanLoader from "react-spinners/PacmanLoader";
interface VideoPlayerProps {
  movie: MovieDTO | null;
  options: any;
  onReady?: (player: any) => void;
  onClose?: (player: any) => void;
}

const Content: React.FC<VideoPlayerProps> = ({ movie, options, onReady }) => {
  const { setBgUrl } = useContext(AppContext);
  const [loading, setLoading] = useState(true);
  const [loading1, setLoading1] = useState(true);
  const username = localStorage.getItem("username") || "";
  const { isLogin } = useContext(AppContext);
  const [contentEmoji, setContentEmoji] = useState<any>(
    <IoSadSharp size={36} color="yellow" />
  );
  const [numRating, setNumRating] = useState<number>(0);
  const [contentRating, setContentRating] = useState<string>("Bad");
  const [dataRelatedMovie, setDataRelatedMovie] = React.useState<MovieDTO[]>(
    []
  );

  const [dataSuggestions, setDataSuggestions] = useState<MovieDTO[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const viewAt = new Date().toISOString().slice(0, 19);

  const videoRef = React.useRef<HTMLDivElement | null>(null);
  const playerRef = React.useRef<any | null>(null);
  const { idMovie } = useParams();
  const stylesAvatar = {
    backgroundImage: `url(${URL_AVATAR_STORAGE})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center",
    display: "inline-block",
    height: "64px",
    width: "64px",
    marginRight: "16px",
  };
  const customStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      padding: 0,
      borderRadius: "4px",
    },
  };

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

  const renderStar = () => {
    const star = [];
    for (let i = 0; i < 5; i++) {
      star.push(
        <FaStar
          key={i}
          // onMouseLeave={() => handleMouseLeave()}
          onMouseOver={() => handleMouseOver(i)}
          onClick={() => handleClickRating(i)}
          style={i <= numRating ? { color: "yellow" } : { color: "gray" }}
        />
      );
    }
    return star;
  };
  const handleClickRating = (i: number) => {
    setNumRating(i);
  };

  const handleReviewMovie = async () => {
    try {
      const viewAt = new Date().toISOString().slice(0, 19);
      const response = await createHistory(
        username || "",
        idMovie || "",
        numRating + 1,
        viewAt
      );
      setIsOpen(false);
      toast.success("Review successfully!", {
        position: "top-right",
        autoClose: 1000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    } catch (e) {
      console.error("Error reviewing movie:", e);
      toast.error("Review fail!", {
        position: "top-right",
        autoClose: 1000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  const handleMouseOver = (index: number) => {
    setNumRating(index);
    setContentRating(
      index === 0
        ? "Bad"
        : index === 1
        ? "Low"
        : index === 2
        ? "Medium"
        : index === 3
        ? "High"
        : "Very Good"
    );

    setContentEmoji(
      index === 0 ? (
        <IoSadSharp size={36} color="yellow" />
      ) : index === 1 ? (
        <RiEmotionNormalFill size={36} color="yellow" />
      ) : index === 2 ? (
        <IoHappySharp size={36} color="yellow" />
      ) : index === 3 ? (
        <FaGrinHearts size={36} color="yellow" />
      ) : (
        <FaGrinHearts size={36} color="yellow" />
      )
    );
  };
  const fetchDataRelated = async () => {
    try {
      setLoading(true);

      if (!username) {
        const username = localStorage.getItem("username") || "";
        const response = await getMovieRelatedLogin(idMovie, username);
        setDataRelatedMovie(response);
      } else {
        const response = await getMovieRelated(idMovie);
        setDataRelatedMovie(response);
      }
    } catch (error) {
      console.error("Error fetching related movies:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchDataSuggestion = async () => {
    try {
      setLoading1(true);
      if (username && idMovie) {
        const username = localStorage.getItem("username") || "";
        const response = await getMovieSuggestion(username, 3, idMovie);
        setDataSuggestions(response);
      }
    } catch (error) {
      console.error("Error fetching related movies:", error);
    } finally {
      setLoading1(false);
    }
  };

  React.useEffect(() => {
    fetchDataRelated();
    fetchDataSuggestion();
  }, [idMovie]); //
  React.useEffect(() => {
    if (!playerRef.current) {
      const videoElement = document.createElement("video-js");
      videoElement.classList.add("vjs-big-play-centered");
      if (videoRef.current !== null) {
        videoRef.current.appendChild(videoElement);
      }

      const player = (playerRef.current = videojs(videoElement, options, () => {
        onReady && onReady(player);
      }));
      player.on("ended", () => {
        setIsOpen(true); // Open modal or handle video end
      });
    } else {
      const player = playerRef.current;
      player.autoplay(options.autoplay);
      player.src(options.sources);
    }

    return () => {
      if (playerRef.current && !playerRef.current.isDisposed()) {
        playerRef.current.dispose();
        playerRef.current = null;
      }
    };
  }, [options, onReady]);

  return (
    <div className="flex justify-between p-6">
      <div className="size-full flex justify-center">
        <div>
          <div className="flex pl-4">
            <div className="text-white text-base">
              <pre> Genre: </pre>
            </div>
            {movie?.genreList.map((item, index) => (
              <h1 key={index} className="text-white pr-2">
                {item.name}{" "}
                {index !== movie.genreList.length - 1 ? (
                  <span>,</span>
                ) : (
                  <div></div>
                )}{" "}
              </h1>
            ))}
          </div>

          <div data-vjs-player>
            <div
              style={{
                width: 1000,
                height: 562,
                minHeight: 562,
                paddingLeft: 16,
                paddingRight: 32,
                paddingTop: 8,
              }}
              ref={videoRef}
            />
          </div>
          <h1 className="pl-4 pt-4 text-white text-xl">{movie?.title}</h1>
          <div className="ml-4 mt-2 bg-[#232d37] max-w-[950px] text-xs text-white p-4">
            {movie?.description}
          </div>

          {username ? (
            <div className="pl-4 text-white mt-2">
              <span>Favorite movie suggestions</span>
              <div className="mt-2 bg-[#232d37] max-w-[950px] text-xs text-white p-4">
                <div>
                  {loading1 ? (
                    <div className="h-72 relative">
                      <div style={style_container}>
                        <PacmanLoader
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
                    <Swiper
                      key={`slider2`}
                      slidesPerView={5}
                      spaceBetween={20}
                      pagination={{
                        clickable: true,
                      }}
                      modules={[Pagination]}
                      className="mySwiper"
                    >
                      {dataSuggestions?.map((item, index) => (
                        <SwiperSlide
                          className="rounded-lg bg-transparent  "
                          key={`movie-poster-${index}`}
                          onMouseOver={() => {
                            setBgUrl(
                              `${URL_IMAGE_STORAGE}${item.pathImageBrand}`
                            );
                          }}
                        >
                          <ItemSuggestionDetail movie={item} />
                        </SwiperSlide>
                      ))}
                    </Swiper>
                  )}
                </div>
              </div>
            </div>
          ) : null}

          <div className="bg-white max-w-[950px] ml-4 mt-8 p-4">
            <h1 className="text-base">0 bình luận</h1>
            <div className="w-full bg-black opacity-15 line-comment mt-2"></div>
            <div className="flex w-full mt-2">
              <div style={stylesAvatar}></div>
              <textarea
                name=""
                className="border-comment pl-2"
                placeholder="Bình luận..."
              />
            </div>
          </div>
        </div>

        <div className="w-96">
          <h1 className="text-white text-base ">Related Movies</h1>

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
            dataRelatedMovie?.map((item, index) => (
              <>
                <div className="mt-2"></div>
                <ItemRelatedBaseDetail movie={item} />
              </>
            ))
          )}
        </div>
      </div>
      {isOpen && username ? (
        <>
          <Modal
            isOpen={isOpen}
            style={customStyles}
            contentLabel="Example Modal"
            ariaHideApp={false}
          >
            <div className="w-80 bg-green-400 flex justify-between p-4 ">
              <h2 className=" text-white">Review movie</h2>
              <IoMdClose
                size={20}
                color="white"
                onClick={() => setIsOpen(false)}
              />
            </div>
            <div className="p-4 ">
              <div className="text-[#4ade80] font-semibold text-center">
                Give a review about the movie
              </div>
              <br />
              {/* <div className="flex justify-center">{contentEmoji}</div> */}
              {contentEmoji && (
                <div className="flex justify-center">{contentEmoji}</div>
              )}

              <div className="text-center">{contentRating}</div>
              <div className="flex justify-center pt-2">{renderStar()}</div>
            </div>
            <div className=" p-4 w-full flex justify-end ">
              <button
                className="button-review-modal text-xs mr-1"
                onClick={() => handleReviewMovie()}
              >
                Review
              </button>
              <button
                className="button-cancel-modal text-xs"
                onClick={() => setIsOpen(false)}
              >
                Cancel
              </button>
            </div>
          </Modal>
        </>
      ) : null}
      <ToastContainer />
    </div>
  );
};
export default Content;
