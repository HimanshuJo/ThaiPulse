import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

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
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<NewsService />} />

          <Route path="/city/bangkok" element={<BangkokNewsService />} />
          <Route path="/city/pattaya" element={<PattayaNewsService />} />
          <Route path="/city/phuket" element={<PhuketNewsService />} />
          <Route path="/city/chiang-mai" element={<ChiangMaiNewsService />} />
          <Route path="/city/hat-yai" element={<HatYaiNewsService />} />
          <Route path="/city/khon-kaen" element={<KhonKaenNewsService />} />
          <Route
            path="/city/nakhon-ratchasima"
            element={<NakhonRatchasimaNewsService />}
          />

          <Route path="/bangkokScoopNews" element={<BangkokScoopNewsService />} />
          <Route path="/thailandIslandNews" element={<ThailandIslandNewsService />} />
          <Route path="/findThaiProperty" element={<FindThaiPropertyService />} />
          <Route
            path="/legallyMarriedInThailand"
            element={<LegallyMarriedInThailandService />}
          />
          <Route
            path="/weddingBoutiquePhuket"
            element={<WeddingBoutiquePhuketService />}
          />
          <Route path="/bicycleThailand" element={<BicycleThailandService />} />
          <Route path="/thaiCapitalist" element={<ThaiCapitalistService />} />
          <Route
            path="/aboutThailandLiving"
            element={<AboutThailandLivingService />}
          />
          <Route
            path="/daveTheRavesThailand"
            element={<DaveTheRavesThailandService />}
          />
          <Route path="/thaiFoodMaster" element={<ThaifoodmasterService />} />
          <Route path="/thailandBail" element={<ThailandBailService />} />
          <Route path="/theSilomer" element={<TheSilomerService />} />
          <Route path="/pattayaPI" element={<PattayaPIService />} />
          <Route path="/budgetCatcher" element={<BudgetCatcherService />} />
          <Route path="/thaiLawyers" element={<ThaiLawyersService />} />
          <Route path="/meanderingTales" element={<MeanderingTalesService />} />
          <Route
            path="/lifestyleInThailand"
            element={<LifestyleInThailandService />}
          />
          <Route path="/thatBangkokLife" element={<ThatBangkokLifeService />} />
          <Route
            path="/thinglishLifestyle"
            element={<ThinglishLifestyleService />}
          />
          <Route path="/fashionGalleria" element={<FashionGalleriaService />} />
          <Route
            path="/thaiLadyDateFinder"
            element={<ThaiLadyDateFinderService />}
          />

          <Route path="/popular-cities-map" element={<PopularCitiesMap />} />

          <Route path="*" element={<NewsService />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;