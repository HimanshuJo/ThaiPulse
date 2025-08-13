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
import ChiangMaiNewsService from "./services/chiangMaiNewsService";
import HatYaiNewsService from "./services/hatYaiNewsService";
import KhonKaenNewsService from "./services/khonKaenNewsService";
import NakhonRatchasimaNewsService from "./services/nakhonRatchasimaNewsService";

function App() {
  const path = window.location.pathname;

  let ComponentToRender;
  if (path === "/city/bangkok") {
    ComponentToRender = <BangkokNewsService />;
  } else if(path === "/city/pattaya"){
    ComponentToRender = <PattayaNewsService />;
  } else if(path === "/city/phuket"){
    ComponentToRender = <PhuketNewsService />;
  } else if(path === "/city/chiang-mai"){
    ComponentToRender = <ChiangMaiNewsService />
  } else if(path === "/city/hat-yai"){
    ComponentToRender = <HatYaiNewsService />
  } else if(path === "/city/khon-kaen"){
    ComponentToRender = <KhonKaenNewsService />
  } else if(path === "/city/nakhon-ratchasima"){
    ComponentToRender = <NakhonRatchasimaNewsService />
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
