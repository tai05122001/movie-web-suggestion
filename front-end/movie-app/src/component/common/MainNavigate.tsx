import React, { useState, useContext, CSSProperties } from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";
import { IoClose } from "react-icons/io5";
import { searchMovieByKeyword } from "../../api/apiclient";
import { MovieDTO } from "../../dto/response";
import { RingLoader } from "react-spinners";
import ItemSearchMovie from "./ItemSearchMovie";
import throttle from "lodash.throttle";
import { useParams } from "react-router-dom";
interface MainNavigateProps {
  items: any[]; // Define that the 'items' prop is an array of strings
}
const MainNavigate: React.FC<MainNavigateProps> = ({ items }) => {
  const [content, setContent] = useState<string | null>(null);
  const [isHaveContent, setIsHaveContent] = useState<boolean>(false);
  const [dataSearch, setDataSearch] = useState<MovieDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const style_container: CSSProperties = {
    display: "block",

    right: 16,
    position: "absolute",
    transform: "translate(-50%, -50%)",
  };

  const override: CSSProperties = {
    display: "block",
    margin: "0 auto",
    borderColor: "red",
    paddingRight: 8,
  };
  const handleSearchCallback = React.useCallback(
    throttle((content) => {
      fetchDataSearch();
    }, 1000), // Gửi yêu cầu tối đa một lần mỗi 500ms
    []
  );
  const fetchDataSearch = async () => {
    try {
      setLoading(true);
      if (content !== null) {
        const response = await searchMovieByKeyword(content);
        setDataSearch(response);
      }
    } catch (e) {
      console.error("Error fetching movie search:", e);
    } finally {
      setLoading(false);
    }
  };

  const handleChangeContent = (e: any) => {
    setContent(e.target.value);
    setIsHaveContent(e.target.value.length > 0);
    handleSearchCallback(content);
  };

  const handleClearContent = () => {
    setContent(null);
    setIsHaveContent(false);
    setDataSearch([]);
  };
  const handleSearch = (e: any) => {
    if (e.code === "Space") {
      fetchDataSearch();
    }
  };
  return (
    <>
      <div className="list-none flex-row flex bg-bgNav text-center h-12 rounded-full main-nav items-center relative justify-center ">
        <input
          className="  w-72 bg-transparent text-xs text-white pt-2 pb-2 pl-8  focus:outline-none focus:border-transparent"
          type="text"
          name="search-bar"
          placeholder="Search here..."
          value={content || ""}
          onChange={(e) => handleChangeContent(e)}
          onKeyUp={(e) => handleSearch(e)}
        />
        <div style={{ paddingRight: 16 }}>
          <RingLoader
            color={"#ffffff"}
            loading={loading}
            cssOverride={override}
            size={20}
            aria-label="Loading Spinner"
            data-testid="loader"
          />
        </div>

        <div
          className=""
          style={{
            transition: "opacity ease-in-out 0.5s",
            opacity: isHaveContent ? 1 : 0,
            paddingRight: 8,
          }}
        >
          <IoClose
            color="white"
            size={20}
            onClick={() => handleClearContent()}
          />
        </div>

        <div
          className=" p-4 rounded-full bg-white cursor-pointer  "
          onClick={() => console.log(content)}
        >
          <FaMagnifyingGlass color="#010f1c" size={18} />
        </div>

        <div className=" absolute w-4/5 top-12 z-50 ">
          {dataSearch?.map((movie) => (
            <ItemSearchMovie movie={movie} />
          ))}
        </div>
      </div>
    </>
  );
};
export default MainNavigate;
