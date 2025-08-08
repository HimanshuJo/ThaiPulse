import "./App.css";
import NewsList from "./services/newsService";
import PattayaNewsService from "./services/pattayaNewsService";
import BangkokNewsService from "./services/bangkokNewsService";

function App() {
  const path = window.location.pathname;

  let ComponentToRender;
  if (path === "/city/pattaya") {
    ComponentToRender = <PattayaNewsService />;
  } else if(path==="/city/bangkok"){
    ComponentToRender=<BangkokNewsService />;
  } else {
    ComponentToRender = <NewsList />;
  }

  return <div className="App">{ComponentToRender}</div>;
}

export default App;
