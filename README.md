# 🏆 Sports Tournament Management System

A comprehensive Java-based digital sports environment designed to streamline tournament management, player development, and fan engagement. This system provides tailored interfaces for administrators, coaches, players, and viewers.

---

## 🚀 Key Features

### 👤 Role-Based Functionality

#### **🛠️ Administrator**
- **User Management**: Oversee the creation and registration of players and coaches.
- **Tournament Control**: Create, manage, and schedule tournaments.
- **System Oversight**: Assign coaches to players and monitor overall system health via reports.

#### **📋 Coach**
- **Training Management**: Create customized training plans (e.g., Batting, Bowling) and assign tasks.
- **Player Development**: Track assigned players, monitor their progress, and provide assessments.
- **Communication**: Send messages and guidance to players.

#### **🏅 Player**
- **Personal Dashboard**: View individual statistics, skill assessments, and training progress.
- **Training Hub**: Access training plans assigned by coaches and track improvement over time.
- **Performance Tracking**: Monitor tournament participation and match history.

#### **📺 Viewer / Guest**
- **Live Match Insights**: View ball-by-ball updates and live commentary.
- **Analytics**: Access team point tables, win probabilities, and venue analysis.
- **Historical Data**: Explore past winners and player timelines.

---

## 💻 Tech Stack & Architecture

- **Language**: Java
- **Data Persistence**: File-based storage system using `.dat` files (`users.dat`, `coaches.dat`, `players.dat`, `tournaments.dat`, `historical_data.dat`).
- **Entry Point**: [UserManagment.java](file:///Users/dani/PersonalGrowth/projects/untitled%20folder/Sports-Tournament-Management-System/UserManagment.java) contains the `main` method and system logic.

---

## 🛠️ Setup & Usage

### Prerequisites
- Java Development Kit (JDK) 8 or higher.

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd Sports-Tournament-Management-System
   ```

### Running the Application
Compile and run the main management class:
```bash
javac UserManagment.java
java UserManagment
```

---

## 📂 Project Structure

| File | Description |
| :--- | :--- |
| `UserManagment.java` | Main entry point; handles login, registration, and role menus. |
| `Admin.java` | logic for administrator operations and user verification. |
| `Coach.java` | Manages coach profiles, players, and training plans. |
| `Player.java` | Handles player data, dashboards, and performance stats. |
| `Tournament.java` | Manages tournament scheduling, point tables, and updates. |
| `PlayerTeamAnalysis.java`| Provides head-to-head reports and AI performance analysis. |
| `Training.java` | Facilitates video uploads and messaging for training sessions. |
| `HistoricalData.java` | Stores and retrieves past records and financial trends. |
| `LiveEngagement.java` | Provides live match updates, commentary, and probabilities. |
| `Technical.java` | Handles system settings, data exports, and API status. |
| `Viewer.java` | Defines viewing capabilities for non-registered users. |

---

## 📊 Data Persistence

The system uses a flat-file database approach for simplicity and portability. Data is stored in comma-separated formats within the following files:
- `users.dat`: Admin credentials and user lists.
- `coaches.dat`: Coach details and player assignments.
- `players.dat`: Player profiles and basic info.
- `tournaments.dat`: Tournament logs and statistics.
- `historical_data.dat`: Historic tournament and player records.

