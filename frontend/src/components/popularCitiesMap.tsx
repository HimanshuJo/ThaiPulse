import React, { useEffect, useState, useRef } from "react";
import Navbar from "../components/navbar";
import Footer from "../components/footer";

declare const L: any;

const API_BASE =
  window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1"
    ? "http://localhost:8080"
    : "https://thaipulsetimes.com";

const PopularCitiesMap: React.FC = () => {
  const [cities, setCities] = useState<any[]>([]);
  const [startCity, setStartCity] = useState<string>("");
  const [endCity, setEndCity] = useState<string>("");

  const mapRef = useRef<any>(null);
  const routingControlRef = useRef<any>(null);

    const burgerMenu = document.querySelector(".burger-menu") as HTMLElement | null;

    if(burgerMenu){
      if (window.location.pathname.includes("popular-cities-map")) {
        burgerMenu.style.display = "none";
      } else {
        burgerMenu.style.display = "flex";
      }
    }

  useEffect(() => {
    if (!mapRef.current) {
      const map = L.map("popular-cities-map", {
        zoomControl: true,
        attributionControl: true,
        scrollWheelZoom: true,
      }).setView([13.7563, 100.5018], 6);

      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        maxZoom: 19,
        detectRetina: true,
      }).addTo(map);

      mapRef.current = map;
    }

    fetch(`${API_BASE}/api/cities`)
      .then((res) => {
        if (!res.ok) return res.json().then((text) => Promise.reject(text));
        return res.json();
      })
      .then((data) => {
        setCities(data);

        data.forEach((city: any) => {
          const marker = L.marker([city.lat, city.lng])
            .addTo(mapRef.current)
            .bindPopup(`<b>${city.name}</b>`);

          marker.on("click", () => {
            mapRef.current.flyTo([city.lat, city.lng], 14, { animate: true, duration: 1.2 });
            marker.openPopup();
          });
        });
      })
      .catch((err) => console.error("Error fetching cities:", err));
  }, []);

  useEffect(() => {
    if (!startCity || !endCity || cities.length === 0 || !mapRef.current) return;

    const start = cities.find((c) => c.name === startCity);
    const end = cities.find((c) => c.name === endCity);
    if (!start || !end) return;

    if (routingControlRef.current) {
      mapRef.current.removeControl(routingControlRef.current);
    }

    const routingControl = L.Routing.control({
      waypoints: [L.latLng(start.lat, start.lng), L.latLng(end.lat, end.lng)],
      lineOptions: { styles: [{ color: "#0074D9", weight: 5 }] },
      router: L.Routing.osrmv1({ serviceUrl: "https://router.project-osrm.org/route/v1" }),
      show: true,
      addWaypoints: false,
      routeWhileDragging: false,
      draggableWaypoints: false,
    }).addTo(mapRef.current);

    routingControlRef.current = routingControl;
  }, [startCity, endCity, cities]);

  return (
    <div
      style={{
        height: "300vh",
        width: "80vw",
        display: "flex",
        flexDirection: "column",
        margin: 0,
        padding: 0,
        overflow: "hidden",
      }}
    >
      <Navbar />
      <div style={{ padding: "10px 20px", background: "#f0f0f0", zIndex: 2000, position: "relative", justifyContent: "flex-end" }}>
        <select value={startCity} onChange={(e) => setStartCity(e.target.value)} style={{ marginRight: 10 }}>
          <option value="">Select Start City</option>
          {cities.map((city) => (
            <option key={city.name} value={city.name}>{city.name}</option>
          ))}
        </select>

        <select value={endCity} onChange={(e) => setEndCity(e.target.value)}>
          <option value="">Select End City</option>
          {cities.map((city) => (
            <option key={city.name} value={city.name}>{city.name}</option>
          ))}
        </select>
      </div>

      <div id="popular-cities-map" style={{
         flex: 1,
        }}>
      </div>

      <footer
        style={{
          background: "#333",
          color: "white",
          padding: "10px 20px",
          flexShrink: 0,
          textAlign: "center",
        }}
      >
        &copy; {new Date().getFullYear()} Thailand Travel Guide
      </footer>
      <Footer />
    </div>
  );
};

export default PopularCitiesMap;
