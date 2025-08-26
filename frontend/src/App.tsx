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

const getNormalizedPath = () => {
  const hostname = window.location.hostname;
  const path = window.location.pathname;
  if (hostname === "thaipulsetimes.com") {
    console.log("Forming prod path: "+"https://thaipulsetimes.com"+path)
    return "https://thaipulsetimes.com"+path;
  }
  console.log("path name is: "+path);
  return path;
};

function App() {
  const path = getNormalizedPath();

  let ComponentToRender;
  switch (path) {
    case "/city/bangkok":
    case "https://thaipulsetimes.com/city/bangkok":
      ComponentToRender = <BangkokNewsService />;
      break;
    case "/city/pattaya":
    case "https://thaipulsetimes.com/city/pattaya":
      ComponentToRender = <PattayaNewsService />;
      break;
    case "/city/phuket":
    case "https://thaipulsetimes.com/city/phuket":
      ComponentToRender = <PhuketNewsService />;
      break;
    case "/city/chiang-mai":
    case "https://thaipulsetimes.com/city/chiang-mai":
      ComponentToRender = <ChiangMaiNewsService />;
      break;
    case "/city/hat-yai":
    case "https://thaipulsetimes.com/city/hat-yai":
      ComponentToRender = <HatYaiNewsService />;
      break;
    case "/city/khon-kaen":
    case "https://thaipulsetimes.com/city/khon-kaen":
      ComponentToRender = <KhonKaenNewsService />;
      break;
    case "/city/nakhon-ratchasima":
    case "https://thaipulsetimes.com/city/nakhon-ratchasima":
      ComponentToRender = <NakhonRatchasimaNewsService />;
      break;
    case "/bangkokScoopNews":
    case "https://thaipulsetimes.com/bangkokScoopNews":
      ComponentToRender = <BangkokScoopNewsService />;
      break;
    case "/thailandIslandNews":
    case "https://thaipulsetimes.com/thailandIslandNews":
      ComponentToRender = <ThailandIslandNewsService />;
      break;
    case "/findThaiProperty":
    case "https://thaipulsetimes.com/findThaiProperty":
      ComponentToRender = <FindThaiPropertyService />;
      break;
    case "/legallyMarriedInThailand":
    case "https://thaipulsetimes.com/legallyMarriedInThailand":
      ComponentToRender = <LegallyMarriedInThailandService />;
      break;
    case "/thaiLadyDateFinder":
    case "https://thaipulsetimes.com/thaiLadyDateFinder":
      ComponentToRender = <ThaiLadyDateFinderService />;
      break;
    case "/weddingBoutiquePhuket":
    case "https://thaipulsetimes.com/weddingBoutiquePhuket":
      ComponentToRender = <WeddingBoutiquePhuketService />;
      break;
    case "/bicycleThailand":
    case "https://thaipulsetimes.com/bicycleThailand":
      ComponentToRender = <BicycleThailandService />;
      break;
    case "/feeds/thaiCapitalist":
    case "https://thaipulsetimes.com/feeds/thaiCapitalist":
      ComponentToRender = <ThaiCapitalistService />;
      break;
    case "/aboutThailandLiving":
    case "https://thaipulsetimes.com/aboutThailandLiving":
      ComponentToRender = <AboutThailandLivingService />;
      break;
    case "/daveTheRavesThailand":
    case "https://thaipulsetimes.com/daveTheRavesThailand":
      ComponentToRender = <DaveTheRavesThailandService />;
      break;
    case "/thaiFoodMaster":
    case "https://thaipulsetimes.com/thaiFoodMaster":
      ComponentToRender = <ThaifoodmasterService />;
      break;
    case "/thailandBail":
    case "https://thaipulsetimes.com/thailandBail":
      ComponentToRender = <ThailandBailService />;
      break;
    case "/theSilomer":
    case "https://thaipulsetimes.com/theSilomer":
      ComponentToRender = <TheSilomerService />;
      break;
    case "/pattayaPI":
    case "https://thaipulsetimes.com/pattayaPI":
      ComponentToRender = <PattayaPIService />;
      break;
    case "/budgetCatcher":
    case "https://thaipulsetimes.com/budgetCatcher":
      ComponentToRender = <BudgetCatcherService />;
      break;
    case "/thaiLawyers":
    case "https://thaipulsetimes.com/thaiLawyers":
      ComponentToRender = <ThaiLawyersService />;
      break;
    case "/meanderingTales":
    case "https://thaipulsetimes.com/meanderingTales":
      ComponentToRender = <MeanderingTalesService />;
      break;
    case "/lifestyleInThailand":
    case "https://thaipulsetimes.com/lifestyleInThailand":
      ComponentToRender = <LifestyleInThailandService />;
      break;
    case "/thatBangkokLife":
    case "https://thaipulsetimes.com/thatBangkokLife":
      ComponentToRender = <ThatBangkokLifeService />;
      break;
    case "/thinglishLifestyle":
    case "https://thaipulsetimes.com/thinglishLifestyle":
      ComponentToRender = <ThinglishLifestyleService />;
      break;
    case "/fashionGalleria":
    case "https://thaipulsetimes.com/fashionGalleria":
      ComponentToRender = <FashionGalleriaService />;
      break;
    case "/popular-cities-map":
    case "https://thaipulsetimes.com/popular-cities-map":
      ComponentToRender = <PopularCitiesMap />;
      break;
    default:
      ComponentToRender = <NewsService />;
      break;
  }

  return <div className="App">{ComponentToRender}</div>;
}

export default App;
