# 🚀 Cloud-Native URL-Shortener
Eine Skalierbare URL-Shortener-Anwendung in Microservice-Architektur welche auf Spring-Boot basiert.
Der URL-Shortener kürzt entgegengenommene Links und bietet bei Bedarf Statistiken zu den Links an.

## ✨ Features
* Kürzen von langen URLs in kompakte Links
* Bereitstellen der Klick-Statistiken für ein Link (Wie oft wurde Link geklickt)
* Verwaltung der Links mit eigenen Workspaces
* Microservice-Architektur bei der jeder Service unabhängig läuft
usage
## 🛠️ Tech Stack
* **Backend:** Java 17, Spring Boot, Maven
* **Datenbank:** MySQL (Docker), H2 (für lokale Tests)
* **Infrastruktur & DevOps:** Docker, Docker Compose, GitHub Actions (CI/CD), Google Cloud 

## 🏗️ Architektur
as System ist in vier eigenständige Microservices unterteilt, die jeweils über eine eigene, isolierte Datenbank verfügen:
* **Client-Service:** Dient als primäre Benutzeroberfläche und Schnittstelle zu den Backend-Diensten.
* **Shortener-Service:**  Übernimmt die Kernlogik zur Generierung und Auflösung der Kurz-URLs.
* **Workspace-Service:** Verwaltet die Benutzerbereiche und die Zuordnung der jeweiligen Links.
* **Stats-Service:** Sammelt die Aufrufstatistiken und Klickzahlen der verarbeiteten Links.

## 📀 Installation
**Voraussetzung:** Auf dem lokalen System müssen Docker und Docker Compose installiert sein!  
1. **Klone Projekt**  
```shell
gh repo clone xxxatacion/url-shortener
```

2. **Setze Umgebungsvariablen**  
   (In url-shortener dir)
```shell
cat <<EOF > .env
SHORTENER_PUBLIC_URL=http://localhost:8080/api/shortLinks/
MYSQL_ROOT_PASSWORD=UR_MYSQL_ROOT_PASSWORD
EOF
```
3. **Services starten**  
    (In url-shortener dir)

```shell
docker-compose up -d --build
```
4. **Anwendung öffnen**  
<http://localhost>


## 📖 Nutzung 
