import React from "react";
import "../resources/footer.css";

const Footer: React.FC=()=>{
    return (
        <footer className="footer">
            <div className="footer-content">
                <p>© 2025 THAI PULSE. All rights reserved. </p>
                <div className="footer-links">
                    <a href="/terms">Terms</a>
                    <a href="/privacy">Privacy</a>
                </div>
            </div>
        </footer>
    );
};

export default Footer;