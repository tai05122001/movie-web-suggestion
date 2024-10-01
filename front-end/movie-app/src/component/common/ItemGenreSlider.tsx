import React from "react";

interface ItemMovieSliderProp {
  icon: any;
  title: string;
}
const ItemMovieSlider: React.FC<ItemMovieSliderProp> = (props) => {


  return (
    <div className="flex justify-center h-12 items-center bg-opacity-60">
      <div className="pr-4 ">{props.icon}</div>
      <div className=" text-white" ><span>{props.title}</span></div>
    </div>

  );
};

export default ItemMovieSlider;
