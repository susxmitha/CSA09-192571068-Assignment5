# CSA09 – Programming in Java
## Assignment 5 – CO5: Data Persistence Engineering – Files and Databases

### Student Details

| Field | Details |
|---|---|
| Student Name | S. Susmitha |
| Register Number | 192571068 |
| Course Code | CSA09 |
| Course Name | Programming in Java |
| Assignment | Assignment 5 – CO5 |

---

## 1. Project Overview

This project focuses on implementing and evaluating different Java data persistence techniques using file handling, buffered I/O, object serialization, JDBC connectivity, database transactions, and batch processing.

The assignment investigates the performance, reliability, security, and scalability of file-based and database-based persistence approaches.

The implementation covers eight tasks, including practical programs, experimental investigations, test cases, and supporting evidence.

### Objectives

- Understand Java file handling and data persistence.
- Implement buffered and unbuffered file operations.
- Design reusable Data Access Object (DAO) structures.
- Implement object serialization and deserialization.
- Understand JDBC connectivity and database transactions.
- Prevent SQL injection using PreparedStatement.
- Compare Statement, PreparedStatement, and batch processing.
- Perform file-to-database migration.
- Investigate database metadata and persistence reliability.

---

## 2. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| JDBC | Database connectivity |
| MySQL | Relational database |
| MySQL Connector/J | JDBC driver |
| File I/O | File-based persistence |
| Buffered I/O | Efficient file processing |
| Object Serialization | Object persistence |
| Git and GitHub | Version control and submission |
| Visual Studio Code / GitHub Codespaces | Development environment |

**Java Version:** OpenJDK 21.0.13 (Windows 11).

---

## 3. Project Structure

```text
CSA09-192571068-Assignment5/
│
├── Task01/
│   ├── PassStore.java
│   └── FindGrowthBenchmark.java
│
├── Task02/
│   └── LineCounter.java
│
├── Task03/
│   └── StudentDaoDemo.java
│
├── Task04/
│   └── SerializationDemo.java
│
├── Task05/
│   └── JdbcSafetyDemo.java
│
├── Task06/
│   ├── PassLoader.java
│   └── TransactionComparison.java
│
├── Task07/
│   └── MigrationAndMetadata.java
│
├── Task08/
│   └── AdversarialFileTest.java
│
├── inputs/
│   └── bus_passes.csv
│
├── sql/
│   └── schema.sql
│
├── docs/
│   ├── PartA_Design_and_Analysis.md
│   ├── PartB_Experiments.md
│   └── measured_results.csv
│
├── evidence/
│   ├── Q1/
│   ├── Q2/
│   ├── Q3/
│   ├── Q4/
│   ├── Q5/
│   ├── Q6/
│   ├── Q7/
│   ├── Q8/
│   └── README.md
│
└── README.md
```

---

## 4. Task-wise Implementation

### Task 1 – Bus Pass File Store

**Files:** `PassStore.java`, `FindGrowthBenchmark.java`

Implemented file-based storage and retrieval of bus-pass records.

Key features:
- Adding bus-pass records.
- Finding records using pass ID.
- Listing stored records.
- Handling malformed or damaged records.
- Investigating search performance as the number of records increases.

### Task 2 – Buffered I/O Benchmark

**File:** `LineCounter.java`

Compared unbuffered and buffered file-reading approaches.

Key features:
- Byte-by-byte file reading.
- Buffered file reading.
- Processing files of different sizes.
- Measuring execution time.
- Comparing performance across repeated runs.

### Task 3 – DAO Design

**File:** `StudentDaoDemo.java`

Demonstrated the Data Access Object design pattern.

Key features:
- Separation of data access and application logic.
- Reusable DAO interface design.
- Student data access implementation.
- Comparison with embedded SQL approaches.

### Task 4 – Object Serialization

**File:** `SerializationDemo.java`

Implemented Java object serialization and deserialization.

Key features:
- Object persistence.
- Serialization of related objects.
- Use of transient fields.
- Serial version compatibility considerations.

### Task 5 – JDBC Transactions and SQL Injection

**File:** `JdbcSafetyDemo.java`

Investigated SQL injection vulnerabilities and transaction management.

Key features:
- Unsafe SQL query construction.
- PreparedStatement-based query execution.
- SQL injection testing.
- Wallet balance transfer.
- Commit and rollback operations.

### Task 6 – JDBC Batch Processing

**Files:** `PassLoader.java`, `TransactionComparison.java`

Compared database insertion approaches.

Key features:
- Statement execution.
- PreparedStatement execution.
- Batch insertion.
- Auto-commit comparison.
- Transaction rollback testing.
- ResultSetMetaData investigation.

### Task 7 – Database Migration and Metadata

**File:** `MigrationAndMetadata.java`

Implemented file-to-database migration.

Key features:
- Reading records from CSV files.
- Validating input records.
- Batch insertion into MySQL.
- Handling invalid records.
- DatabaseMetaData investigation.

### Task 8 – Adversarial File Validation

**File:** `AdversarialFileTest.java`

Tested file-processing reliability using malformed and unexpected input data.

Key features:
- Invalid record detection.
- Incorrect field-count handling.
- Invalid numeric-value handling.
- Malformed and empty-line validation.

---

## 5. Experimental Results

The following results were obtained during the experimental runs.

### 5.1 Buffered vs Unbuffered File Reading

| File Size | Unbuffered Time (ms) | Buffered Time (ms) | Speedup |
|---|---:|---:|---:|
| 1 MB | 2516.953 | 49.213 | 51.1× |
| 10 MB | 52708.138 | 98.685 | 534.2× |
| 50 MB | 232851.679 | 489.905 | 475.3× |

Buffered file reading performed substantially faster in the recorded experiments.

### 5.2 File Search Growth

| Number of Records | Average Search Time (ms) |
|---:|---:|
| 1,000 | 4.5750 |
| 10,000 | 10.2280 |
| 50,000 | 21.5132 |
| 100,000 | 35.4710 |

The experiment demonstrates how search execution time changes as the number of stored records increases.

### 5.3 JDBC Insertion Comparison

| Records | Statement (ms) | PreparedStatement (ms) | Batch (ms) |
|---:|---:|---:|---:|
| 10 | 76 | 76 | 45 |
| 1,000 | 2510 | 2439 | 510 |
| 10,000 | 22110 | 21270 | 2833 |
| 100,000 | 233415 | 231840 | 39022 |

Batch insertion achieved the best execution time in the recorded experiment.

### 5.4 Transaction Testing

| Test | Result |
|---|---|
| Auto-commit with forced duplicate-key failure | 4 rows remained |
| Transaction with rollback | 0 rows remained |

This demonstrates the importance of transaction control when multiple database operations must succeed or fail together.

### 5.5 SQL Injection Testing

| Approach | Result |
|---|---|
| Unsafe SQL concatenation | Crafted input accepted |
| PreparedStatement | Crafted input rejected |

PreparedStatement provides safer parameter handling than directly concatenating user input into SQL statements.

**Note:** Execution timings may vary depending on the system, database configuration, and file-system caching.

Detailed experimental results are available in `docs/PartB_Experiments.md` and `docs/measured_results.csv`.

---

## 6. Database Setup

The database-related tasks require MySQL and MySQL Connector/J.

### Step 1 – Create the Database

Execute the SQL script:

```sql
SOURCE sql/schema.sql;
```

Alternatively, execute the contents of `sql/schema.sql` through MySQL Workbench or the MySQL command-line client.

### Step 2 – Configure Database Credentials

Configure the following environment variables before running JDBC programs:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Example configuration:

```bash
export DB_URL="jdbc:mysql://localhost:3306/assignment5"
export DB_USER="root"
export DB_PASSWORD="your_password"
```

Replace the example password with your actual local MySQL password.

On Windows PowerShell, use:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/assignment5"
$env:DB_USER="root"
$env:DB_PASSWORD="your_password"
```

### Step 3 – Configure MySQL Connector/J

Ensure that the MySQL Connector/J JAR file is available in your development environment.

The JDBC programs must have the Connector/J JAR included in their classpath.

---

## 7. Compilation and Execution

Navigate to the project root directory.

### Check Java Installation

```bash
java -version
javac -version
```

### Compile and Run Task 1

```bash
javac Task01/*.java
java -cp Task01 PassStore
```

### Compile and Run Task 2

```bash
javac Task02/LineCounter.java
java -cp Task02 LineCounter
```

### Compile and Run Task 3

```bash
javac Task03/StudentDaoDemo.java
java -cp Task03 StudentDaoDemo
```

### Compile and Run Task 4

```bash
javac Task04/SerializationDemo.java
java -cp Task04 SerializationDemo
```

### Compile and Run Task 5

Compile and execute with the MySQL Connector/J JAR in the classpath.

### Compile and Run Task 6

Compile and execute `PassLoader.java` and `TransactionComparison.java` with the MySQL Connector/J JAR in the classpath.

### Compile and Run Task 7

Compile and execute `MigrationAndMetadata.java` with the MySQL Connector/J JAR in the classpath.

### Compile and Run Task 8

```bash
javac Task08/AdversarialFileTest.java
java -cp Task08 AdversarialFileTest
```

**Important:** JDBC tasks require a running MySQL server, the database schema, configured credentials, and the Connector/J driver. Use the actual classpath and execution settings configured in your environment.

---

## 8. Evidence and Screenshots

Execution screenshots and supporting evidence are organized task-wise.

| Folder | Evidence |
|---|---|
| `evidence/Q1/` | Bus-pass file store and search |
| `evidence/Q2/` | Buffered I/O performance comparison |
| `evidence/Q3/` | DAO implementation |
| `evidence/Q4/` | Serialization and deserialization |
| `evidence/Q5/` | SQL injection and transaction testing |
| `evidence/Q6/` | JDBC insertion and transaction comparison |
| `evidence/Q7/` | Database migration and metadata |
| `evidence/Q8/` | Adversarial file validation |

Each evidence folder contains its corresponding screenshots and a README describing the evidence.

---

## 9. Important Design Considerations

- File-based persistence is suitable for simple and lightweight storage requirements.
- Buffered I/O reduces the overhead associated with repeated low-level read operations.
- DAO architecture improves separation of concerns and maintainability.
- Serialization supports persistence of Java object graphs.
- PreparedStatement improves SQL query safety by separating SQL structure from parameter values.
- Database transactions maintain consistency when multiple operations are performed together.
- Batch processing reduces repeated database communication overhead.
- Database migration requires validation and error handling to prevent invalid records from entering the database.

The complete design discussion and pseudocode are available in `docs/PartA_Design_and_Analysis.md`.

---

## 10. Known Implementation Notes

- The complete transport-office record design discussed in Part A contains seven fields: pass ID, register number, name, route, boarding stop, validity date, and amount.
- The executable PassStore implementation follows the five-field format used in the supplied starter program: pass ID, register number, name, route number, and amount.
- The current PassStore implementation replaces commas in names rather than implementing complete CSV escaping. A production implementation should use a standards-compliant CSV parser and writer.
- Database credentials and Connector/J configuration must be supplied locally.
- Experimental timings are environment-dependent and should not be interpreted as universal performance guarantees.

---

## 11. Conclusion

This assignment provided practical experience with Java persistence engineering through file handling, buffering, serialization, DAO design, JDBC connectivity, transaction management, SQL injection prevention, batch processing, and database migration.

The experiments demonstrated the performance benefits of buffered file operations and JDBC batch insertion, while security and transaction tests highlighted the importance of safe database programming and reliable data management.

The project combines implementation, experimental investigation, measured results, and supporting evidence to demonstrate the concepts covered under CO5.

---
