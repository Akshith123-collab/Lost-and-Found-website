🧾 Lost & Found Management System

A full-stack Lost and Found Management System built using Vue.js (frontend) and Spring Boot (backend) with MongoDB for persistent data storage.
The system allows users to report lost or found items, view others’ posts, and manage their own submissions securely using JWT-based authentication.

🚀 Features
👤 Authentication & Authorization

User registration and login (JWT-based)

Role-based access (USER / ADMIN)

Secure password storage using BCrypt

📦 Item Management

Add Lost or Found items with image upload

View all items or only your submissions

Edit and Delete your own posts

Default status set to Lost

Real-time validation for description and contact info

🖼️ Image Handling

Upload item images stored on the backend

Automatically generated accessible image URLs

Display images in responsive cards

🧑‍💼 Admin Capabilities

View all items (both lost and found)

Manage or moderate posts

JWT-secured access

🏗️ Tech Stack

Layer	Technology

Frontend	Vue.js 3, Vite, Tailwind CSS, Axios

Backend	Spring Boot 3, Java 17, Spring Security (JWT)

Database	MongoDB (NoSQL)

Auth	JSON Web Tokens (JWT)

Storage	File-based image upload via MultipartFile

📁 Project Structure

🖥️ Frontend (Vue)

frontend/

│

├── src/

│   ├── api/axios.js            # Axios setup with JWT interceptor

│   ├── components/

│   │   ├── AddItemForm.vue

│   │   ├── ItemCard.vue

│   │   ├── MyItemCard.vue

│   ├── pages/

│   │   ├── HomePage.vue

│   │   ├── MyItemsPage.vue

│   │   ├── Login.vue

│   │   ├── Register.vue

│   ├── store/AuthStore.js      # Pinia store for auth

│   ├── router/index.js         # Vue Router setup

│   └── App.vue

│

└── vite.config.js


⚙️ Backend (Spring Boot)

backend/

│

├── com.ssn.registration/

│   ├── config/SecurityConfig.java

│   ├── controller/

│   │   ├── AuthController.java

│   │   ├── ItemController.java

│   ├── model/

│   │   ├── Item.java

│   │   ├── User.java

│   ├── repository/

│   │   ├── ItemRepository.java

│   │   ├── UserRepository.java

│   ├── security/

│   │   ├── JwtAuthFilter.java

│   │   ├── JwtService.java

│   ├── service/

│   │   ├── UserService.java

│   │   ├── ItemService.java

│   ├── payload/

│   │   ├── CreateItemRequest.java

│   │   ├── LoginRequest.java

│   │   ├── RegisterRequest.java

│   └── RegistrationApplication.java

│

└── src/main/resources/application.properties

⚙️ Setup Instructions

1️⃣ Backend Setup

# Clone the project

git clone https://github.com/<your-username>/lost-and-found.git

cd lost-and-found/backend

# Open in IntelliJ / Eclipse

# Configure MongoDB connection in application.properties

spring.data.mongodb.uri=mongodb://localhost:27017/lostfound

# Run the application

mvn spring-boot:run


The backend will start at:

👉 http://localhost:8080

2️⃣ Frontend Setup

cd ../frontend

# Install dependencies

npm install

# Run the app

npm run dev


The frontend will start at:

👉 http://localhost:5173

🔐 JWT Authentication Flow

User registers and logs in to receive a JWT token.

Token is stored in localStorage and sent in Authorization headers.

Backend validates token and grants access to secured routes.

📸 Screenshots (optional)

You can include screenshots like:

Home Page

Add Item Form

My Items Page

Admin Dashboard

🧩 API Endpoints

Method	Endpoint	Description

POST	/api/auth/register	Register new user

POST	/api/auth/login	Login and receive JWT

GET	/api/items	Get all items

GET	/api/items/my	Get user’s own items

POST	/api/items	Add new item

PUT	/api/items/{id}	Update item

DELETE	/api/items/{id}	Delete item

GET	/uploads/{filename}	Access uploaded image

🧠 Validation Rules

Add Item Form:

Title: minimum 3 characters

Description: minimum 10 characters

Reporter Contact: must be a valid email or phone number

Reporter Name: minimum 2 characters

Submit button is disabled until all validations pass

🧑‍💻 Contributors

Akshith Viswanathan — Full Stack Developer (Vue + Spring Boot)

Open to contributions! Feel free to fork and improve 🚀

📝 License

This project is licensed under the MIT License.

You are free to use, modify, and distribute it with attribution.
