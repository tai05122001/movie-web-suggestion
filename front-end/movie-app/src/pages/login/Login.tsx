import React, { useContext } from "react";
import { AppContext } from "../../component/provider/AppProvider";
import { handleLogin } from "../../api/apiclient";
import { useNavigate } from "react-router-dom";
const Login: React.FC = () => {
  const navigate = useNavigate();
  const { bgUrl, setIslogin } = useContext(AppContext);
  const [email, setEmail] = React.useState<string>("");
  const [password, setPassword] = React.useState<string>("");
  const handleSubmit = async (e: any) => {
    e.preventDefault();
    try {
      const response = await handleLogin(email, password);
      localStorage.setItem("email", response.email);
      localStorage.setItem("username", response.username);
      localStorage.setItem("isLogin", "true");
      setIslogin(true);
      navigate("/");
    } catch (err) {
      setEmail("");
      setPassword("");
    }
  };
  const handleChangeEmail = (e: any) => {
    setEmail(e.target.value);
  };
  const handleChangePassword = (e: any) => {
    setPassword(e.target.value);
  };

  const myStyle = {
    backgroundImage: `url(${bgUrl})`,
    height: "140vh",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    zIndex: -1,
    filter: "blur(10px)",
    top: 0,
    left: 0,
  };

  return (
    <>
      <div
        className="flex justify-center items-center size-full h-lvh absolute background-app"
        style={myStyle}
      ></div>
      <div className="login-layout pt-8 pb-8">
        <div className="text-white text-2xl text-center ">Login</div>
        <form action="" className="mt-4" onSubmit={(e) => handleSubmit(e)}>
          <div className="m-auto mt-2 w-3/5">
            <label htmlFor="email" className="text-white text-left">
              Email
            </label>
            <br />

            <input
              type="text"
              placeholder="Email"
              name="email"
              className="mt-2"
              onChange={(e) => handleChangeEmail(e)}
              value={email}
            />
          </div>
          <div className="m-auto w-3/5 mt-2">
            <label htmlFor="password" className="text-white text-left">
              Password
            </label>
            <br />
            <input
              type="password"
              placeholder="Password"
              name="password"
              className="mt-2"
              onChange={(e) => handleChangePassword(e)}
              value={password}
            />
            <br />
            <button type="submit" className="bg-white mt-2 m-auto btn-login">
              Login
            </button>
          </div>
        </form>
      </div>
    </>
  );
};

export default Login;
