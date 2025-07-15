import "./App.css";
import NewsList from "./services/newsService";
import PattayaNewsService from "./services/pattayaNewsService";

function App() {
  const path = window.location.pathname;

  let ComponentToRender;
  if (path === "/city/pattaya") {
    ComponentToRender = <PattayaNewsService />;
  } else {
    ComponentToRender = <NewsList />;
  }

  return <div className="App">{ComponentToRender}</div>;
}

export default App;
