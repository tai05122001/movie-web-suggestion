import React, { useContext, useState } from "react";
import Header from "../../component/common/Header";
import Content from "../../component/home/Content";
import Footer from "../../component/common/Footer";
import { AppContext } from "../../component/provider/AppProvider";
import { handleLogin } from "../../api/apiclient";

const Home: React.FC = () => {
  const { bgUrl } = useContext(AppContext);
  const [currentBg, setCurrentBg] = useState(bgUrl);
  const [fade, setFade] = useState(false);
  const myStyle = {
    backgroundImage: `url(${currentBg})`,
    height: "140vh",
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
    setFade(true);
    const timeout = setTimeout(() => {
      setCurrentBg(bgUrl);
      setFade(false);
    }, 500);

    return () => clearTimeout(timeout);
  }, [bgUrl]);
  return (
    <>
      <div
        className="flex justify-center items-center size-full h-lvh absolute background-app"
        style={myStyle}
      ></div>
      <div className="main-layout-wrapper m-auto h-4/5 mt-8 rounded-lg">
        <Header />
        <Content />
        <Footer />
      </div>
    </>
  );
};

export default Home;
