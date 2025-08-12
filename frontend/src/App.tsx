import "./App.css";
import NewsService from "./services/newsService";
import BangkokScoopNewsService from "./services/bangkokScoopNewsService";
import ThailandIslandNewsService from "./services/thailandIslandNewsService";
import FindThaiPropertyService from "./services/findThaiPropertyService";
import LegallyMarriedInThailandService from "./services/legallyMarriedInThailandService";
import ThaiLadyDateFinderService from "./services/thaiLadyDateFinderService";
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
  } else if(path === "/bangkokScoopNews"){
    ComponentToRender = <BangkokScoopNewsService />
  } else if(path === "/thailandIslandNews"){
    ComponentToRender = <ThailandIslandNewsService />
  } else if(path === "/findThaiProperty"){
    ComponentToRender = <FindThaiPropertyService />
  } else if(path === "/legallyMarriedInThailand"){
    ComponentToRender = <LegallyMarriedInThailandService />
  } else if(path === "/thaiLadyDateFinder"){
    ComponentToRender = <ThaiLadyDateFinderService />
  } else {
    ComponentToRender = <NewsService />;
  }

  return <div className="App">{ComponentToRender}</div>;
}

export default App;
