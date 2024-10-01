import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { FaBell, FaUser } from "react-icons/fa";
import { AppContext } from "../provider/AppProvider";
const ControlMenu: React.FC = () => {
  // const { isLogin, setIslogin } = useContext(AppContext);
  const username = localStorage.getItem("username") || "";
  const [isShowPopup, setIsShowPopup] = React.useState<boolean>(false);
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("email");
    localStorage.removeItem("isLogin");
    // setIslogin(false);
    navigate("/");
  };
  return (
    <>
      <div className="flex w-28 justify-between items-center pr-4">
        <FaBell color="#b5c8cb" className="m-1" title="Notification" />
        {username ? (
          <div className="relative">
            <span
              className="text-white"
              onMouseOver={() => setIsShowPopup(true)}
            >
              {username}
            </span>

            {isShowPopup ? (
              <div
                className="absolute bg-white right-2 text-base pr-2 pl-2 cursor-pointer"
                onClick={() => handleLogout()}
                onMouseLeave={() => setIsShowPopup(false)}
              >
                Logout
              </div>
            ) : null}
          </div>
        ) : (
          <FaUser
            color="#b5c8cb"
            className="m-1"
            title="Login"
            onClick={() => navigate("/login")}
          />
        )}
      </div>
    </>
  );
};
export default ControlMenu;
