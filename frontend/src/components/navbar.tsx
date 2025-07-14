import React, { useState } from "react";
import "../resources/navbar.css";

const cities = [
  "Bangkok",
  "Chiang Mai",
  "Phuket",
  "Pattaya",
  "Khon Kaen",
  "Hat Yai",
  "Nakhon Ratchasima",
];

const Navbar: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <nav className="navbar">
      <ul className="nav-links">
        {cities.map((city, index) => (
          <li key={index}>
            <a href={`/city/${city.toLowerCase().replace(/\s+/g, "-")}`}>
              {city}
            </a>
          </li>
        ))}
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
