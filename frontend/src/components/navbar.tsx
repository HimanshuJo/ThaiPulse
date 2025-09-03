import React, { useState, useRef, useEffect } from "react";
import WeatherWidget from "./weatherWidget";
import "../resources/navbar.css";

const cities = [
  "Bangkok",
  "Pattaya",
  "Phuket",
  "Chiang Mai",
  "Hat Yai",
  "Khon Kaen",
  "Nakhon Ratchasima",
];

const Navbar: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [showWeather, setShowWeather] = useState(false);

  const menuRef = useRef<HTMLDivElement>(null);
  const burgerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (
        menuRef.current &&
        burgerRef.current &&
        !menuRef.current.contains(e.target as Node) &&
        !burgerRef.current.contains(e.target as Node)
      ) {
        setIsMenuOpen(false);
        setShowWeather(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <nav className="navbar">

        <ul className="nav-links">
          <li>
            <a
              href="/"
              onClick={(e) => {
                const currentPath = window.location.pathname;
                if (currentPath === "/") e.preventDefault();
              }}
            >
              Home
            </a>
          </li>
          {cities.map((city, index) => {
            const formattedCity = city.toLowerCase().replace(/\s+/g, "-");

            const cityPath = `/navFeeds/city/${formattedCity}`;

            const hostname = window.location.hostname;
            const fullCityPath =
              hostname === "thaipulsetimes.com"
                ? `https://thaipulsetimes.com${cityPath}`
                : cityPath;

            const currentPath = window.location.pathname;
            const isActive = currentPath === cityPath;

            return (
              <li key={index}>
                <a
                  href={fullCityPath}
                  className={isActive ? "active" : ""}
                  onClick={(e) => {
                    if (location.pathname === cityPath) {
                      e.preventDefault();
                    }
                  }}
                >
                  {city}
                </a>
              </li>
            );
          })}
        </ul>

      <div
          className="burger-menu"
          onClick={() => {
            setIsMenuOpen(!isMenuOpen);
            document.body.classList.toggle("menu-open", !isMenuOpen);
          }}
          ref={burgerRef}
          role="button"
          tabIndex={0}
          onKeyPress={(e) => {
              if (e.key === "Enter") {
                const newState = !isMenuOpen;
                setIsMenuOpen(newState);
                document.body.classList.toggle("menu-open", newState);
              }
          }}
      >
        <div className="bar"></div>
        <div className="bar"></div>
        <div className="bar"></div>
      </div>

      {isMenuOpen && (
        <div className="dropdown-menu" ref={menuRef}>
          <div className="dropdown-section">
            {showWeather && (
              <WeatherWidget onClose={() => setShowWeather(false)} />
            )}
            <button onClick={() => setShowWeather(!showWeather)}>
              🌤 Weather
            </button>
            <br />
            <hr />
          </div>
          <div className="dropdown-section">
              <a href="/">🏠 Home</a>
              {cities.map((city, index) => {
                const formattedCity = city.toLowerCase().replace(/\s+/g, "-");
                const cityPath = `/navFeeds/city/${formattedCity}`;
                const hostname = window.location.hostname;
                const fullCityPath =
                  hostname === "thaipulsetimes.com"
                    ? `https://thaipulsetimes.com${cityPath}`
                    : cityPath;
                const isActive = window.location.pathname === cityPath;

                return (
                  <a
                    key={index}
                    href={fullCityPath}
                    className={isActive ? "active" : ""}
                    onClick={(e) => {
                      if (location.pathname === cityPath) e.preventDefault();
                    }}
                  >
                    {city}
                  </a>
                );
              })}
              <hr />
            </div>
            <div className="dropdown-section">
                <a href="/popular-cities-map">🗺️ Popular Cities Map</a>
            </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
