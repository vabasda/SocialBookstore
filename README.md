# Online Social Bookstore Application 📚

A full-stack enterprise web application built using **Java** and the **Spring Boot** framework. The project was engineered following **Agile/Scrum methodologies** over three distinct development sprints. It features a robust multi-layered architecture implementing custom repository patterns, a layered domain model, and comprehensive object-oriented design principles to handle secure user flows, peer-to-peer book trading, custom matching logic, and notifications.

---

## 👥 Scrum Team & Agile Governance

The project was executed by an organized Scrum team across explicit user stories and targeted use-case deadlines:

* **Development Team:** Evangelos Basdavanos, Konstantinos Kapsimalis, Romanos Kotsis

### 📉 Sprint Backlog Mapping
* **Sprint 1 (V1.0):** Ingestion and development of core security layers covering `UC1` through `UC5` (Account creation, basic authentication, and setting up initial book offers).
* **Sprint 2 (V2.0):** Implementation of peer interaction mechanics covering `UC6` through `UC9` (Request browsing, acceptance/rejection transaction handlers, user data integration, and asset removal).
* **Sprint 3 (V3.0):** Advanced algorithmic workflows covering `UC10` and `UC11` (Dynamic approximate search patterns and recommendation workflows).

---

## 📁 Architectural Layout & Component Design

The application enforces a strict separation of concerns, mapping database states directly to operational web routes via a robust **MVC (Model-View-Controller)** pattern:

### 1. Controllers Layer (`.Controllers`) ⚙️
* **`HomeController`:** The main orchestration engine connecting the frontend views to the back-end infrastructure. Manages stateful views, endpoints, and session boundaries (`showLoginPage`, `checkLogin`, `logOut`).
* **`HomePage`:** A helper controller class designed to offload heavy querying mechanisms. It manages operational lists like tracking personal inventories (`searchMyOffers`), incoming exchange bids (`searchMyRequests`), and dynamic status updates (`searchMyNotifications`).

### 2. Entities Layer (`.Entities`) 🧬
Maps the application's domain objects using robust composite primary keys (`...Key` classes) for secure relational mapping:
* **`User` & `Book`:** Base schemas storing specific customer profiles and explicit textbook criteria (Title, Author, Category, and Summary).
* **`BookAuthors` & `BookAuthorsKey`:** Establishes highly indexable structural ties linking multiple authors to persistent book assets.
* **`Offer` & `Request`:** Entities tracking user inventory listings and transaction negotiations.
* **`FavourableCategory` & `FavourableAuthor`:** Tracks user-selected preference structures to empower tailored content feeds.
* **`Notification`:** Stores runtime alert signals and communications intended for individual active accounts.

### 3. Repositories Layer (`.Repositories`) 🗄️
Extends core repository patterns to execute clean, decoupled relational queries directly against underlying database entities:
* Interfaces include: `IUserRepository`, `IBookRepository`, `IOfferRepository`, `IRequestRepository`, `INotificationRepository`, `IBookAuthorRepository`, and `IAuthorRepository`.

---

## 🛠️ System Use Cases (Functional Overview)

The software coordinates eleven comprehensive Use Cases to manage interactions end-to-end[cite: 5]:

* **Authentication Sequence (`UC1` - `UC4`):** Directs account routing flows, input validations, username uniqueness checks, error exception messaging, and automated redirection handlers[cite: 5].
* **Inventory Trading Loop (`UC5`, `UC6`, `UC9`):** Empowers authenticated users to generate structured public book listings (`AddBookOffer`) with parameter constraints or scrub active offerings (`DeleteBookOffer`) along with all linked incoming bids[cite: 5, 7].
* **The Transaction Engine (`UC7` & `UC8`):** Processes inbound asset requests[cite: 5]. Accepting a request triggers a transactional state change: the book offer is dropped from public visibility, conflicting open bids are pruned, and matching users receive acceptance and rejection notices along with contact details (`GetInformationOfUser`)[cite: 5].
* **Algorithmic Discovery Engine (`UC10` & `UC11`):** 
  * *Search:* Leverages hybrid semantic query routes to match explicit book targets by strict text matches or approximate query definitions[cite: 7].
  * *Recommendations:* Runs contextual filtering queries to evaluate and deliver recommended offers based on a user's logged preference matrix (`SearchByMyFavouriteCategories`, `SearchByMyFavouriteAuthors`, or a combination of both)[cite: 7, 8].

---

## 💻 Tech Stack & Frameworks
* **Language:** Java 17+
* **Framework:** Spring Boot (Spring MVC, Spring Data JPA)

---
