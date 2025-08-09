import "./App.css";
import NewsList from "./services/newsService";
import BangkokNewsService from "./services/bangkokNewsService";
import PattayaNewsService from "./services/pattayaNewsService";
import PhuketNewsService from "./services/phuketNewsService";

function App() {
  const path = window.location.pathname;

  let ComponentToRender;
  if (path === "/city/bangkok") {
    ComponentToRender = <BangkokNewsService />;
  } else if(path === "/city/pattaya"){
    ComponentToRender=<PattayaNewsService />;
  } else if(path === "/city/phuket"){
    ComponentToRender = <PhuketNewsService />;
  } else {
    ComponentToRender = <NewsList />;
  }

  return <div className="App">{ComponentToRender}</div>;
}

export default App;
