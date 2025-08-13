import React, { useState } from "react";
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
      <div className="burger-menu" onClick={() => setIsMenuOpen(!isMenuOpen)}>
        <div className="bar"></div>
        <div className="bar"></div>
        <div className="bar"></div>
      </div>
      {isMenuOpen && (
        <div className="dropdown-menu">
          <a href="/login">Login</a>
          <a href="/register">Register</a>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
