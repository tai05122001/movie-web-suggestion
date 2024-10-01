import React, { useContext } from "react";
import Header from "../../component/common/Header";
import Footer from "../../component/common/Footer";
import Content from "../../component/detail/Content";
import videojs from "video.js";
import { AppContext } from "../../component/provider/AppProvider";
import { useParams } from "react-router-dom";
import { getMovieDetail } from "../../api/apiclient";
import { MovieDTO } from "../../dto/response";
import { URL_VIDEO_STORAGE } from "../../env/contant";
import "react-toastify/dist/ReactToastify.css";


const Detail: React.FC = () => {
  const [dataMovie, setDataMovie] = React.useState<MovieDTO | null>(null);
  const { idMovie } = useParams(); // Lấy biến id từ URL
  const { bgUrl } = useContext(AppContext);
  const playerRef = React.useRef(null);
  const [currentBg, setCurrentBg] = React.useState(bgUrl);
  const [fade, setFade] = React.useState(false);
  const myStyle = {
    backgroundImage: `url(${currentBg})`,
    height: "300%",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    zIndex: -1,
    filter: "blur(5px)",
    top: 0,
    left: 0,
    transition: "opacity 0.5s ease-in-out", 
    opacity: fade ? 0 : 1,
  
  };

  React.useEffect(() => {
    try {
      const fetchVideoMovies = async () => {
        const response = await getMovieDetail(idMovie);
        setDataMovie(response);
      };
      fetchVideoMovies();
    } catch (error) {
      console.error(error);
    }
  }, [idMovie]);
  const videoJsOptions = {
    autoplay: false,
    controls: true,
    responsive: true,
    fluid: true,
    sources: [
      {
        src: `${URL_VIDEO_STORAGE}${dataMovie?.urlMovie}`,
        type: "video/mp4",
      },
    ],
  };

  React.useEffect(() => {
    setFade(true);
    const timeout = setTimeout(() => {
      setCurrentBg(bgUrl);
      setFade(false);
    }, 500);

    return () => clearTimeout(timeout);
  }, [bgUrl]);

  const handlePlayerReady = (player: any) => {
    playerRef.current = player;

    // You can handle player events here, for example:
    player.on("waiting", () => {
      videojs.log("player is waiting");
    });

    player.on("dispose", () => {
      videojs.log("player will dispose");
    });
  };

  const handlePlayerClose = (player: any) => {
    player.on("ended", () => {});
  };
  return (
    <>
      <div
        className="flex justify-center items-center size-full h-lvh absolute bg-website"
        style={myStyle}
      ></div>
      <div className="main-layout-wrapper m-auto h-4/5 mt-8 rounded-lg">
        <Header />
        <Content
          options={videoJsOptions}
          onReady={handlePlayerReady}
          onClose={handlePlayerClose}
          movie={dataMovie}
        />

        <Footer />
      </div>
    </>
  );
};

export default Detail;
