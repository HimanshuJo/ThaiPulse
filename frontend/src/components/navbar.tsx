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
          const cityPath = `/city/${formattedCity}`;
          const currentPath = window.location.pathname;
          const isActive = currentPath === cityPath;

          return (
            <li key={index}>
              <a
                href={cityPath}
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
        onClick={() => setIsMenuOpen(!isMenuOpen)}
        ref={burgerRef}
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
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
