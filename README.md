# 🏧 Cajero ATM — JavaFX Banking Simulator

Sistema de cajero automático desarrollado en **Java 21**, con interfaz gráfica en **JavaFX** y persistencia en **MongoDB Atlas**.

Simula las operaciones principales de un ATM real: autenticación, retiros, depósitos, transferencias, cambio de PIN, generación de vouchers y auditoría de operaciones.

---

## ✨ Features

### Core Banking

- 🔐 Autenticación mediante DNI y PIN.
- 💳 Gestión de cuentas bancarias.
- 💵 Retiro de efectivo con control de billetes disponibles.
- 💰 Depósito de dinero.
- 🔄 Transferencias entre cuentas.
- 🔑 Cambio seguro de PIN.
- 🚫 Bloqueo de cuenta.

### Transaction System

- Registro automático de transacciones.
- Historial de operaciones.
- Generación de vouchers.
- Control de fecha y hora de movimientos.

### Audit System

Sistema de auditoría integrado:

- Registro de accesos.
- Intentos fallidos de autenticación.
- Cambios de PIN.
- Operaciones realizadas.
- Eventos importantes del sistema.

Los datos son almacenados en MongoDB Atlas.

---

# 🏗 Architecture

El proyecto utiliza una arquitectura basada en MVC:

```
FXML Views
     |
     ↓
Controllers
     |
     ↓
Models
     |
     ↓
MongoDB Atlas
```

---

# 🛠 Tech Stack

| Tecnología | Uso |
|------------|-----|
| Java 21 | Lenguaje principal |
| JavaFX | Interfaz gráfica |
| FXML | Diseño de vistas |
| MongoDB Atlas | Base de datos |
| MongoDB Driver | Conexión BD |
| IntelliJ IDEA | Desarrollo |

---

# 📦 Project Structure

```
Cajero-ATM
│
├── application
│   │
│   ├── model
│   │   ├── Cajero
│   │   ├── Cuenta
│   │   ├── Usuario
│   │   ├── Transaccion
│   │   ├── Auditoria
│   │   └── MongoManager
│   │
│   ├── controllers
│   │   ├── LoginController
│   │   ├── MenuController
│   │   ├── RetirarDineroController
│   │   ├── TransferenciaController
│   │   ├── CambiarPinController
│   │   └── BloquearCuentaController
│   │
│   └── Main.java
│
└── resources
    └── FXML Views
```

---

# 🚀 Quick Start

## Requirements

- Java JDK 21
- JavaFX SDK
- MongoDB Atlas

---

## Run

Clone the repository:

```bash
git clone https://github.com/usuario/cajero-ATM.git
```

Open the project and run:

```bash
Main.java
```

---

# 🔄 Application Flow

```
Login

 ↓

Main Menu

 ↓

 ├── Withdraw Money
 |
 ├── Deposit Money
 |
 ├── Transfer
 |
 ├── Change PIN
 |
 ├── Account Block
 |
 └── Transaction History
```

---

# 🧩 Design Patterns

## Singleton

Used in:

- `Cajero`
- `MongoManager`

Purpose:

- Maintain a single ATM instance.
- Manage a unique database connection.

---

# ☁ Database

MongoDB collections:

```
atm_db

├── transacciones
└── eventos_auditoria
```

---

# 🔒 Security

Implemented:

- PIN validation.
- Login attempt control.
- Session management.
- Operation auditing.

---

# 📌 Future Improvements

- Real database authentication.
- Encryption of sensitive data.
- User roles (Admin / Client).
- Real ATM hardware integration.
- Advanced reports dashboard.

---

# 👨‍💻 Author

Carlos Alberto Walter Chapoñan

Java ATM Simulator — Academic Project
