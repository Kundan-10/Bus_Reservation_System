# Bus Reservation System Portal REST API
<img  src="https://images.unsplash.com/photo-1525962898597-a4ae6402826e?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NDd8fGJ1c3xlbnwwfHwwfHx8MA%3D%3D" width="93%" height="420px" /><img align="right"/>

The Bus Reservation System Portal is a robust and secure platform designed for seamless bus ticket booking. It features efficient CRUD operations, JWT-based authentication, Spring Security, and role-based access control, ensuring a smooth and secure user experience. 🚀

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security (JWT Authentication)**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Lombok**
- **Swagger API Documentation**

## 📌 Main Modules

### 🏷 User Module
- **User Registration & Login** (JWT-based authentication)
- **Search for Available Buses** based on routes and schedules
- **Book a Ticket** for a selected bus
- **View Booking History** and manage reservations
- **Update User Profile**
- **Provide Feedback** for completed trips

### 👨‍💼 Admin Module
- **Admin Authentication & Role-Based Access**
- **Manage Bus Information** (Add, Update, Delete Bus details)
- **Manage Routes** (Add, Update, Delete Routes)
- **View and Manage Users**
- **Access & Manage Reservations**
- **Moderate User Feedback**

### 🚏 Route Module
- Manage bus routes, including **stops and distances**
- Assign routes to specific buses

### 🚌 Bus Module
- Store bus details including **bus type, capacity, operator, and schedule**
- Assign buses to specific routes

### 🎟 Reservation Module
- Allow users to **book, cancel, and modify** reservations
- Track **reservation history and payment status**

### ⭐ Feedback Module
- Users can **rate and review** their journey experience
- Admin can **moderate inappropriate reviews**

## 🚀 Advanced Features

### 🔐 Security & Authentication

- **JWT-based authentication** with role-based access control.
- **Password encryption** using **BCrypt** for security.
- **Session management** to prevent unauthorized access.

### 📈 Additional Enhancements

- **Global Exception Handling** for better API stability.
- **Swagger UI Integration** for interactive API testing.

## 🚀 Installation & Run

### ⚙ Prerequisites

- Java 17+
- MySQL Database
- Maven

### 🔧 Configuration

Before running the API server, update the database configuration inside the `application.properties` file.

```properties
server.port=8888

spring.datasource.url=jdbc:mysql://localhost:3306/ibusdb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root

spring.security.jwt.secret=your_secret_key
spring.security.jwt.expiration=3600000
```

### ▶ Running the Application

```sh
# Clone the repository
git clone https://github.com/your-username/BusReservationSystemPortal.git

# Navigate to the project directory
cd BusReservationSystemPortal

# Build the project
mvn clean install

# Start the application
mvn spring-boot:run
```

## 📄 API Documentation

API documentation is available via **Swagger UI**:

- **Swagger UI:** [http://localhost:8888/swagger-ui/index.html](http://localhost:8888/swagger-ui/index.html)
- **API Root Endpoint:** `http://localhost:8888/`

## 🔗 API Endpoints

### 🔑 Authentication Module (JWT-Based Security)

- `POST /auth/login` - Authenticate and receive JWT token.
- `POST /auth/register` - Register a new user.

**Request:**

```json
{
    "email": "Admin@gmail.com",
    "password": "Admin@123"
}
```

**Response:**

```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCIsdfadfadrte...",
}
```


### Swagger UI

---
### User and User Login Controller

---
![Screenshot (291)](https://user-images.githubusercontent.com/100846744/201393329-4ca0173c-b6fe-46f9-afe0-a977f096687f.png)


![Screenshot (292)](https://user-images.githubusercontent.com/100846744/201393274-7d2f08c6-2fbe-47d2-af20-e85f0cc6482b.png)

---

### Admin and Admin Login Controller

---
![Screenshot (290)](https://user-images.githubusercontent.com/100846744/201393426-bdee2b71-4b89-47c2-b60a-969464088294.png)
![Screenshot (285)](https://user-images.githubusercontent.com/100846744/201393509-babcc11c-8501-4ad9-b8a7-30a5a67615a9.png)

---

### Bus Controller

---


![Screenshot (286)](https://user-images.githubusercontent.com/100846744/201393212-9f8d839e-a6cd-4d9e-aac3-7cd975d04675.png)

---

### Reservation Controller

---


![Screenshot (288)](https://user-images.githubusercontent.com/100846744/201393110-68855185-f04d-4bb8-9af1-9f69bb2ecf5d.png)

---

### Route Controller

---

![Screenshot (289)](https://user-images.githubusercontent.com/100846744/201393162-8a30069f-039a-4010-9fe9-3864452545a6.png)


---

### Feedback Controller

---


![Screenshot (287)](https://user-images.githubusercontent.com/100846744/201393007-95a20ad5-1816-4dc6-8d9c-2ff41c22c450.png)

---


<img  align="center" src="https://readme-typing-svg.herokuapp.com?font=Architects+Daughter&amp;color=FF5733&amp;size=20&amp;lines=Thanks!+For+Visiting+On+My+Project!;See+You+Next-Time+Hope+u+like+its...👨🏻‍💻;" style="width: 70%;">
