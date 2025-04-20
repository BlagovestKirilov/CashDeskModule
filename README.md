# Cash Desk Module

![Java](https://img.shields.io/badge/java-17-blue)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.4.4-brightgreen)

A Spring Boot application for managing cash operations with support for multiple currencies and cashiers.

## Features

- 💰 **Multi-currency support** (BGN, EUR)
- 👩💼 **Cashier management** (Martina, Peter, Linda)
- 📊 **Transaction tracking** with date filtering
- 🔐 **API key authentication**
- 📝 **File-based persistence** for transactions and balances
- ✅ **Request validation** for all operations

## API Endpoints

### Cash Balance
GET /api/v1/cash-balance

**Headers:**
- `FIB-X-AUTH: YOUR_API_KEY`
- `Content-Type: application/json`

**Sample Request:**
```json
{
"cashierName": "MARTINA"
}
```

### Cash Operations
POST /api/v1/cash-operation

**Headers:**
- `FIB-X-AUTH: YOUR_API_KEY`
- `Content-Type: application/json`

**Sample Request:**
```json
{
  "operationType": "DEPOSIT",
  "cashierName": "MARTINA",
  "currency": "BGN",
  "denominations": [
    {
      "value": 10,
      "quantity": 5
    },
    {
      "value": 50,
      "quantity": 2
    }
  ]
}
```

📁 Data Files
- Transaction history is stored in: /data/transaction_history.txt
- Cash balances and denominations are stored in: /data/balances.txt

📁 Postman
- Postman collection (API requests) and environment variables are located in: /postman/

📁 Logs
- Application logs are stored in: /logs/
