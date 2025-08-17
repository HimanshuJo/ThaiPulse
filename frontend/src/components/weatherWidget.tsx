import React, { useEffect, useRef, useState } from "react";
import "../resources/weatherWidget.css";

type WeatherDto = {
  city: string;
  tempC: number;
  description: string;
  icon: string;
  humidity: number;
  windKph: number;
  updatedAt: string;
};

const CITIES = [
  "Bangkok",
  "Phuket",
  "Pattaya",
  "Chiang Mai",
  "Hat Yai",
  "Khon Kaen",
  "Nakhon Ratchasima"
];

const API_BASE =
  window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1"
    ? "http://localhost:8080"
    : "https://thaipulsetimes.com";

const WeatherWidget: React.FC<{ onClose: () => void }> = ({ onClose }) => {
  const [city, setCity] = useState("Bangkok");
  const [data, setData] = useState<WeatherDto | null>(null);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState<string | null>(null);
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (ref.current && !ref.current.contains(event.target as Node)) {
        onClose();
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [onClose]);

  const load = async (c: string) => {
    try {
      setLoading(true);
      setErr(null);

      const res = await fetch(`${API_BASE}/api/weather/${encodeURIComponent(c)}`, {
        headers: { Accept: "application/json" }
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const json = await res.json();
      setData(json);
    } catch (e: any) {
      setErr(e.message || "Failed to load weather");
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load(city);
  }, [city]);

  return (
    <div ref={ref} className="weather-widget">
      <div className="weather-header">
        <select
          className="city-select"
          value={city}
          onChange={(e) => setCity(e.target.value)}
        >
          {CITIES.map((c) => (
            <option key={c} value={c}>
              {c}
            </option>
          ))}
        </select>
        {loading && <span className="loading">Loading…</span>}
      </div>

      {err && <div className="error">{err}</div>}

      {data && (
        <div className="weather-content">
          <div className="temp">{Math.round(data.tempC)}°C</div>
          <div className="desc">
            <img
              alt={data.description}
              src={`https://openweathermap.org/img/wn/${data.icon}@2x.png`}
            />
            <span>{data.description}</span>
          </div>
          <div className="meta">
            <span>💧 {data.humidity}%</span>
            <span>💨 {data.windKph.toFixed(1)} km/h</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default WeatherWidget;
