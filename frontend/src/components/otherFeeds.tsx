import React, { useState, useRef, useEffect } from "react";
import "../resources/otherFeeds.css";

interface BlogLink {
  name: string;
  path: string;
}

const blogLinks: BlogLink[] = [
  { name: "Bangkok Scoop News", path: "/bangkokScoopNews" },
  { name: "Thailand Islands", path: "/thailandIslandNews" },
  { name: "Thailand Property", path: "/findThaiProperty" },
  { name: "Legally Married In Thailand", path: "/legallyMarriedInThailand" },
  { name: "Thai Lady Date Finder", path: "/thaiLadyDateFinder" },
];

const SCROLL_STEP = 50;
const SCROLL_INTERVAL = 30;

const OtherFeeds: React.FC = () => {
  const [autoScroll, setAutoScroll] = useState(true);
  const [isHovered, setIsHovered] = useState(false);
  const scrollRef = useRef<HTMLUListElement>(null);

  useEffect(() => {
    if (!autoScroll || !scrollRef.current) return;

    const scrollStep = () => {
      if (!scrollRef.current) return;

      scrollRef.current.scrollLeft += 1;

      if (
        scrollRef.current.scrollLeft >=
        scrollRef.current.scrollWidth - scrollRef.current.clientWidth
      ) {
        scrollRef.current.scrollLeft = 0;
      }
    };

    const intervalId = setInterval(scrollStep, SCROLL_INTERVAL);

    return () => clearInterval(intervalId);
  }, [autoScroll]);

  const scrollLeft = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollLeft = Math.max(
        0,
        scrollRef.current.scrollLeft - SCROLL_STEP
      );
    }
  };

  return (
    <div
      className="other-feeds"
      onMouseEnter={() => {
        setAutoScroll(false);
        setIsHovered(true);
      }}
      onMouseLeave={() => {
        setAutoScroll(true);
        setIsHovered(false);
      }}
    >
      &nbsp;
      {isHovered && (
        <button className="left-arrow" onClick={scrollLeft} aria-label="Scroll Left">
          &#8592;
        </button>
      )}

      <ul ref={scrollRef}>
        {blogLinks.map((blog, index) => {
          const isActive = window.location.pathname === blog.path;
          return (
            <li key={index}>
              <a
                href={blog.path}
                className={isActive ? "active" : ""}
                onClick={(e) => {
                  if (isActive) e.preventDefault();
                }}
              >
                {blog.name}
              </a>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default OtherFeeds;