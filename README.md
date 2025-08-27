ThaiPulse

  ThaiPulse is a modern news aggregation and publishing platform focused on Thailand.
  It delivers curated content from multiple feeds and presents them with a clean, responsive frontend and a robust backend API.

<======= ####### =======>

🚀 Tech Stack

  Frontend: Vue / React (SPA, built with npm run build)
  
  Backend: Spring Boot (Java, Maven build system)
  
  Database: H2 (dev), can be extended to MySQL/Postgres in production
  
  Server: Nginx (serves frontend + reverse proxy for backend)
  
  Process Management: systemd service for backend

<======= ####### =======>

🖥️ Deployment Guide

🔹 Frontend Setup

  # Navigate to Nginx config
  cd /etc/nginx/sites-available
  
  # Clear old build
  sudo rm -rf /var/www/thaipulse/*
  
  # Move into project frontend
  cd ThaiPulse/frontend
  
  # Build frontend
  npm run build
  
  # Deploy build to web root
  sudo cp -r dist/* /var/www/thaipulse/
  
  # Verify Nginx configuration
  sudo nginx -t
  
  # Restart Nginx
  sudo systemctl restart nginx

<======= ####### =======>

🔹 Backend Setup

  # Navigate to systemd services
  cd /etc/systemd/system/
  
  # Edit backend service (if needed)
  sudo nano thai-pulse-backend.service
  
  # Check service status
  sudo systemctl status thai-pulse-backend
  
  # Build backend
  cd ThaiPulse/backend
  mvn clean install
  mvn clean package
  
  # Reload systemd and restart backend service
  sudo systemctl daemon-reload
  sudo systemctl restart thai-pulse-backend.service
  
  # Check backend logs (live)
  sudo journalctl -u thai-pulse-backend.service -f
  
  # Restart Nginx (if required)
  sudo systemctl restart nginx

<======= ####### =======>

🔧 Service File Example

[Unit]
Description=ThaiPulse Backend Service
After=syslog.target
After=network.target

[Service]
User=ubuntu
ExecStart=/usr/bin/java -jar /home/ubuntu/ThaiPulse/backend/target/backend-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier=thai-pulse-backend

[Install]
WantedBy=multi-user.target

<======= ####### =======>

📡 Access

  Frontend: http://your-server-ip/

  Backend API: http://your-server-ip:8080/

<======= ####### =======>

📝 Notes

  Every time the frontend changes → npm run build + copy to /var/www/thaipulse/.

  Every time the backend changes → mvn clean package + restart the service.

  Logs can be monitored via journalctl.

  Make sure ports 80 (HTTP) and 8080 (Backend API) are open.

<======= ####### =======>

❤️ Contributing

  Pull requests are welcome! Please open an issue first to discuss major changes.
