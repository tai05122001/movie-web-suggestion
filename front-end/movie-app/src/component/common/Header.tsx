import React from "react"; // we need this to make JSX compile
import MainNavigate from "./MainNavigate";
import ControlMenu from "./ControlMenu";
import { useNavigate } from "react-router-dom";
import { URL_LOGO_STORAGE } from "../../env/contant";
const Header: React.FC = () => {
  const list_items = [
    {
      title: "Movie",
      link: "/",
      index: 0,
    },
    {
      title: "Series",
      link: "/detail",
      index: 1,
    },
    {
      title: "Originals",
      link: "/",
      index: 2,
    },
  ];
  const navigate = useNavigate();
  return (
    <>
      <div className="flex justify-between pt-4 items-center pr-4 pl-4">
        <button onClick={() => navigate("/")}>
          <img src={`${URL_LOGO_STORAGE}`} alt="" width={96} height={96} />
        </button>
        <MainNavigate items={list_items}></MainNavigate>
        <ControlMenu></ControlMenu>
      </div>
    </>
  );
};
export default Header;
