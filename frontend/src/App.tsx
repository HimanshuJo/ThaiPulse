import "./App.css";
import NewsService from "./services/newsService";
import BangkokScoopNewsService from "./services/bangkokScoopNewsService";
import ThailandIslandNewsService from "./services/thailandIslandNewsService";
import FindThaiPropertyService from "./services/findThaiPropertyService";
import LegallyMarriedInThailandService from "./services/legallyMarriedInThailandService";
import WeddingBoutiquePhuketService from "./services/weddingBoutiquePhuketService";
import PattayaSoi6Service from "./services/pattayaSoi6Service";
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
    console.log("Forming prod path: " + "https://thaipulsetimes.com" + path);
    return "https://thaipulsetimes.com" + path;
  }
  console.log("path name is: " + path);
  return path;
};

function App() {
  const path = getNormalizedPath();

  let ComponentToRender;
  switch (path) {
    case "/navFeeds/city/bangkok":
    case "https://thaipulsetimes.com/navFeeds/city/bangkok":
      ComponentToRender = <BangkokNewsService />;
      break;

     case "/navFeeds/city/pattaya":
      case "https://thaipulsetimes.com/navFeeds/city/pattaya":
        ComponentToRender = <PattayaNewsService />;
        break;

     case "/navFeeds/city/phuket":
      case "https://thaipulsetimes.com/navFeeds/city/phuket":
        ComponentToRender = <PhuketNewsService />;
        break;

     case "/navFeeds/city/chiang-mai":
      case "https://thaipulsetimes.com/navFeeds/city/chiang-mai":
        ComponentToRender = <ChiangMaiNewsService />;
        break;

     case "/navFeeds/city/hat-yai":
      case "https://thaipulsetimes.com/navFeeds/city/hat-yai":
        ComponentToRender = <HatYaiNewsService />;
        break;

     case "/navFeeds/city/khon-kaen":
      case "https://thaipulsetimes.com/navFeeds/city/khon-kaen":
        ComponentToRender = <KhonKaenNewsService />;
        break;

     case "/navFeeds/city/nakhon-ratchasima":
      case "https://thaipulsetimes.com/navFeeds/city/nakhon-ratchasima":
        ComponentToRender = <NakhonRatchasimaNewsService />;
        break;

    case "/feeds/thaiCapitalist":
    case "https://thaipulsetimes.com/feeds/thaiCapitalist":
      ComponentToRender = <ThaiCapitalistService />;
      break;

    case "/feeds/bangkokScoopNews":
    case "https://thaipulsetimes.com/feeds/bangkokScoopNews":
      ComponentToRender = <BangkokScoopNewsService />;
      break;

    case "/feeds/thailandIslandNews":
    case "https://thaipulsetimes.com/feeds/thailandIslandNews":
      ComponentToRender = <ThailandIslandNewsService />;
      break;

    case "/feeds/findThaiProperty":
    case "https://thaipulsetimes.com/feeds/findThaiProperty":
      ComponentToRender = <FindThaiPropertyService />;
      break;

    case "/feeds/legallyMarriedInThailand":
    case "https://thaipulsetimes.com/feeds/legallyMarriedInThailand":
      ComponentToRender = <LegallyMarriedInThailandService />;
      break;

    case "/feeds/thaiLadyDateFinder":
    case "https://thaipulsetimes.com/feeds/thaiLadyDateFinder":
      ComponentToRender = <ThaiLadyDateFinderService />;
      break;

    case "/feeds/weddingBoutiquePhuket":
    case "https://thaipulsetimes.com/feeds/weddingBoutiquePhuket":
      ComponentToRender = <WeddingBoutiquePhuketService />;
      break;

    case "/feeds/pattayaSoi6":
    case "https://thaipulsetimes.com/feeds/pattayaSoi6":
      ComponentToRender = <PattayaSoi6Service />;
      break;

    case "/feeds/aboutThailandLiving":
    case "https://thaipulsetimes.com/feeds/aboutThailandLiving":
      ComponentToRender = <AboutThailandLivingService />;
      break;

    case "/feeds/daveTheRavesThailand":
    case "https://thaipulsetimes.com/feeds/daveTheRavesThailand":
      ComponentToRender = <DaveTheRavesThailandService />;
      break;

    case "/feeds/thaiFoodMaster":
    case "https://thaipulsetimes.com/feeds/thaiFoodMaster":
      ComponentToRender = <ThaifoodmasterService />;
      break;

    case "/feeds/thailandBail":
    case "https://thaipulsetimes.com/feeds/thailandBail":
      ComponentToRender = <ThailandBailService />;
      break;

    case "/feeds/theSilomer":
    case "https://thaipulsetimes.com/feeds/theSilomer":
      ComponentToRender = <TheSilomerService />;
      break;

    case "/feeds/pattayaPI":
    case "https://thaipulsetimes.com/feeds/pattayaPI":
      ComponentToRender = <PattayaPIService />;
      break;

    case "/feeds/budgetCatcher":
    case "https://thaipulsetimes.com/feeds/budgetCatcher":
      ComponentToRender = <BudgetCatcherService />;
      break;

    case "/feeds/thaiLawyers":
    case "https://thaipulsetimes.com/feeds/thaiLawyers":
      ComponentToRender = <ThaiLawyersService />;
      break;

    case "/feeds/meanderingTales":
    case "https://thaipulsetimes.com/feeds/meanderingTales":
      ComponentToRender = <MeanderingTalesService />;
      break;

    case "/feeds/lifestyleInThailand":
    case "https://thaipulsetimes.com/feeds/lifestyleInThailand":
      ComponentToRender = <LifestyleInThailandService />;
      break;

    case "/feeds/thatBangkokLife":
    case "https://thaipulsetimes.com/feeds/thatBangkokLife":
      ComponentToRender = <ThatBangkokLifeService />;
      break;

    case "/feeds/thinglishLifestyle":
    case "https://thaipulsetimes.com/feeds/thinglishLifestyle":
      ComponentToRender = <ThinglishLifestyleService />;
      break;

    case "/feeds/fashionGalleria":
    case "https://thaipulsetimes.com/feeds/fashionGalleria":
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
