import "./App.css";
import NewsService from "./services/newsService";
import BangkokScoopNewsService from "./services/bangkokScoopNewsService";
import ThailandIslandNewsService from "./services/thailandIslandNewsService";
import FindThaiPropertyService from "./services/findThaiPropertyService";
import LegallyMarriedInThailandService from "./services/legallyMarriedInThailandService";
import WeddingBoutiquePhuketService from "./services/weddingBoutiquePhuketService";
import BicycleThailandService from "./services/bicycleThailandService";
import ThaiCapitalistService from "./services/thaiCapitalistService";
import AboutThailandLivingService from "./services/aboutThailandLivingService";
import DaveTheRavesThailandService from "./services/daveTheRavesThailandService";
import ThaifoodmasterService from "./services/thaifoodmasterService";
import ThailandBailService from "./services/thailandBailService";
import TheSilomerService from "./services/theSilomerService";
import PattayaPIService from "./services/pattayaPIService";
import BudgetCatcherService from "./services/budgetCatcherService";
import ThaiLawyersService from "./services/thaiLawyersService";
import MeanderingTalesService from "./services/meanderingTalesService";
import LifestyleInThailandService from "./services/lifestyleInThailandService";
import ThatBangkokLifeService from "./services/thatBangkokLifeService";
import ThinglishLifestyleService from "./services/thinglishLifestyleService";
import FashionGalleriaService from "./services/fashionGalleriaService";
import ThaiLadyDateFinderService from "./services/thaiLadyDateFinderService";
import BangkokNewsService from "./services/bangkokNewsService";
import PattayaNewsService from "./services/pattayaNewsService";
import PhuketNewsService from "./services/phuketNewsService";
import ChiangMaiNewsService from "./services/chiangMaiNewsService";
import HatYaiNewsService from "./services/hatYaiNewsService";
import KhonKaenNewsService from "./services/khonKaenNewsService";
import NakhonRatchasimaNewsService from "./services/nakhonRatchasimaNewsService";
import PopularCitiesMap from "./components/popularCitiesMap";

function App() {
  const path = window.location.pathname;

  let ComponentToRender;
  if (path === "/city/bangkok") {
    ComponentToRender = <BangkokNewsService />;
  } else if (path === "/city/pattaya") {
    ComponentToRender = <PattayaNewsService />;
  } else if (path === "/city/phuket") {
    ComponentToRender = <PhuketNewsService />;
  } else if (path === "/city/chiang-mai") {
    ComponentToRender = <ChiangMaiNewsService />;
  } else if (path === "/city/hat-yai") {
    ComponentToRender = <HatYaiNewsService />;
  } else if (path === "/city/khon-kaen") {
    ComponentToRender = <KhonKaenNewsService />;
  } else if (path === "/city/nakhon-ratchasima") {
    ComponentToRender = <NakhonRatchasimaNewsService />;
  } else if (path === "/bangkokScoopNews") {
    ComponentToRender = <BangkokScoopNewsService />;
  } else if (path === "/thailandIslandNews") {
    ComponentToRender = <ThailandIslandNewsService />;
  } else if (path === "/findThaiProperty") {
    ComponentToRender = <FindThaiPropertyService />;
  } else if (path === "/legallyMarriedInThailand") {
    ComponentToRender = <LegallyMarriedInThailandService />;
  } else if (path === "/thaiLadyDateFinder") {
    ComponentToRender = <ThaiLadyDateFinderService />;
  } else if (path === "/weddingBoutiquePhuket") {
    ComponentToRender = <WeddingBoutiquePhuketService />;
  } else if (path === "/bicycleThailand") {
    ComponentToRender = <BicycleThailandService />;
  } else if (path === "/thaiCapitalist") {
    ComponentToRender = <ThaiCapitalistService />;
  } else if (path === "/aboutThailandLiving") {
    ComponentToRender = <AboutThailandLivingService />;
  } else if (path === "/daveTheRavesThailand") {
    ComponentToRender = <DaveTheRavesThailandService />;
  } else if (path === "/thaiFoodMaster") {
    ComponentToRender = <ThaifoodmasterService />;
  } else if (path === "/thailandBail") {
    ComponentToRender = <ThailandBailService />;
  } else if (path === "/theSilomer") {
    ComponentToRender = <TheSilomerService />;
  } else if (path === "/pattayaPI") {
    ComponentToRender = <PattayaPIService />;
  } else if (path === "/budgetCatcher") {
    ComponentToRender = <BudgetCatcherService />;
  } else if (path === "/thaiLawyers") {
    ComponentToRender = <ThaiLawyersService />;
  } else if (path === "/meanderingTales") {
    ComponentToRender = <MeanderingTalesService />;
  } else if (path === "/lifestyleInThailand") {
    ComponentToRender = <LifestyleInThailandService />;
  } else if (path === "/thatBangkokLife") {
    ComponentToRender = <ThatBangkokLifeService />;
  } else if (path === "/thinglishLifestyle") {
    ComponentToRender = <ThinglishLifestyleService />;
  } else if (path === "/fashionGalleria") {
    ComponentToRender = <FashionGalleriaService />;
  } else if (path === "/popular-cities-map") {
    ComponentToRender = <PopularCitiesMap />;
  } else {
    ComponentToRender = <NewsService />;
  }

  return <div className="App">{ComponentToRender}</div>;
}

export default App;
