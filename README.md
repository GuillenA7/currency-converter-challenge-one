# 🪙 Currency Converter – Java Backend Challenge (Oracle ONE | Alura Latam)

## 📋 Project Description

The **Currency Converter** is a console-based Java application that allows users to convert monetary values between different currencies using **real-time exchange rates** obtained from the [ExchangeRate API](https://www.exchangerate-api.com/).

This project was developed as part of the **[Oracle ONE](https://www.oracle.com/mx/education/oracle-next-education/) + [Alura Latam](https://www.aluracursos.com/) Java Backend specialization**, focusing on:

* Consuming APIs in Java
* Managing JSON with Gson
* Applying Object-Oriented Programming
* Handling exceptions
* Working with modular and maintainable code

---

## 🚀 Features

✅ Conversion between multiple currencies (USD, ARS, BRL, COP, etc.)

✅ Real-time rates via REST API (ExchangeRate API)

✅ JSON parsing with **Gson 2.10.1**

✅ Menu-driven console interface using **Scanner**

✅ Clean modular structure with reusable classes

✅ BigDecimal for precise monetary calculations

✅ Error handling for invalid inputs and API issues

---

## 🧩 Tech Stack

| Tool / Library                 | Description                            |
| ------------------------------ | -------------------------------------- |
| **Java 17+**                   | Main programming language              |
| **HttpClient (java.net.http)** | HTTP requests to the API               |
| **Gson (by Google)**           | JSON serialization/deserialization     |
| **IntelliJ IDEA**              | IDE used for development               |
| **Git & GitHub**               | Version control and repository hosting |

---

## 🧠 Project Architecture

```
src/
 ├── model/
 │   └── Currency.java
 ├── service/
 │   ├── ExchangeRateClient.java
 │   └── CurrencyConverter.java
 ├── util/
 │   └── CurrencyFilter.java
 └── Main.java
```

* **ExchangeRateClient** → Handles API calls and parses JSON responses.
* **CurrencyConverter** → Performs precise currency calculations.
* **CurrencyFilter** → Filters allowed currency codes (ARS, BRL, COP, USD, etc.).
* **Main** → Provides an interactive menu for users.

---

## ⚙️ Setup and Installation

### 1. Clone this repository:

```bash
git clone https://github.com/GuillenA7/currency-converter-challenge-one.git
cd currency-converter-java
```

### 2. Configure API Key:

1. Get your free key from [ExchangeRate API](https://www.exchangerate-api.com/).
2. Set it as an environment variable:

   ```bash
   export EXCHANGE_RATE_API_KEY=your_api_key_here
   ```

   *(On Windows PowerShell)*

   ```powershell
   setx EXCHANGE_RATE_API_KEY "your_api_key_here"
   ```

### 3. Run the app

```bash
javac -d out src/**/*.java
java -cp out Main
```

---

## 💡 Example Console Output

```
****************************************************
*        Welcome to the Currency Converter         *
****************************************************
1) US Dollar  =>  Argentine Peso   (USD -> ARS)
2) Argentine Peso  =>  US Dollar   (ARS -> USD)
3) US Dollar  =>  Brazilian Real   (USD -> BRL)
4) Brazilian Real  =>  US Dollar   (BRL -> USD)
5) US Dollar  =>  Colombian Peso   (USD -> COP)
6) Colombian Peso  =>  US Dollar   (COP -> USD)
7) Exit

Choose a valid option: 1
Enter the amount to convert (USD): 25
-----------------------------------------------
25.00 USD @ rate 1012.34 = 25308.50 ARS
-----------------------------------------------
```

---

## 🧪 Testing

* **Unit tested** core logic (`CurrencyConverter`) for precision and error handling.
* **Manual testing** with various currency pairs and invalid inputs.
* **Error simulations** (e.g., invalid API key, negative values, no internet connection).

---

## 🧰 Future Improvements

* Add graphical interface (Swing or JavaFX)
* Implement more currencies dynamically from `/latest/{base}`
* Include caching to reduce API calls
* Create automated JUnit tests

---

## 👩‍💻 Author

**Jose Adrian Guillen Lamas**

Oracle ONE – Java Backend Student

📫 [LinkedIn](https://www.linkedin.com/in/jose-adrian-guillen-lamas-3b3b5135b/)

📂 [GitHub](https://github.com/GuillenA7)

---

## 🏆 Acknowledgments

Project developed as part of the **[Oracle ONE](https://www.oracle.com/mx/education/oracle-next-education/) + [Alura Latam](https://www.aluracursos.com/) Program**

Instructor: *Génesys Rondón*

Special thanks to the Alura team for guidance and resources.

---

## 🪪 License

This project is licensed under the **MIT License** — feel free to use and modify it for learning or professional purposes.
