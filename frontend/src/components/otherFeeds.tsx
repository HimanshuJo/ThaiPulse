import React, { useState, useRef, useEffect } from "react";
import "../resources/otherFeeds.css";

interface BlogLink {
  name: string;
  href: string;
}

const BASE_URL =
  window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1"
    ? ""
    : "https://thaipulsetimes.com";

const PREFIX = "feeds";

const blogLinks: BlogLink[] = [
  { name: "About Thailand Living", href: `${BASE_URL}/${PREFIX}/aboutThailandLiving` },
  { name: "Thailand Bail", href: `${BASE_URL}/${PREFIX}/thailandBail` },
  { name: "The Silomer", href: `${BASE_URL}/${PREFIX}/theSilomer` },
  { name: "Thai Lawyers", href: `${BASE_URL}/${PREFIX}/thaiLawyers` },
  { name: "Thinglish Lifestyle", href: `${BASE_URL}/${PREFIX}/thinglishLifestyle` },
  { name: "That Bangkok Life", href: `${BASE_URL}/${PREFIX}/thatBangkokLife` },
  { name: "Bangkok Scoop News", href: `${BASE_URL}/${PREFIX}/bangkokScoopNews` },
  { name: "Thailand Islands", href: `${BASE_URL}/${PREFIX}/thailandIslandNews` },
  { name: "Thailand Property", href: `${BASE_URL}/${PREFIX}/findThaiProperty` },
  { name: "Legally Married In Thailand", href: `${BASE_URL}/${PREFIX}/legallyMarriedInThailand` },
  { name: "Thai Lady Date Finder", href: `${BASE_URL}/${PREFIX}/thaiLadyDateFinder` },
  { name: "Bicycle Thailand", href: `${BASE_URL}/${PREFIX}/bicycleThailand` },
  { name: "Thai Capitalist", href: `${BASE_URL}/${PREFIX}/thaiCapitalist` },
  { name: "Dave The Rave's Thailand", href: `${BASE_URL}/${PREFIX}/daveTheRavesThailand` },
  { name: "Thai Food Master", href: `${BASE_URL}/${PREFIX}/thaiFoodMaster` },
  { name: "Pattaya PI", href: `${BASE_URL}/${PREFIX}/pattayaPI` },
  { name: "Budget Catcher", href: `${BASE_URL}/${PREFIX}/budgetCatcher` },
  { name: "Meandering Tales", href: `${BASE_URL}/${PREFIX}/meanderingTales` },
  { name: "Lifestyle in Thailand", href: `${BASE_URL}/${PREFIX}/lifestyleInThailand` },
  { name: "Fashion Galleria", href: `${BASE_URL}/${PREFIX}/fashionGalleria` },
  { name: "Wedding Boutique Phuket", href: `${BASE_URL}/${PREFIX}/weddingBoutiquePhuket` }
];

const SCROLL_STEP = 50;
const SCROLL_INTERVAL = 20;

const OtherFeeds: React.FC = () => {
  const [autoScroll, setAutoScroll] = useState(true);
  const [isHovered, setIsHovered] = useState(false);
  const scrollRef = useRef<HTMLUListElement>(null);

  useEffect(() => {
    if (!autoScroll || !scrollRef.current) return;

    const scrollStep = () => {
      if (!scrollRef.current) return;

      scrollRef.current.scrollLeft += 2;

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

  const scrollRight = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollLeft = Math.min(
        scrollRef.current.scrollWidth,
        scrollRef.current.scrollLeft + SCROLL_STEP
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
      {isHovered && (
        <>
          <button className="left-arrow" onClick={scrollLeft} aria-label="Scroll Left">
            &#8592;
          </button>
          <button className="right-arrow" onClick={scrollRight} aria-label="Scroll Right">
            &#8594;
          </button>
        </>
      )}

      <ul ref={scrollRef}>
        {blogLinks.map((blog, index) => {
          // For active detection: normalize without BASE_URL
          const currentPath = window.location.pathname;
          const blogPath = new URL(blog.href, window.location.origin).pathname;
          const isActive = currentPath === blogPath;

          return (
            <li key={index}>
              <a
                href={blog.href}
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
