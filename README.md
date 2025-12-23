# Restourant Project 🍽️

Repository ini berisi project pembuatan website restoran sederhana menggunakan bahasa pemrograman **Java**. Project ini dikembangkan untuk mengelola data atau tampilan terkait layanan restoran secara digital.

## 🚀 Fitur Utama

- **Authentication & Authorization**:
  ✅ Login/Register dengan JWT Token

✅ Role-based Access Control (USER, ADMIN)

✅ Token Management dengan expiry time

✅ Password Encryption menggunakan BCrypt

- **User Management**;
  ✅ CRUD Operations untuk user management

✅ Role Assignment (multiple roles per user)

✅ User Status (active/inactive toggle)

✅ Profile Management (update nama, telepon, email)

- **Menu Management**:
  ✅ CRUD Menu Items (makanan & minuman)

✅ Kategori Management (makanan, minuman, etc.)

✅ Stock Management dengan tracking stok

✅ Availability Toggle (tersedia/tidak)

✅ Recommended Items flag untuk menu spesial

- **Order System**:
  ✅ Create Orders dengan multiple items

✅ Order Status Tracking (PENDING, PROCESSING, COMPLETED, CANCELLED)

✅ Order History untuk setiap user

✅ Invoice Generation dengan detail pesanan

- **Table Reservation**:
  ✅ Meja Management (nomor meja, kapasitas, status)

✅ Online Reservation oleh customer

✅ Reservation Status (CONFIRMED, PENDING, CANCELLED)

✅ Reservation History

- **Fitur Lanjutan**
  ✅ Pagination & Filtering di semua listing pages

✅ Search Functionality dengan multiple criteria

✅ Email Notification untuk order & reservation

✅ Audit Logging untuk tracking aktivitas

✅ Export Data ke Excel/PDF

✅ Dashboard Statistics untuk admin

- **Clean UI**: Tampilan yang responsif dan mudah digunakan.

- **Backend Robust**: Dibangun menggunakan Java dengan struktur Maven.

## 🛠️ Tech Stack

- **Backend (Spring Boot 3)**
  Java 21 dengan Spring Boot 3.2.0

Spring Security dengan JWT Authentication

Spring Data JPA untuk database operations

MySQL 8 sebagai database utama

Maven untuk dependency management

Lombok untuk mengurangi boilerplate code

- **Security Features**
  ✅ JWT Token-based Authentication

✅ Role-based Authorization (@PreAuthorize)

✅ Password Encryption (BCrypt)

✅ CORS Configuration

✅ Input Validation (@Valid annotations)

✅ SQL Injection Protection (JPA Parameterized Queries)

## 📊 Database Schema

┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
│ users │ │ roles │ │ user_roles │
├─────────────────┤ ├─────────────────┤ ├─────────────────┤
│ id │◄───┤ id │ │ user_id │
│ username │ │ name │ │ role_id │
│ password │ │ │ │ │
│ email │ │ │ │ │
│ nama │ └─────────────────┘ └─────────────────┘
│ nomor_telepon │
│ aktif │
│ created_at │ ┌─────────────────┐ ┌─────────────────┐
│ updated_at │ │ menus │ │ order_items │
└─────────────────┘ ├─────────────────┤ ├─────────────────┤
│ id │◄───┤ id │
┌─────────────────┐ │ nama │ │ order_id │
│ orders │ │ deskripsi │ │ menu_id │
├─────────────────┤ │ harga │ │ quantity │
│ id │ │ kategori │ │ subtotal │
│ user_id │ │ gambar_url │ │ │
│ total_harga │ │ tersedia │ └─────────────────┘
│ status │ │ recommended │
│ tanggal_pesan │ │ stok │ ┌─────────────────┐
│ created_at │ │ created_at │ │ reservations │
│ updated_at │ │ updated_at │ ├─────────────────┤
└─────────────────┘ └─────────────────┘ │ id │
│ user_id │
┌─────────────────┐ │ tanggal_reservasi│
│ tables │ │ jumlah_orang │
├─────────────────┤ │ catatan_khusus │
│ id │ │ status │
│ nomor_meja │ │ created_at │
│ kapasitas │ │ updated_at │
│ status │ └─────────────────┘
│ created_at │
│ updated_at │
└─────────────────┘

## 📋 Prasyarat

Sebelum menjalankan project ini, pastikan kamu sudah menginstal:

- [Java Development Kit (JDK) 17+](https://www.oracle.com/java/technologies/downloads/) atau versi yang sesuai.
- [Apache Maven](https://maven.apache.org/download.cgi).
- IDE seperti IntelliJ IDEA, Eclipse, atau VS Code.

## ⚙️ Cara Instalasi & Menjalankan

1.  **Clone repository:**
    ```bash
    git clone [https://github.com/Rxg1l/Restourant.git](https://github.com/Rxg1l/Restourant.git)
    ```
2.  **Masuk ke direktori project:**
    ```bash
    cd Restourant
    ```
3.  **Build project menggunakan Maven:**

    ```bash
    ./mvnw clean install
    ```

4.  **Default Credential**
    Admin: admin / admin123

    User: user / user123

5.  **Jalankan aplikasi:**
    ```bash
    ./mvnw spring-boot:run
    ```
    _(Gunakan perintah running yang sesuai dengan frameworkmu)_

## 📂 Struktur Folder

- `src/main/java`: Source code logika bisnis aplikasi.
- `src/main/resources`: File konfigurasi dan aset statis.
- `pom.xml`: File konfigurasi dependensi Maven.

---

## API Endpoints

1. **Authentication**
   POST /api/auth/login # LOGIN user
   POST /api/auth/register # REGISTER new User
   POST /api/auth/refresh # REFRESH JWT Token

2. **User Management**
   GET /api/users/{id} # GET User by id (Admin Only)
   PUT /api/users/{id} # Update User
   DELETE /api/users/{id} # Delete User
   GET /api/users # GET All user (Admin Only)
   POST /api/users # CREATE new User
   PATCH /api/users/{id}/status # Update user status (Admin Only)

3. **Menu Management**
   GET /api/menu/{id} # GET menu by id
   PUT /api/menu/{id} # UPDATE menu (Admin Only)
   DELETE /api/menu/{id} # DELETE menu (Admin Only)
   GET /api/menu # GET Allmenu
   POST /api/menu # CREATE new menu (Admin Only)  
   GET /api/menu/search # Search menu
   GET /api/menu/kategori/{kategori} # Search menu by kategori

4. **Order Management**
   PUT /api/pemesanan/{id}/status # UPDATE Order status
   GET /api/pemesanan # GET All Order (Admin Only)
   POST /api/pemesanan # CREATE new Order
   GET /api/pemesanan/{id} # GET Order by id
   DELETE /api/pemesanan/{id} # DELETE Order
   GET /api/pemesanan/today # Get Order today (Admin Only)
   GET /api/pemesanan/status/{status} # Get Order by status

5. **Pelayan Management**
   GET /api/pelayan/{id} # GET Pelayan by id
   PUT /api/pelayan/{id} # UPDATE Pelayan (Admin Only)
   DELETE /api/pelayan/{id} # DELETE Pelayan (Admin Only)
   GET /api/pelayan # GET All Pelayan
   POST /api/pelayan # CREATE new Pelayan (Admin Only)
   GET /api/pelayan/search # SEARCH Pelayan

## 🔒 Security Implementation

1. **JWT Token Flow**

   - User Login → Generate JWT Token
   - Token disimpan di localStorage
   - Setiap request include token di Authorization header
   - Backend validate token & check permissions
   - Token refresh sebelum expiry

2. **Role Permissions**
   - @PreAuthorize("hasRole('ADMIN')") // Admin only
   - @PreAuthorize("hasRole('USER')") // User only
   - @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // Multiple roles

## 🧪 Testing

1. **Unit Test**

   # Run all tests

   mvn test

   # Run specific test class

   mvn test -Dtest=UserServiceTest

## 📈 Performance Optimization

1. **Database Optimization**
   - Indexing pada frequently queried columns
   - Pagination untuk large datasets
   - Caching dengan Spring Cache
   - Connection pooling dengan HikariCP

## 🐛 Troubleshooting

**Common Issues**

1.  Database Connection Failed

    - Cek MySQL service running

    - Verify credentials di application.properties

2.  JWT Token Expired

    - Login ulang untuk mendapatkan token baru

    - Implement refresh token mechanism

3.  Corse Error

    - Verify CORS configuration di SecurityConfig

    - Check frontend URL whitelist

Dibuat dengan ❤️ oleh [Rxg1l](https://github.com/Rxg1l)
