import React from "react";
import "../resources/leftTable.css";

interface BlogLink {
  name: string;
  path: string;
}

const blogLinks: BlogLink[] = [
  { name: "Bangkok Scoop News", path: "/bangkokScoopNews" },
  { name: "Bangkok Life", path: "/bangkokLife" },
  { name: "Pattaya News", path: "/pattayaNews" },
  { name: "Phuket News", path: "/phuketNews" },
  { name: "Chiang Mai News", path: "/chiangMaiNews" },
  { name: "Thai Food Blog", path: "/thaiFood" },
  { name: "Travel Tips", path: "/travelTips" },
  { name: "Cultural Events", path: "/culturalEvents" },
  { name: "Sports", path: "/sports" },
  { name: "Music & Arts", path: "/musicArts" },
  { name: "Business", path: "/business" },
  { name: "Technology", path: "/technology" },
  { name: "Lifestyle", path: "/lifestyle" },
  // Add more here...
];

const LeftTable: React.FC = () => {
  return (
    <div className="left-table">
      <ul>
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

export default LeftTable;
