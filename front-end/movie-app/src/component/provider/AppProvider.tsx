import React, { useState, useContext, createContext, ReactNode } from "react";
import { URL_IMAGE_STORAGE } from "../../env/contant";

interface AppContextProps {
  bgUrl: string;
  state: number;
  genreSelected: number;
  isLogin: boolean;
  isShowLogin: boolean;
  setState: React.Dispatch<React.SetStateAction<number>>;
  setBgUrl: React.Dispatch<React.SetStateAction<string>>;
  setGenreSelected: React.Dispatch<React.SetStateAction<number>>;
  setIslogin: React.Dispatch<React.SetStateAction<boolean>>;
  setIsShowLogin: React.Dispatch<React.SetStateAction<boolean>>;
}
const isLogin = localStorage.getItem("isLogin") || "false";
const initState: AppContextProps = {
  bgUrl: `${URL_IMAGE_STORAGE}/v1727363225/background-2_zbcyb7.jpg`,
  state: 0,
  genreSelected: 0,
  isLogin: isLogin === "true" ? true : false,
  isShowLogin: false,
  setGenreSelected: () => {},
  setState: () => {},
  setBgUrl: () => {},
  setIslogin: () => {},
  setIsShowLogin: () => {},
};
const AppContext = createContext<AppContextProps>(initState);
const AppProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [state, setState] = useState<number>(initState.state);
  const [bgUrl, setBgUrl] = useState<string>(initState.bgUrl);
  const [genreSelected, setGenreSelected] = useState<number>(
    initState.genreSelected
  );
  const [isLogin, setIslogin] = useState<boolean>(initState.isLogin);
  const [isShowLogin, setIsShowLogin] = useState<boolean>(
    initState.isShowLogin
  );
  return (
    <AppContext.Provider
      value={{
        state,
        setState,
        bgUrl,
        setBgUrl,
        genreSelected,
        setGenreSelected,
        isLogin,
        setIslogin,
        isShowLogin,
        setIsShowLogin,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};
export { AppProvider, AppContext };
